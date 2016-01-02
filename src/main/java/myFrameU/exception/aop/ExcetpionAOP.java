package myFrameU.exception.aop;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myFrameU.app.util.AppJsonResultUtil;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyJSONException;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.spring.aop.interf.AOPI;
import myFrameU.spring.aop.util.AOPUtil;
import net.sf.json.JSONObject;

public class ExcetpionAOP implements AOPI {
	@Override
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,AbstractBizI aBiz)  throws Exception{
		Object appKeyo=req.getParameter(AppJsonResultUtil.appKey);
		System.out.println("AOP子模块----执行了ExcetpionAOP-aopAfter");
		if(null==appKeyo){
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			try{
				Exception e = (Exception) req.getAttribute("exception");
				HttpSession session = req.getSession();
				if(null!=e){
					System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
					if (e instanceof MyJSONException) {
						System.out.println("ccccccccccccccccccccccccccccccc");
						req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
						dealE_json(e,res);
					} else if (e instanceof MyRefererException) {
						System.out.println("dddddddddddddddddddddddddddddddddddd");
						/**
						 * 页面跳转异常
						 * 		比如修改新闻，点击beforeModifyNews，实际上就是已经查出news来了，
						 * 		在修改的过程中，出现错误，那么还应该回到这个news单页面来
						 * 		在刷新一次之后，页面上会有js来弹出错误信息
						 * 
						 * 
						 * 		保留原来填写的字段值
						 * 		
						 */
						Enumeration e1 = (Enumeration) req.getParameterNames();
						while (e1.hasMoreElements()) {
							String parName = (String) e1.nextElement();
							String value = req.getParameter(parName);
							if(null!=parName && !parName.equals("")){
								req.getSession().setAttribute("form_"+parName, value);
							}
						}
						req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
						String referer = req.getHeader("referer");
						if (null != referer) {
							req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
							session.setAttribute("errorMessage", e.getMessage());
							res.sendRedirect(referer);
						}
					} else if (e instanceof MyStrException) {
						System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
						req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
						res.setContentType("text/plain;charset=UTF-8");  
						res.getWriter().print(e.getMessage());
					}else if(e instanceof InvocationTargetException){
						System.out.println("fffffffffffffffffffffffffffffffffffffffff");
						System.out.println("1111111111111111111");
						
						
						Exception e1=(Exception) ((InvocationTargetException) e).getTargetException();
						if(e1 instanceof MyStrException){
							System.out.println("222222222222222222222222222");
							req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
							dealE_str(e1, res);
						}else if(e1 instanceof MyJSONException){
							System.out.println("333333333333333333333333");
							req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
							dealE_json(e1,res);
						}else if(e1 instanceof MyRefererException){
							System.out.println("44444444444444444444");
							Enumeration e12 = (Enumeration) req.getParameterNames();
							while (e12.hasMoreElements()) {
								String parName = (String) e12.nextElement();
								String value = req.getParameter(parName);
								if(null!=parName && !parName.equals("")){
									req.getSession().setAttribute("form_"+parName, value);
								}
							}
							req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
							String referer = req.getHeader("referer");
							if (null != referer) {
								req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
								session.setAttribute("errorMessage", e.getMessage());
								res.sendRedirect(referer);
							}
						}else{
							System.out.println("5555555555555555555555");
							//exception
							throw e;
						}
						
					} else {
						System.out.println("ggggggggggggggggggggggggggggggggggggg");
						//res.getWriter().print(e.getMessage());
						e.printStackTrace();
						throw e;
					}
				}
			}catch(Exception e){
				System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
				//e.printStackTrace();
				System.out.println("6666666666666666666666");
				throw e;
			}
		}else{
			//手机端过来的异常
			try{
				Exception e = (Exception) req.getAttribute("exception");
				if(null!=e){
					String result=null;
					if (e instanceof MyJSONException) {
						result=AppJsonResultUtil.tipResult("error", e.getMessage(), req);
					}else if(e instanceof MyStrException){
						result=AppJsonResultUtil.tipResult("error", e.getMessage(), req);
					}else if(e instanceof MyRefererException){
						result=AppJsonResultUtil.tipResult("error", "错误", req);
					}else{
						result=AppJsonResultUtil.tipResult("error", "错误", req);
					}
					req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
					res.setContentType("text/json;charset=UTF-8");  
					res.setHeader("Cache-Control", "no-cache");  
					res.getWriter().print(result);
					
				}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		return null;
		

	}

	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
				return null;
		
	}
	
	private void dealE_str(Exception e,HttpServletResponse res) throws Exception{
		String msg = e.getMessage();
		res.setContentType("text/plain;charset=UTF-8");  
		res.getWriter().print(msg);
	}
	private void dealE_json(Exception e,HttpServletResponse res) throws Exception{
		res.setContentType("text/json;charset=UTF-8");  
		res.setHeader("Cache-Control", "no-cache");  
		
		JSONObject jo = new JSONObject();
		jo.put("error", "error");
		jo.put("errorMessage", e.getMessage());
		res.getWriter().print(jo.toString());
	}
	
	
}
