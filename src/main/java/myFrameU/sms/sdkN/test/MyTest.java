package myFrameU.sms.sdkN.test;

import myFrameU.sms.sdkN.entity.SendResultEntity;
import myFrameU.sms.sdkN.entity.YEEntity;
import myFrameU.sms.sdkN.util.SmsClientOverage;
import myFrameU.sms.sdkN.util.SmsClientSend;
import myFrameU.util.commonUtil.xml.XMLFormat;

public class MyTest {
	public static String url = "http://113.11.210.117:8802/sms.aspx";
	public static String userid = "45";
	public static String account = "njsy";
	public static String password = "abcd1234";
	public static String checkWord = "这个字符串中是否包含了屏蔽字";
	
	public static void main(String[] args) {
		//System.out.println(getYE());
		System.out.println(send());
		
	}
	
	//发送短信
	public static SendResultEntity send(){
		SendResultEntity sre= null;
		/**
		 * <?xml version="1.0" encoding="utf-8" ?><returnsms>
			 <returnstatus>Success</returnstatus>
			 <message>ok</message>
			 <remainpoint>24</remainpoint>
			 <taskID>490742</taskID>
			 <successCounts>1</successCounts></returnsms>
		 */
		
		String result = SmsClientSend.sendSms(url, userid, account, password, "18754462512", "测试短信内容[艺藏家]");
		if(null!=result && !result.equals("")){
			sre = (SendResultEntity) XMLFormat.xml2Object(result,SendResultEntity.class );
			if(null!=sre){
				System.out.println(sre.getMessage()+"===="+sre.getRemainpoint());
			}
		}
		return sre;
	}
	
	//查询余额
	public static YEEntity getYE(){
		YEEntity e = null;
		/**
		 * <?xml version="1.0" encoding="utf-8" ?>
		 * 	<returnsms>
			 	<returnstatus>Sucess</returnstatus>
			 	<message></message>
			 	<payinfo>预付费</payinfo>
			 	<overage>25</overage>
			 	<sendTotal>320</sendTotal>
			</returnsms>
		 */
		String result = SmsClientOverage.queryOverage(url, userid, account, password);
		if(null!=result){
			e = (YEEntity)XMLFormat.xml2Object(result, YEEntity.class);
			if(null!=e){
				System.out.println(e.getMessage()+"===="+e.getOverage());
			}
		}
		return e;
	}

}
