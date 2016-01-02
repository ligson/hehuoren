package myFrameU.pager.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.pager.init.InitMavenImpl;
import myFrameU.pager.pager.Pager;
import myFrameU.spring.aop.interf.AOPI;
import myFrameU.util.httpUtil.path.PathUtil;

public class AgainPagerAOP implements AOPI{
	public  Object aopAfter(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz) {
		
		System.out.println("AOP子模块----执行了AgainPagerAOP-aopAfter");
		
		Pager pager = (Pager)req.getAttribute("pager");
		if(null!=pager){
			String path = req.getServletPath();
			String qs = req.getQueryString();
			
			String referer=null;
			if(null!=qs && !qs.equals("")){
				referer=path+"?"+qs;
			}else{
				referer=path;
			}
			
			referer=PathUtil.getBasePath_no(req)+referer;
			String end=InitMavenImpl.ic.getStaticHtmlEnd();//.html
			String pagerStr=InitMavenImpl.ic.getStaticPager();//-pager-
			if(null!=referer){
				int currentPage=pager.getCurrentPage();
				int nextPage=currentPage+1;
				int prevPage=currentPage-1;
				int lastPage=pager.getTotalPages();
				if(!referer.contains(pagerStr) && !referer.endsWith(end)){
					if(referer.contains("pageMethod=number")){
						referer=referer.replace("&pageMethod=number&currentPage="+currentPage, "");
						referer=referer.replace("?pageMethod=number&currentPage="+currentPage, "");
					}
					if(referer.contains("?")){
						//http://localhost:8080/yishupaipai/admin/user/finds?queryArgs=[{%27key%27:%27userLevelId%27,%27value%27:%271%27}]
						pager.setNextUrl(referer+"&pageMethod=number&currentPage="+nextPage);
						pager.setPrevUrl(referer+"&pageMethod=number&currentPage="+prevPage);
						pager.setLastUrl(referer+"&pageMethod=number&currentPage="+lastPage);
						pager.setIndexUrl(referer+"&pageMethod=number&currentPage=1");
					}else{
						pager.setNextUrl(referer+"?pageMethod=number&currentPage="+nextPage);
						pager.setPrevUrl(referer+"?pageMethod=number&currentPage="+prevPage);
						pager.setLastUrl(referer+"?pageMethod=number&currentPage="+lastPage);
						pager.setIndexUrl(referer+"?pageMethod=number&currentPage=1");
					}
				}else{
					//http://localhost:8080/zhuangxiu/shenyang/shopList-pager-1.html
					String refererSS=referer.replace(end, "");//http://localhost:8080/zhuangxiu/shenyang/shopList-pager-1
					refererSS=refererSS.split(pagerStr)[0];//http://localhost:8080/zhuangxiu/shenyang/shopList
					refererSS=refererSS+pagerStr;//http://localhost:8080/zhuangxiu/shenyang/shopList-pager-
					pager.setNextUrl(refererSS+nextPage+end);
					pager.setPrevUrl(refererSS+prevPage+end);
					pager.setLastUrl(refererSS+lastPage+end);
					pager.setIndexUrl(refererSS+1+end);
					pager.setUrl(refererSS);
				}
			}
			/*StringBuffer sb = new StringBuffer();
			String path = req.getServletPath();
			String qs = req.getQueryString();
			if (null != qs) {
				int pmIndex = qs.indexOf("&pageMethod");
				if (pmIndex > 0) {
					qs = qs.substring(0, qs.indexOf("&pageMethod"));
				}
				sb.append(path).append("?").append(qs);
				pager.setUrl(sb.toString());
			}
*/
			
			/*if(null!=referer){
				String refererSS=null;
				String beforePager=null;
				//http://localhost:8080/yishupaipai/admin/user/finds?queryArgs=[{%27key%27:%27userLevelId%27,%27value%27:%271%27}]
				//http://localhost:8080/zhuangxiu/shenyang/shopList-pager-1.html
				
				
				
				
				String end=InitMavenImpl.ic.getStaticHtmlEnd();
				String pagerStr=InitMavenImpl.ic.getStaticPager();
				if(referer.endsWith(end)){
					refererSS=referer.replace(end, "");
					if(referer.contains(pagerStr)){
						String array[]=refererSS.split(pagerStr);
						if(null!=array){
							if(array.length==2){
								beforePager=array[0];//http://localhost:8080/zhuangxiu/shenyang/shopList
								//int curPage=new Integer(array[1]).intValue();
								int curPage=pager.getCurrentPage();
								int nextPage=curPage+1;
								int prevPage=curPage-1;
								pager.setNextUrl(beforePager+pagerStr+nextPage+end);
								pager.setPrevUrl(beforePager+pagerStr+prevPage+end);
								pager.setIndexUrl(beforePager+pagerStr+"1"+end);
								pager.setLastUrl(beforePager+pagerStr+pager.getTotalPages()+end);
							}
						}
					}
					
				}
				req.setAttribute("pager", pager);
			}*/
			
			
		}
		return null;
	}

	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
				return null;
	}
}
