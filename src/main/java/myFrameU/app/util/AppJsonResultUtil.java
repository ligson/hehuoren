package myFrameU.app.util;

import javax.servlet.http.HttpServletRequest;

import myFrameU.app.init.InitConfig;

public class AppJsonResultUtil {
	public static final String appKey="appKey";
	public static final String app_="app_";
	public static final String appTipResult="appTipResult";
	
	public static String tipResult(String okOrEr,String message,HttpServletRequest req){
		StringBuffer sb = new StringBuffer("{\"status\":\"");
		sb.append(okOrEr).append("\",\"results\":\"\",\"message\":\"").append(message).append("\"}");
		req.setAttribute(appTipResult, sb.toString());
		return sb.toString();
	}
	
	
	
	public static boolean verAppkey(HttpServletRequest req){
		InitConfig initConfig=myFrameU.app.init.InitMavenImpl.ic;
		String webAppKey=initConfig.getAppKey();
		Object appKeyo=req.getParameter(AppJsonResultUtil.appKey);
		if(null!=appKeyo){
			String appKey=appKeyo.toString();
			if(appKey.equals(webAppKey)){
				return true;
			}
		}
		return false;
	}
	
	
	
}
