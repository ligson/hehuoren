package myFrameU.weixin.base.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

public class WeixinUtil {
	
	//效验判断此条消息的真实性
/*	public static String verIsTrue(HttpServletRequest request){
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		if(null!=signature && null!=timestamp && null!=nonce && null!=echostr ){
			String[] arr={myFrameU.weixin.init.InitMavenImpl.ic.getToken(),timestamp,nonce};
			Arrays.sort(arr);
			
			StringBuffer sb = new StringBuffer();
			for(String a:arr){
				sb.append(a);
			}
			
			String sha1Msg=SecurityKit.sha1(sb.toString());
			if(sha1Msg.equals(signature)){
				return echostr;
			}
		}
		return null;
	}*/
	//效验判断此条消息的真实性
	public static String verIsTrue(HttpServletRequest request){
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		System.out.println(echostr+"====================================");
		if(null!=signature && null!=timestamp && null!=nonce && null!=echostr ){
			String[] arr={myFrameU.weixin.init.InitMavenImpl.ic.getToken(),timestamp,nonce};
			Arrays.sort(arr);
			
			StringBuffer sb = new StringBuffer();
			for(String a:arr){
				sb.append(a);
			}
			
			String sha1Msg=SecurityKit.sha1(sb.toString());
			if(sha1Msg.equals(signature)){
				return echostr;
			}
		}
		return null;
	}
}
