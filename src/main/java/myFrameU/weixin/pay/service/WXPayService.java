package myFrameU.weixin.pay.service;

import java.io.UnsupportedEncodingException;
import java.util.SortedMap;
import java.util.TreeMap;

import myFrameU.util.httpUtil.httpclient.PostXml;
import myFrameU.weixin.base.util.HttpClientConnectionManager;
import myFrameU.weixin.init.InitConfig;
import myFrameU.weixin.pay.entity.WxPayBackEntity;
import myFrameU.weixin.pay.util.ReceiveXmlProcess;
import myFrameU.weixin.pay.util.WXPayUtil;

import org.apache.http.impl.client.DefaultHttpClient;


public class WXPayService {
	public static DefaultHttpClient httpclient;
	static {    
        httpclient = new DefaultHttpClient();    
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端    
    }  
	
	
	//生成统一预支付订单的请求参数
	/**
	 * 
	 * @param openId
	 * @param out_trade_no  咱们系统自己的订单号
	 * @param trade_type	交易类型，JSAPI
	 * @param total_fee		交易金额，整数
	 * @param attach		附加数据
	 * @param body			产品描述
	 * @return
	 */
	public static String createUnifiedorderArgs(String openId,String out_trade_no,String trade_type,String total_fee,String attach,String body){
		//body="test";
		
		String nonce_str = WXPayUtil.getRandomStringByLength(32);
		
		InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("appid", ic.getAppId());  
        
        if(null!=attach && !attach.equals("")){
        	parameters.put("attach", attach);  
		}
        parameters.put("body", body);  
        parameters.put("mch_id", ic.getPayMchId());  
        parameters.put("nonce_str", nonce_str); 
        parameters.put("notify_url", ic.getPayNotifyUrl());  
        parameters.put("openid", openId);  
        parameters.put("out_trade_no", out_trade_no);  
        parameters.put("spbill_create_ip", ic.getPaySpbillCreateIp());  
        parameters.put("total_fee",total_fee);  
        parameters.put("trade_type", trade_type);  
        String mySign = WXPayUtil.createSign("UTF-8",parameters,ic.getAppKey()); 
		
		
		
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>").append("\r\n");
		sb.append("<appid>").append(CDATAValue(ic.getAppId())).append("</appid>").append("\r\n");
		if(null!=attach && !attach.equals("")){
			sb.append("<attach>").append(CDATAValue(attach)).append("</attach>").append("\r\n");
		}
		
		sb.append("<body>").append(body).append("</body>").append("\r\n");
		sb.append("<mch_id>").append(CDATAValue(ic.getPayMchId())).append("</mch_id>").append("\r\n");
		sb.append("<nonce_str>").append(CDATAValue(nonce_str)).append("</nonce_str>").append("\r\n");
		sb.append("<notify_url>").append(CDATAValue(ic.getPayNotifyUrl())).append("</notify_url>").append("\r\n");
		sb.append("<openid>").append(CDATAValue(openId)).append("</openid>").append("\r\n");
		sb.append("<out_trade_no>").append(CDATAValue(out_trade_no)).append("</out_trade_no>").append("\r\n");
		sb.append("<spbill_create_ip>").append(CDATAValue(ic.getPaySpbillCreateIp())).append("</spbill_create_ip>").append("\r\n");
		sb.append("<total_fee>").append(total_fee).append("</total_fee>").append("\r\n");
		sb.append("<trade_type>").append(CDATAValue(trade_type)).append("</trade_type>").append("\r\n");
		sb.append("<sign>").append(CDATAValue(mySign)).append("</sign>").append("\r\n");
		sb.append("</xml>").append("\r\n");
		/**
		 * <xml>
		 * 		<appid>wx247c7e42bcde403d</appid>
		 * 		<body>充值0.01元</body>
		 * 		<mch_id>1247893201</mch_id>
		 * 		<nonce_str>sefdvpit9kmpda33y2gu3r4ah2y9ny9p</nonce_str>
		 * 		<notify_url>http://weixin.024lm.com/024lm/weixin/pay/test/wxPayCallback</notify_url>
		 * 		<openid>o9WE8uGHLCF15kQaFAjBKSdiNsyg</openid>
		 * 		<out_trade_no>1436852435753</out_trade_no>
		 * 		<spbill_create_ip>58.58.75.67</spbill_create_ip>
		 * 		<total_fee>1</total_fee>
		 * 		<trade_type>JSAPI</trade_type>
		 * 		<sign>BD56A65E7800559D3550A63E12FF1C98</sign>
		 * </xml>
		 */
		System.out.println(sb.toString());
		
		
		
		
		String s = sb.toString();
		try {
			//s =  new String(s.toString().getBytes(), "ISO8859-1");
			s = new String(s.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		return s;
	}
	
	
	
	//微信下单
	public WxPayBackEntity addWXPayOrder(String openId,String out_trade_no,String trade_type,String total_fee,String attach,String body) throws Exception{
		String params = createUnifiedorderArgs(openId, out_trade_no, trade_type, total_fee, attach, body);
		
		/*List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		paramsList.add(new BasicNameValuePair("", params));  
		
		String jsonStr = HttpClientUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder", paramsList,true);
		*/
		
		
		
		/*HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.mch.weixin.qq.com/pay/unifiedorder");    
        httpost.setEntity(new StringEntity(params, "UTF-8"));    
        HttpResponse response = httpclient.execute(httpost);    
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");*/
        
		
		
		
		
		
		
		String returnXML = PostXml.postXml("https://api.mch.weixin.qq.com/pay/unifiedorder", params);
		
		
		
		
		/**
		 * <xml>
			 * 	<return_code><![CDATA[SUCCESS]]></return_code>
				<return_msg><![CDATA[OK]]></return_msg>
				<appid><![CDATA[wx247c7e42bcde403d]]></appid>
				<mch_id><![CDATA[1247893201]]></mch_id>
				<nonce_str><![CDATA[f9RGE5td0Otkz9nq]]></nonce_str>
				<sign><![CDATA[1701B0B547AB966B72783DFB0A803668]]></sign>
				<result_code><![CDATA[SUCCESS]]></result_code>
				<prepay_id><![CDATA[wx201507131503564758aafc2d0055569142]]></prepay_id>
				<trade_type><![CDATA[JSAPI]]></trade_type>
			</xml>
		 */
		WxPayBackEntity wpbe=ReceiveXmlProcess.getMsgEntity(returnXML, WxPayBackEntity.class);
		
       // System.out.println("================["+returnXML+"]*********************%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%55");
        
        return wpbe;
	}
	
	
	
	private static String CDATAValue(String value){
		//value="<![CDATA["+value+"]]>";
		return value;
	}
	
	
	
	
	
	
	
	
}
