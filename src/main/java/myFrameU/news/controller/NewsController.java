package myFrameU.news.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.news.biz.NewsBizI;
import myFrameU.news.entity.News;
import myFrameU.news.entity.NewsContent;
import myFrameU.news.entity.NewsType;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("newsController")
public class NewsController extends FatherController {
	@Autowired
	private NewsBizI newsBiz;
	public NewsBizI getNewsBiz() {
		return newsBiz;
	}
	public void setNewsBiz(NewsBizI newsBiz) {
		this.newsBiz = newsBiz;
	}

	@RequestMapping(value={"/news/finds","/wrap/news/finds","/admin/news/finds"})
	public String finds(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String orderBy = form.getString("orderBy");
		String queryArgs = form.getString("queryArgs");
		
		
		List<NewsType> newsTypeList=CacheKey.CKNewsType.ALLMAP.getList(uICacheManager);
		req.setAttribute("newsTypeList", newsTypeList);
		
		
		aBiz.findEntitysByArgs(
				News.class, 
				EntityTableUtil.tableName(News.class.getName()), 
				queryArgs, orderBy, null, true, 0, "newsList", req);
		
		
		
		return getForward("news/newsList", req);
	}
	
	@RequestMapping(value={"/admin/news/toAdd_step1"})
	public String toAdd_sept1(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		//[5][7]
		List<NewsType> newsTypeList=CacheKey.CKNewsType.ALLMAP.getList(uICacheManager);
		req.setAttribute("newsTypeList", newsTypeList);
		
		return getForward("news/newsAdd1", req);
	}
	
	
	@RequestMapping(value={"/admin/news/toAdd_step2"})
	public String toAdd_step2(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer newsTypeId=form.getInteger("newsTypeId");
		if(null!=newsTypeId && newsTypeId.intValue()!=0){
			NewsType nt = CacheKey.CKNewsType.ALLMAP.getObject(uICacheManager, newsTypeId);
			if(null!=nt){
				req.setAttribute("newsType", nt);
			}
		}
		
		return getForward("news/newsAdd2", req);
	}
	
	
	
	
	@RequestMapping(value="/admin/news/add")
	public String add(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String title = form.getString("title");
		String redirect=form.getString(CommonField.redirect);
		Integer newsTypeId=form.getInteger("newsTypeId");
		String image = form.getString("image");
		String content=form.getString("content");
		
		if(null==title || title.equals("")){
			throw new MyRefererException("请填写标题");
		}
		if(null==newsTypeId || newsTypeId.intValue()==0){
			throw new MyRefererException("请选择种类");
		}
		//第一）处理普通属性
		News n = new News();
		n.setTitle(title);
		n.setImage(image);
		n.setNewsTypeId(newsTypeId);
		NewsType nt = CacheKey.CKNewsType.ALLMAP.getObject(uICacheManager, newsTypeId);
		if(null!=nt){
			int isROOT=nt.getIsROOT();
			if(isROOT==0){
				n.setNewsTypeROOTId(nt.getId());
			}else{
				n.setNewsTypeROOTId(nt.getRootTypeId());
			}
			n.setNewsTypeTreeIds(nt.getTreeId());
		}
		
		n.setRelDate(new Date());
		n.setStatus(News.STATUS.OK.toString());
		
		newsBiz.addNews(n, content);
		
		return redirect;
	}
	
	
	
	
	
	/**
	 * 去修改
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/admin/news/toMod")
	public String toMod(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		if(null==id || id==0){
			throw new MyStrException("请填写newsId");
		}
		News n = newsBiz.findNewsById(id,false);
		if(null!=n){
			req.setAttribute("news", n);
			NewsContent nc = (NewsContent)aBiz.findObjectById("from NewsContent as nc where nc.newsId=?", new Object[]{n.getId()});
			req.setAttribute("newsContent", nc);
			
		}
		return getForward("news/newsMod", req);
	}
	
	
	@RequestMapping(value="/admin/news/mod")
	public String mod(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		String title = form.getString("title");
		String redirect=form.getString(CommonField.redirect);
		Integer newsTypeId=form.getInteger("newsTypeId");
		String image = form.getString("image");
		String content=form.getString("content");
		
		if(null==title || title.equals("")){
			throw new MyRefererException("请填写标题");
		}
		if(null==newsTypeId || newsTypeId.intValue()==0){
			throw new MyRefererException("请选择种类");
		}
		
		if(null!=id && id.intValue()!=0){
			News n = (News)newsBiz.findNewsById(id, false);
			if(null!=n){
				n.setTitle(title);
				n.setImage(image);
				n.setNewsTypeId(newsTypeId);
				NewsType nt = CacheKey.CKNewsType.ALLMAP.getObject(uICacheManager, newsTypeId);
				if(null!=nt){
					int isROOT=nt.getIsROOT();
					if(isROOT==0){
						n.setNewsTypeROOTId(nt.getId());
					}else{
						n.setNewsTypeROOTId(nt.getRootTypeId());
					}
					n.setNewsTypeTreeIds(nt.getTreeId());
				}
				
				newsBiz.modifyNews(n, content);
			}
		}
		return redirect;
	}
	
	
	

	@RequestMapping(value="/admin/news/del")
	public void del(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			News n = newsBiz.findNewsById(id, false);
			if(null!=n){
				newsBiz.delNews(n);
			}
		}
	}
	
	
	
	

	@RequestMapping(value={"/admin/news/findId","/wrap/news/findId"})
	public String findId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id=form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			News n=newsBiz.findNewsById(id, true);
			if(null!=n){
				req.setAttribute("news", n);
				int newsContentId = n.getNewsContentId();
				NewsContent nc = (NewsContent)aBiz.findObjectById("from NewsContent as nc where nc.newsId=?", new Object[]{id});
				req.setAttribute("newsContent", nc);
				
				NewsType nt = CacheKey.CKNewsType.ALLMAP.getObject(uICacheManager, n.getNewsTypeId());
				req.setAttribute("newsType", nt);
			}
		}
		String pack="manage/admin/news/news";
		String fix = WebAop.getReqPrefix(req);
		if(null!=fix && !fix.equals("")){
			if(fix.contains("/wrap/")){
				pack="wrap/fg/news";
			}
		}
		return pack;
	}
	
	
	

	

	
	
	
	
	
}
