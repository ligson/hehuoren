package myFrameU.news.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.exception.exception.MyStrException;
import myFrameU.news.entity.NewsType;
import myFrameU.news.util.NewsTypeUtil;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.text.PinyinUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class NewsTypeController extends FatherController {
	@RequestMapping(value={"/newsType/finds","/admin/newsType/finds"})
	public String finds(HttpServletRequest req,HttpServletResponse res) throws Exception{
		List<NewsType> newsTypeList = CacheKey.CKNewsType.ALLMAP.getList(uICacheManager);
		req.setAttribute("newsTypeList", newsTypeList);
		return getForward("news/newsTypeList", req);
	}
	
	
	@RequestMapping(value={"/admin/newsType/add"})
	public void add(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name=form.getString("name");
		String isRoot=form.getString("isRoot");
		if(null==isRoot || isRoot.equals("")){
			isRoot="root";
		}
		
		if(null!=name && !name.equals("") && null!=isRoot && !isRoot.equals("")){
			//if(null==ptOld){
				if(isRoot.equals("isRoot")){
					NewsType nt = new NewsType();
					nt.setName(name);
					nt.setIsROOT(0);
					nt.setFatherId(0);
					nt.setIsLeaf(1);
					nt.setJibie(1);
					nt.setPinyin(PinyinUtil.getPingYin(name));
					nt.setUrl("1");
					nt.setTreeId("");
					nt.setParent(null);
					nt.setAllName(name);
					aBiz.addObject(nt);
					nt.setTreeId("["+nt.getId()+"]");
					nt.setRootTypeId(nt.getId());
					aBiz.modifyObject(nt);
				}else{
					Integer fatherId=form.getInteger("fatherId");
					if(null!=fatherId && fatherId.intValue()!=0){
						NewsType nt1=(NewsType)aBiz.findObjectById("from NewsType as nt where nt.id=?", new Object[]{fatherId});
						if(null!=nt1){
							NewsType nt = new NewsType();
							nt.setName(name);
							nt.setIsROOT(1);
							nt.setFatherId(nt1.getId());
							nt.setIsLeaf(1);
							nt.setJibie(2);
							nt.setParent(nt1);
							nt.setUrl("1");
							nt.setTreeId("");
							nt.setAllName(nt1.getAllName()+"-"+name);
							aBiz.addObject(nt);
							
							nt.setTreeId(nt1.getTreeId()+"["+nt.getId()+"]");
							nt.setRootTypeId(nt1.getId());
							aBiz.modifyObject(nt);
							nt1.setIsLeaf(0);
							aBiz.modifyObject(nt1);
						}
					}else{
						throw new MyStrException("请选择父类");
					}
					
				}
			/*}else{
				throw new MyStrException("该名称已经存在！");
			}*/
		}
	}
	
	@RequestMapping(value={"/admin/newsType/remove"})
	public void remove(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			NewsType nt=(NewsType)aBiz.findObjectById("from NewsType as nt where nt.id=?", new Object[]{id});
			if(null!=nt){
				aBiz.removeObject(nt);
			}
		}
	}
	
	@RequestMapping(value={"/admin/newsType/findById"})
	public String findById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			NewsType nt=(NewsType)aBiz.findObjectById("from NewsType as nt where nt.id=?", new Object[]{id});
			if(null!=nt){
				req.setAttribute("newsType", nt);
			}
		}
		return getForward("news/newsTypeMod", req);
	}
	
	@RequestMapping(value={"/admin/newsType/modify"})
	public void modify(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		String name=form.getString("name");
		if(null!=name && !name.equals("") && null!=id && id.intValue()!=0){
			NewsType nt=(NewsType)aBiz.findObjectById("from NewsType as nt where nt.id=?", new Object[]{id});
				if(null!=nt){
					String nameOld=nt.getName();
					String ptOldAllName=nt.getAllName();
					if(!name.equals(nameOld)){
						//修改name
						String newAllName=NewsTypeUtil.getNewAllName(ptOldAllName, nameOld, name);
						nt.setName(name);
						nt.setAllName(newAllName);
						int isLeaf=nt.getIsLeaf();
						if(isLeaf==1){
							//叶子节点，不管了
						}else{
							List<NewsType> sonsList = (List<NewsType>)aBiz.findObjectList(NewsType.class, new Object[]{id}, "from NewsType as nt where nt.fatherId=?", null, false, 0, 0);
							int size = sonsList.size();
							NewsType nt123 = null;
							String sonOldAllName=null;
							String sonNewAllName=null;
							for(int i=0;i<size;i++){
								nt123=sonsList.get(i);
								sonOldAllName=nt123.getAllName();
								sonNewAllName=NewsTypeUtil.getNewAllName(sonOldAllName, nameOld, name);
								nt123.setAllName(sonNewAllName);
								aBiz.modifyObject(nt123);
							}
						}
					}
					aBiz.modifyObject(nt);
				}else{
				}
		}
	}
	
}
