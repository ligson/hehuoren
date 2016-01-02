package myFrameU.util.commonUtil.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

	public static void main(String[] args) {
		System.out.println(isEnglish("sssss23ss"));
	}

	// 是否是英文字符串
	public static boolean isEnglish(String str) {
		String regex = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		boolean b = match.matches();
		return b;
	}

	// 是否是数字
	public static boolean isNumber(String str) {
		String regex = "^[0-9]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(str);
		boolean b = match.matches();
		return b;
	}

	// 判断一个字符串是否全部为中文，用来验证实名认证的姓名是否格式正确
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!isChinese(c)) {
				return false;
			}
		}
		return true;
	}

	// 判断一个字符串是否含有中文
	public static boolean haveChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}
	
	
	
	public static int getSubNumber(String desc, String reg) {
	     Pattern p = Pattern.compile(reg);
	     Matcher m = p.matcher(desc);
	     int count = 0;//记录个数
	     while(m.find()){
	           count++;
	     }
	     return count;
	} 
	
	
}
