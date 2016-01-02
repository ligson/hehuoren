package myFrameU.util.commonUtil.regular;

import java.util.regex.Pattern;

/**
 * 
 * 正则表达式的工具类
 *
 */
public class RegularExpressionUtil {
	//判断是否为数字
	public static boolean isNumeric1(String str){
		  Pattern pattern = Pattern.compile("[0-9]*");
		  return pattern.matcher(str).matches();
	}
}
