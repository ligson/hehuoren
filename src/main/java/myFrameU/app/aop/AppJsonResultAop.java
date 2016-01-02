package myFrameU.app.aop;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.aspectj.lang.ProceedingJoinPoint;

import myFrameU.app.init.InitConfig;
import myFrameU.app.util.AppJsonResultUtil;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.spring.aop.interf.AOPI;
import myFrameU.spring.aop.util.AOPUtil;
import myFrameU.util.commonUtil.json.JSONUtils;

public class AppJsonResultAop implements AOPI {

	@Override
	public Object aopBefore(HttpServletRequest req, HttpServletResponse res,
			UICacheManager cacheManager, AbstractBizI aBiz) {
		return null;
	}

	@Override
	public Object aopAfter(HttpServletRequest req, HttpServletResponse res,
			UICacheManager cacheManager, AbstractBizI aBiz) {
		System.out.println("AOP子模块----执行了AppJsonResultAop-aopAfter");
		String result=null;
		Object appKeyo=req.getParameter(AppJsonResultUtil.appKey);
		if(null!=appKeyo){
			if(AppJsonResultUtil.verAppkey(req)){
				
				
				
				/**
				 * 所有的controller方法分为两大类：findXXXX和其他的
				 * find方法就直接去找request.getAttr，如果是其他的则默认的是error，直到controller在成功的地方显示的触发成功
				 */
				ProceedingJoinPoint pjp=(ProceedingJoinPoint) req.getAttribute("proceedingJoinPoint");
				String method=pjp.getSignature().getName();
				if(method.startsWith("find")){
					StringBuffer sb = new StringBuffer("{\"status\":\"ok\",\"results\":[");
					Enumeration v = req.getAttributeNames();
					String nextElement=null;
					while (v.hasMoreElements()) {
						
						nextElement=(v.nextElement()).toString();
						if(!nextElement.startsWith("org.springframework") && !nextElement.equals("encodingFilter.FILTERED")  && !nextElement.equals("proceedingJoinPoint")){
							if(nextElement.startsWith(AppJsonResultUtil.app_)){
								Object attributeObject=req.getAttribute(nextElement);
								sb.append("{").append("\"").append(nextElement).append("\":");
								if(attributeObject instanceof List){
									//如果是List集合
									String listResult=JSONUtils.toJSONString(attributeObject);
									sb.append(listResult);
								}else if(attributeObject instanceof String){
									//如果返回的是string
									sb.append("\"").append((String)attributeObject).append("\"");
								}else if(attributeObject instanceof Integer){
									//如果返回的是int
									Integer aInt=(Integer) attributeObject;
									sb.append("\"").append(aInt).append("\"");
								}else if(attributeObject instanceof Boolean){
									//如果返回的是boolean
									Boolean aInt=(Boolean) attributeObject;
									sb.append("\"").append(aInt).append("\"");
								}else if(attributeObject instanceof Float){
									//如果返回的是boolean
									Float aInt=(Float) attributeObject;
									sb.append("\"").append(aInt).append("\"");
								}else if(attributeObject instanceof Double){
									//如果返回的是boolean
									Double aInt=(Double) attributeObject;
									sb.append("\"").append(aInt).append("\"");
								}else if(attributeObject instanceof Long){
									//如果返回的是boolean
									Long aInt=(Long) attributeObject;
									sb.append("\"").append(aInt).append("\"");
								}else if(attributeObject instanceof Date){
									//如果返回的是boolean
									Date aInt=(Date) attributeObject;
									sb.append("\"").append(DateUtil.formatDate(aInt)).append("\"");
								}else{
									//其他Object类型，大部分是自定义类型，如Shop
									String oesult=JSONUtils.toJSONString(attributeObject);
									sb.append(oesult);
								}
								sb.append("}").append(",");
							}
						}
					}
					
					StringBuffer sbLast=delSuffix(sb);
					sbLast.append("],\"message\":\"\"}");
					result=sbLast.toString();
				}else{
					String appTipResult=(String) req.getAttribute(AppJsonResultUtil.appTipResult);
					System.out.println("==================-----------============"+appTipResult);
					if(null!=appTipResult && !appTipResult.equals("")){
						result=appTipResult;
					}else{
						//默认的错误，至于错误的原因
						result=AppJsonResultUtil.tipResult("error", "执行失败", req);
					}
				}
			}else{
				result="{\"status\":\"ok\",\"results\":\"\",\"message\":\"没有授权\"}";
			}
			System.out.println(result);
			req.setAttribute(AOPUtil.mySetResultNULL, AOPUtil.mySetResultNULL);
			try {
				res.setContentType("text/json;charset=UTF-8");  
				res.setHeader("Cache-Control", "no-cache");  
				res.getWriter().print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//req.setAttribute("appResults", result);
		}else{
			//没有appKey参数，说明不是手机端过来的
		}
		
		return result;
		
		
		
		
		/**
		 * {
		 * 		"status":"ok",
		 * 		"results":
		 * 			[
		 * 				{
		 * 					"globalList":
		 * 					[
		 * 						{"bz":"备注","id":21,"myValue":"98_188_288_388","name":"招标项目平台抽取费用","namePy":"zbxmptcqfy"},
		 * 						{"bz":"备注","id":21,"myValue":"98_188_288_388","name":"招标项目平台抽取费用","namePy":"zbxmptcqfy"},
		 * 						{"bz":"备注","id":21,"myValue":"98_188_288_388","name":"招标项目平台抽取费用","namePy":"zbxmptcqfy"}
		 * 					]
		 * 				}
		 * 			]
		 * 	}
		 */
		
		
		
		
		
		
		
	}
	
	

	
	
	private StringBuffer delSuffix(StringBuffer sb){
		String sbs=null;
		sbs=sb.toString();
		if(sbs.endsWith(",")){
			sbs=sbs.substring(0,sbs.length()-1);
		}
		StringBuffer sbLast=new StringBuffer(sbs);
		return sbLast;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
