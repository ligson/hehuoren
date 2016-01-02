package myFrameU.weixin.pay.util;

import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import myFrameU.weixin.init.InitConfig;
import myFrameU.weixin.pay.entity.WxPayJSAPINeedEntity;

import org.json.JSONObject;

public class CreateJSAPIPayStr {
	public static WxPayJSAPINeedEntity createWxPayJSAPINeedEntity(String prepay_id) throws Exception{
		InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
		String timeStamp=((new Date()).getTime())/1000+"";
		String nonce_str = WXPayUtil.getRandomStringByLength(32);
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
		parameters.put("appId", ic.getAppId());
		parameters.put("nonceStr",nonce_str);
		parameters.put("package","prepay_id="+prepay_id);
		parameters.put("signType","MD5");
		parameters.put("timeStamp",timeStamp);
	    String paySign = WXPayUtil.createSign("UTF-8",parameters,ic.getAppKey()); 
		/*
	    JSONObject jo = new JSONObject();
		jo.put("appId", ic.getAppId());
		jo.put("timeStamp", timeStamp);
		jo.put("nonceStr",nonce_str);
		jo.put("wpackage", "prepay_id="+prepay_id);
		jo.put("signType","MD5");
		jo.put("paySign", paySign);
		*/
		WxPayJSAPINeedEntity wpjspneedEntity = new WxPayJSAPINeedEntity();
		wpjspneedEntity.setAppId(ic.getAppId());
		wpjspneedEntity.setTimeStamp(timeStamp);
		wpjspneedEntity.setNonceStr(nonce_str);
		wpjspneedEntity.setWpackage("prepay_id="+prepay_id);
		wpjspneedEntity.setSignType("MD5");
		wpjspneedEntity.setPaySign(paySign);
		
	    
		return wpjspneedEntity;
	}
	public static JSONObject createJSAPIPayStr(String prepay_id) throws Exception{
		
		/**
		 * 输出js支付所需要的参数字符串
		 * "appId" : "wx247c7e42bcde403d", //公众号名称，由商户传入     
			"timeStamp" : " 1395712654", //时间戳，自1970年以来的秒数     
			"nonceStr" : "e61463f8efa94090b1f366cccfbbb444", //随机串     
			"package" : "prepay_id=u802345jgfjsdfgsdg888",
			"signType" : "MD5", //微信签名方式:     
			"paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
		 */
		InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
		String timeStamp=((new Date()).getTime())/1000+"";
		String nonce_str = WXPayUtil.getRandomStringByLength(32);
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
		parameters.put("appId", ic.getAppId());
		parameters.put("nonceStr",nonce_str);
		parameters.put("package","prepay_id="+prepay_id);
		parameters.put("signType","MD5");
		parameters.put("timeStamp",timeStamp);
	    String paySign = WXPayUtil.createSign("UTF-8",parameters,ic.getAppKey()); 
		
		/*StringBuffer sb = new StringBuffer("");
		sb.append("\"appId\":\"").append(ic.getAppId()).append("\",");
		sb.append("\"timeStamp\":\"").append(timeStamp).append("\",");
		sb.append("\"nonceStr\":\"").append(nonce_str).append("\",");
		sb.append("\"package\":\"").append("prepay_id=").append(prepay_id).append("\",");
		sb.append("\"signType\":\"").append("MD5").append("\",");
		sb.append("\"paySign\":\"").append(paySign).append("\",");
		*/
		
		JSONObject jo = new JSONObject();
		jo.put("appId", ic.getAppId());
		jo.put("timeStamp", timeStamp);
		jo.put("nonceStr",nonce_str);
		jo.put("wpackage", "prepay_id="+prepay_id);
		jo.put("signType","MD5");
		jo.put("paySign", paySign);
		
		
		
		return jo;
		
	}
}
