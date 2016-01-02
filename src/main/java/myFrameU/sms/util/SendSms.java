package myFrameU.sms.util;

import java.util.Date;
import java.util.List;

import myFrameU.biz.AbstractBizI;
import myFrameU.sms.entity.SMSRecord;
import myFrameU.sms.entity.SmsRecordOther;
import myFrameU.sms.sdkN.MySMSService;
import myFrameU.sms.sdkN.entity.SendResultEntity;
import myFrameU.util.commonUtil.json.JSONUtils;

import com.mysql.fabric.xmlrpc.Client;

public class SendSms {

	
	public static String sdk_mt(String telPhones,String content,AbstractBizI aBiz,String sdkMtType,List<SmsRecordOther> smsOthers){
		try{
			SendResultEntity sre = MySMSService.send(content, telPhones);
			if(null!=sre){
				boolean suc=false;
				String message = sre.getMessage();
				if(null!=message && !message.equals("")){
					if(message.equals("ok")){
						suc=true;
					}
				}
				if(suc){
					if(myFrameU.sms.init.InitMavenImpl.ic.getSaveRecord()){
						String[] telPhones_array=null;
						if(telPhones.contains(",")){
							telPhones_array=telPhones.split(",");
						}else{
							telPhones_array=new String[]{telPhones};
						}
						Date now = new Date();
						if(null!=telPhones_array){
							int size = telPhones_array.length;
							String telPhone=null;
							SMSRecord sr=null;
							for(int i=0;i<size;i++){
								telPhone=telPhones_array[i];
								sr = new SMSRecord();
								sr.setContent(content);
								sr.setTelPhone(telPhone);
								
								if(null!=smsOthers){
									int sizeother = smsOthers.size();
									if(sizeother>0){
										String otherJSON=JSONUtils.toJSONString(smsOthers);
										sr.setSmsRecordOtherJSON(otherJSON);
									}
								}
								
								
								sr.setRelDate(now);
								sr.setSdkMtType(sdkMtType);
								aBiz.addObject(sr);
							}
						}
					}
				}else{
					System.out.print("发送失败！请查看webservice返回值对照表");
				}
			}
		}catch(Exception e){
			System.out.println("发送短信失败-异常");
			e.printStackTrace();
		}
		return "发送成功";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//sdk_mt(telPhone,content.toString(), "", "", "",aBiz,GetConstant.SDK_MT_TYPE_userRegist,"S",0,0,0);
	/*public static String sdk_mt(String telPhones,String content,String ext, String stime, String rrid,AbstractBizI aBiz,String sdkMtType,String sendOrRec,List<SmsRecordOther> smsOthers){
		try{
			Client client=new Client();
			String result_mt = client.mt(telPhones, content, "", stime, rrid);
			if(result_mt.startsWith("-")) {
				System.out.print("发送失败！返回值为："+result_mt+"请查看webservice返回值对照表");
				return "发送失败!-"+result_mt;
			}else{
				if(myFrameU.sms.init.InitMavenImpl.ic.getSaveRecord()){
					
					//输出返回标识，为小于19位的正数，String类型的。记录您发送的批次。
					System.out.print("发送成功，返回值为："+result_mt);
					String[] telPhones_array=null;
					if(telPhones.contains(",")){
						telPhones_array=telPhones.split(",");
					}else{
						telPhones_array=new String[]{telPhones};
					}
					Date now = new Date();
					if(null!=telPhones_array){
						int size = telPhones_array.length;
						String telPhone=null;
						SMSRecord sr=null;
						for(int i=0;i<size;i++){
							telPhone=telPhones_array[i];
							sr = new SMSRecord();
							sr.setContent(content);
							sr.setExt(ext);
							sr.setStime(stime);
							sr.setRrid(rrid);
							sr.setSdkResult(result_mt);
							sr.setTelPhone(telPhone);
							
							if(null!=smsOthers){
								int sizeother = smsOthers.size();
								if(sizeother>0){
									String otherJSON=JSONUtils.toJSONString(smsOthers);
									sr.setSmsRecordOtherJSON(otherJSON);
								}
							}
							
							
							sr.setRelDate(now);
							sr.setSdkMtType(sdkMtType);
							sr.setSendOrRec(sendOrRec);
							aBiz.addObject(sr);
						}
					}
				}
			}
		}catch(Exception e){
			System.out.println("发送短信失败-异常");
			e.printStackTrace();
		}
		return "发送成功";
	}*/
	
}
