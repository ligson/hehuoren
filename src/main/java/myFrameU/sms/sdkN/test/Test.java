package myFrameU.sms.sdkN.test;

import myFrameU.sms.sdkN.util.SmsClientAccessTool;
import myFrameU.sms.sdkN.util.SmsClientKeyword;
import myFrameU.sms.sdkN.util.SmsClientOverage;

public class Test {

	public static String url = "http://118.145.18.144:7777/sms.aspx";
	public static String userid = "45";
	public static String account = "njsy";
	public static String password = "abcd1234";
	public static String checkWord = "这个字符串中是否包含了屏蔽字";

	public static void main(String[] args) {
		
		keyword();
		// overage();
	}

	public static void keyword() {

		String keyword = SmsClientKeyword.queryKeyWord(url, userid, account,
				password, checkWord);
		System.out.println(keyword);
	}

	public static void overage() {

		String overage = SmsClientOverage.queryOverage(url, userid, account,
				password);
		System.out.println(overage);
	}

	public static void test() {
		String send = SmsClientAccessTool.getInstance().doAccessHTTPPost(
				"http://118.145.30.35/sms.aspx", "", "utf-8");
		System.out.println(send);
	}
}
