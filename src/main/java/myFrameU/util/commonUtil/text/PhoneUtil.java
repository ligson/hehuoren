package myFrameU.util.commonUtil.text;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 手机号码的验证
 *
 */
public class PhoneUtil {
	
	private static List<String> telPhone_head=new ArrayList<String>();
	static{
		telPhone_head.add("177");
		telPhone_head.add("134");
		telPhone_head.add("135");
		telPhone_head.add("136");
		telPhone_head.add("137");
		telPhone_head.add("138");
		telPhone_head.add("139");
		telPhone_head.add("150");
		telPhone_head.add("151");
		telPhone_head.add("152");
		telPhone_head.add("157");
		telPhone_head.add("158");
		telPhone_head.add("159");
		telPhone_head.add("182");
		telPhone_head.add("188");
		telPhone_head.add("147");
		
		telPhone_head.add("183");
		telPhone_head.add("187");
		telPhone_head.add("130");
		telPhone_head.add("131");
		telPhone_head.add("132");
		telPhone_head.add("155");
		telPhone_head.add("156");
	
		telPhone_head.add("186");
		telPhone_head.add("145");
		telPhone_head.add("133");
		telPhone_head.add("153");
		
		telPhone_head.add("189");
		telPhone_head.add("180");
		telPhone_head.add("181");
		telPhone_head.add("185");
	}
	public static final String PHONE_MESSAGE_ERROR="手机号不正确";
	public static final String PHONE_MESSAGE_11="手机号不是11位";
	public static final String PHONE_MESSAGE_NULL="手机号不能为空";
	
	
	//验证手机号是否正确，返回值为不合格的理由。
	public static String vailterTelPhone(String telPhone){
		if(null!=telPhone){
			if(telPhone.length()==11){
				String head=telPhone.substring(0,3);
				if(telPhone_head.contains(head)){
					return null;
				}else{
					return PHONE_MESSAGE_ERROR;
				}
			}else{
				return PHONE_MESSAGE_11;
			}
		}else{
			return PHONE_MESSAGE_NULL;
		}
	}
	
	
	
	
	
	
	
}
