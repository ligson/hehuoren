package myFrameU.sms.sdkN;

import myFrameU.sms.init.InitConfig;
import myFrameU.sms.sdkN.entity.SendResultEntity;
import myFrameU.sms.sdkN.entity.YEEntity;
import myFrameU.sms.sdkN.util.SmsClientOverage;
import myFrameU.sms.sdkN.util.SmsClientSend;
import myFrameU.util.commonUtil.xml.XMLFormat;

public class MySMSService {
	private static InitConfig ic = myFrameU.sms.init.InitMavenImpl.ic;
	
	public static String url = ic.getServiceURL();
	public static String userid = ic.getUserId();
	public static String account = ic.getAccount();
	public static String password = ic.getPassword();
	public static String suffix=ic.getSuffix();
	//public static String checkWord = "这个字符串中是否包含了屏蔽字";
	
	
	
	//发送短信
		public static SendResultEntity send(String content,String telPhone){
			System.out.println("调用最底层的发送短信啦................................."+content);
			SendResultEntity sre= null;
			/**
			 * <?xml version="1.0" encoding="utf-8" ?><returnsms>
				 <returnstatus>Success</returnstatus>
				 <message>ok</message>
				 <remainpoint>24</remainpoint>
				 <taskID>490742</taskID>
				 <successCounts>1</successCounts></returnsms>
			 */
			String contentS=null;
			if(content.endsWith(suffix)){
				contentS=content;
			}else{
				contentS=content+suffix;
			}
			String result = SmsClientSend.sendSms(url, userid, account, password, telPhone, contentS);
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
