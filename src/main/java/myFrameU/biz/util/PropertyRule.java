package myFrameU.biz.util;

import java.util.regex.Pattern;

import myFrameU.exception.exception.MyStrException;
import myFrameU.util.commonUtil.text.PhoneUtil;

/**
 * 
 * 修改object的property的常规规则
 *
 */
public class PropertyRule {
    
	public static boolean rule(String method,String value) throws Exception{
		if(method.equals("isNotNull")){
			return isNotNull(value);
		}else if(method.equals("isNumber")){
			return isNumber(value);
		}else if(method.equals("isTelPhone")){
			return isTelPhone(value);
		}
		return false;
	}
    
	private static boolean isNotNull(String value) throws Exception{
		if (null != value) {
			return true;
		}
		return false;
	}

	private static boolean isNumber(String value)  throws Exception{
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(value).matches();
	}
	
	private static boolean isTelPhone(String value)  throws Exception{
		String msg=PhoneUtil.vailterTelPhone(value);
		if(null==msg){
			return true;
		}else{
			throw new MyStrException(msg);
		}
	}
	
	
}
