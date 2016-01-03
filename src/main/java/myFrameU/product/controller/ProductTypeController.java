package myFrameU.product.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.adv.entity.Advertisement;
import myFrameU.adv.entity.Advertising;
import myFrameU.app.util.AppJsonResultUtil;
import myFrameU.exception.exception.MyStrException;
import myFrameU.product.entity.ProductType;
import myFrameU.product.util.ProductTypeUtil;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.collection.MyCollectionUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ProductTypeController extends FatherController {
	@RequestMapping(value={"/productType/finds","/admin/productType/finds"})
	public String findShopLevels(HttpServletRequest req,HttpServletResponse res) throws Exception{
		//HashMap<String,ProductType> map=(HashMap<String, ProductType>) uICacheManager.getObjectCached("web", "productTypeMap");
		//List<ProductType> list = (List<ProductType>)MyCollectionUtils.map2list(map);
		//req.setAttribute(AppJsonResultUtil.app_+"productTypeList", list);
		return getForward("setUp/productTypeList", req);
	}
	
	
	@RequestMapping(value={"/admin/productType/add"})
	public void add(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name=form.getString("name");
		String isRoot=form.getString("isRoot");
		Double webTicheng = form.getDouble("webTicheng");
		if(null==webTicheng){
			webTicheng=0d;
		}
		if(null!=name && !name.equals("") && null!=isRoot && !isRoot.equals("")){
			//ProductType ptOld=(ProductType) aBiz.findObjectById("from ProductType as pt where pt.name=?", new Object[]{name});
			//if(null==ptOld){
				if(isRoot.equals("isRoot")){
					ProductType pt = new ProductType();
					pt.setName(name);
					pt.setIsROOT(0);
					pt.setFatherId(0);
					pt.setIsLeaf(1);
					pt.setJibie(1);
					pt.setUrl("1");
					pt.setTreeId("");
					pt.setParent(null);
					pt.setWebTicheng(webTicheng);
					pt.setAllName(name);
					//aBiz.addObject(pt);
					pt.setTreeId("["+pt.getId()+"]");
					pt.setRootTypeId(pt.getId());
					
					//aBiz.modifyObject(pt);
					
				}else{
					Integer fatherId=form.getInteger("fatherId");
					if(null!=fatherId && fatherId.intValue()!=0){
					//	ProductType pt1=(ProductType)aBiz.findObjectById("from ProductType as pt where pt.id=?", new Object[]{fatherId});
				/*		if(null!=pt1){
							ProductType pt = new ProductType();
							pt.setName(name);
							pt.setIsROOT(1);
							pt.setFatherId(pt1.getId());
							pt.setIsLeaf(1);
							pt.setJibie(2);
							pt.setParent(pt1);
							pt.setWebTicheng(webTicheng);
							pt.setUrl("1");
							pt.setTreeId("");
							pt.setAllName(pt1.getAllName()+"-"+name);
							aBiz.addObject(pt);
							
							pt.setTreeId(pt1.getTreeId()+"["+pt.getId()+"]");
							pt.setRootTypeId(pt1.getId());
							aBiz.modifyObject(pt);
							
							pt1.setIsLeaf(0);
							aBiz.modifyObject(pt1);
						}*/
					}else{
						throw new MyStrException("请选择父类");
					}
					
				}
			/*}else{
				throw new MyStrException("该名称已经存在！");
			}*/
		}
	}
	
	@RequestMapping(value={"/admin/productType/remove"})
	public void remove(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			//ProductType pt = (ProductType)aBiz.findObjectById("from ProductType as pt where pt.id=?", new Object[]{id});
			/*if(null!=pt){
				int isROOT=pt.getIsROOT();
				if(isROOT==0){
					//根目录，删除广告
					//aBiz.j_execute("delete from adv_advertisement where advertisingMarkedNum='advertingMarkedNum_productLanmu_1' and sonByValue='"+pt.getId()+"'", null);
				}
				aBiz.removeObject(pt);
			}*/
		}
	}
	@RequestMapping(value={"/admin/productType/findById"})
	public String findById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			/*ProductType pt = (ProductType)aBiz.findObjectById("from ProductType as pt where pt.id=?", new Object[]{id});
			if(null!=pt){
				req.setAttribute(AppJsonResultUtil.app_+"productType", pt);
			}*/
		}
		return getForward("setUp/productTypeMod", req);
	}
	
	@RequestMapping(value={"/admin/productType/modify"})
	public void modify(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		String name=form.getString("name");
		Double webTicheng = form.getDouble("webTicheng");
		if(null!=name && !name.equals("") && null!=webTicheng && null!=id && id.intValue()!=0){
			//ProductType pt = (ProductType)aBiz.findObjectById("from ProductType as pt where pt.id=?", new Object[]{id});
			/*if(null!=pt){
				ProductType ptOld=(ProductType) aBiz.findObjectById("from ProductType as pt where pt.name=?", new Object[]{name});
				if(null==ptOld){
					String nameOld=pt.getName();
					String ptOldAllName=pt.getAllName();
					if(!name.equals(nameOld)){
						//修改name
						String newAllName=ProductTypeUtil.getNewAllName(ptOldAllName, nameOld, name);
						pt.setName(name);
						pt.setAllName(newAllName);
						int isLeaf=pt.getIsLeaf();
						if(isLeaf==1){
							//叶子节点，不管了
						}else{
							List<ProductType> sonsList = (List<ProductType>)aBiz.findObjectList(ProductType.class, new Object[]{id}, "from ProductType as pt where pt.fatherId=?", null, false, 0, 0);
							int size = sonsList.size();
							ProductType p = null;
							String sonOldAllName=null;
							String sonNewAllName=null;
							for(int i=0;i<size;i++){
								p=sonsList.get(i);
								sonOldAllName=p.getAllName();
								sonNewAllName=ProductTypeUtil.getNewAllName(sonOldAllName, nameOld, name);
								p.setAllName(sonNewAllName);
								aBiz.modifyObject(p);
							}
						}
					}
					pt.setWebTicheng(webTicheng);
					aBiz.modifyObject(pt);
				}else{
					throw new MyStrException("重名了！");
				}
			}*/
		}
	}
	
}
