package myFrameU.weixin.base.util;

import javax.servlet.http.HttpServletRequest;

import myFrameU.util.httpUtil.filter.RequestFilter;
import myFrameU.weixin.base.entity.WXUser;

public class GetWXUserUtil {

	public static String getWXID(){
		String wxId = null;
		HttpServletRequest request = (HttpServletRequest) RequestFilter.threadLocal.get().get("request");
		if(null!=request){
			Object wxIdO=request.getSession().getAttribute("wxId");
			if(null!=wxIdO){
				wxId=(String)wxIdO;
			}
		}
		return wxId;
	}
	public static void saveWXID(String wxId){
		HttpServletRequest request = (HttpServletRequest) RequestFilter.threadLocal.get().get("request");
		if(null!=request){
			request.getSession().setAttribute("wxId", wxId);
		}
		
	}
	public static WXUser getWXUser(){
		HttpServletRequest request = (HttpServletRequest) RequestFilter.threadLocal.get().get("request");
		WXUser wxUser=null;
		if(null!=request){
			Object wxUserO = request.getSession().getAttribute("wxUser");
			if(null!=wxUserO){
				wxUser=(WXUser)wxUserO;
			}
		}
		return wxUser;
	}
	
	public static void saveWXUSER(WXUser wxUser){
		HttpServletRequest request = (HttpServletRequest) RequestFilter.threadLocal.get().get("request");
		if(null!=request){
			request.getSession().setAttribute("wxUser", wxUser);
		}
	}
}
