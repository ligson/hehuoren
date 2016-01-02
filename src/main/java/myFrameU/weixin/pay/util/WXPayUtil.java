package myFrameU.weixin.pay.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

public class WXPayUtil {
	/**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    
    
    
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters,String key){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();
            System.out.println("----"+k);
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)  && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + key);  
        System.out.println(sb.toString()+"=============================================");
        String sign=MD5.MD5Encode(sb.toString()).toUpperCase();
        //String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();  
        
        return sign;  
    }  
    
    
    
    public enum WXPAYTYPE{
    	JSAPI,NATIVE,APP,WAP
    }
    
 /*   public String getAttach(HashMap<String,String> map,String key){
    	StringBuffer sb = new StringBuffer();
    	if(null!=key && !key.equals("")){
    		if(key.equals("RECHARGE")){
    			String accountItemId=map.get("accountItemId");
    			sb.append("accountItemId':'").append(accountItemId).append("");
    		}
    	}
    }
    */
    
    
    //微信的支付单位是分
    public static int priceYUAN2FEN(float price){
    	if(price>0){
    		float fenFl=price*100;
    		int fenIl=(int)fenFl;
    		return fenIl;
    	}else{
    		return 0;
    	}
    }
    
    public static void main(String args[]){
    	System.out.println(priceYUAN2FEN(0.1f));
    }
    
    
}
