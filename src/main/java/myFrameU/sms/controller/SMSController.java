package myFrameU.sms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.sms.entity.SMSRecord;
import myFrameU.sms.entity.SmsRecordOther;
import myFrameU.sms.init.InitMavenImpl;
import myFrameU.sms.sdkN.MySMSService;
import myFrameU.sms.sdkN.entity.YEEntity;
import myFrameU.sms.util.SendSms;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class SMSController extends FatherController {
	
	//暂时只考虑每次给一个号码发送短信
	@RequestMapping(value={"sms/send","admin/sms/send","user/sms/send","shop/sms/send"})
	public void send(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String sdkMtType=req.getParameter(myFrameU.sms.init.InitMavenImpl.ic.getSdkMtType());
		
		if(null!=sdkMtType && !sdkMtType.equals("")){
			String content = myFrameU.sms.util.CreateContentUtil.getContentBySdkMtType(sdkMtType);
			String telPhones = form.getString(InitMavenImpl.ic.getTelPhones());
			String vaiPhone=PhoneUtil.vailterTelPhone(telPhones);
			if(null==vaiPhone){
				if(null!=content && !content.equals("")){
					/*List<SmsRecordOther> smsOthers=form.getListFromJSONStr(InitMavenImpl.ic.getSmsOthers(), SmsRecordOther.class);
					SendSms.sdk_mt_simple(vaiPhone, content, aBiz, sdkMtType, smsOthers);*/
					
				}
			}else{
				throw new MyStrException("手机号码不正确");
			}
			System.out.println("发送短信成功【短信号码="+telPhones+",内容="+content+"】");
			String status = myFrameU.sms.init.InitMavenImpl.ic.getStatus();
			if(null!=status && status.equals("open")){
				List<SmsRecordOther> smsOthers=form.getListFromJSONStr(InitMavenImpl.ic.getSmsOthers(), SmsRecordOther.class);
				if(null!=telPhones && null!=content && !telPhones.equals("") && !content.equals("")){
					if(null==vaiPhone){
						SendSms.sdk_mt(telPhones, content, aBiz, sdkMtType, smsOthers);
					}
				}
			}
			renderData(res, "content="+content);
		}
	}
	
	//暂时只考虑每次给一个号码发送短信
	@RequestMapping(value={"admin/sms/sendSMS"})
	public void sendSMS(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String content = form.getString("content");
		String telPhones = form.getString("telPhones");
		if(null!=content && null!=telPhones && !content.equals("") && !telPhones.equals("")){
			int len = content.length();
			if(len>=65){
				throw new MyStrException("对不起，发送内容不能超过65个字符。");
			}else{
				content = content
						+ myFrameU.sms.init.InitMavenImpl.ic
								.getSuffix();
				
				String vaiPhone=PhoneUtil.vailterTelPhone(telPhones);
				if(null==vaiPhone){
					String status = myFrameU.sms.init.InitMavenImpl.ic.getStatus();
					if(null!=status && status.equals("open")){
						List<SmsRecordOther> smsOthers=form.getListFromJSONStr(InitMavenImpl.ic.getSmsOthers(), SmsRecordOther.class);
						SendSms.sdk_mt(telPhones, content, aBiz,  "admin", smsOthers);
					}
				}else{
					throw new MyStrException("手机号码不正确");
				}
			}
		}else{
			throw new MyStrException("请填写内容和选择发送对象");
		}
		
	}
	
	//验证验证码是否正确
	@RequestMapping(value={"admin/sms/yzm","shop/sms/yzm","user/sms/yzm","sms/yzm"})
	public void verificationYZM(HttpServletRequest req,HttpServletResponse res){
		String errorMessage="error";
		SDynaActionForm form=getSDynaActionForm(req);
		String yzm_request=form.getString(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma());
		String yzm_session=(String) req.getSession().getAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma());
		if(null!=yzm_session){
			if(yzm_session.equals(yzm_request)){
				errorMessage="ok";
			}else{
			}
		}else{
		}
		renderData(res, errorMessage);
	}
	
	
	
	@RequestMapping(value={"admin/sms/findSMSRecords","shop/sms/findSMSRecords","user/sms/findSMSRecords"})
	public String findSMSRecords(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String forwardPage = form.getString("forwardPage");
		String orderBy = form.getString("orderBy");
		String queryArgs = form.getString("queryArgs");
		
		aBiz.findEntitysByArgs(
				SMSRecord.class, 
				EntityTableUtil.tableName(SMSRecord.class.getName()), 
				queryArgs, orderBy, null, true, 0, "smsRecordList", req);
		
		
		YEEntity yee = MySMSService.getYE();
		if(null!=yee){
			req.setAttribute("balance", yee.getOverage());
		}
		return this.getForward("systools/smsRecordList", req);
	}
	
	
	
	//查询余额
	@RequestMapping(value={"admin/sms/findSMSInfo"})
	public String findSMSInfo(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String forwardPage = form.getString("forwardPage");
		YEEntity yee = MySMSService.getYE();
		if(null!=yee){
			req.setAttribute("balance", yee.getOverage());
			System.out.println("还有短信余额："+yee.getOverage());
		}
		return getForward(forwardPage, "sdkInfo");
	}
	
	//查询余额
	@RequestMapping(value="admin/sms/toSend")
	public String toSend(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		String forwardPage = form.getString("forwardPage");
		YEEntity yee = MySMSService.getYE();
		if(null!=yee){
			req.setAttribute("balance", yee.getOverage());
			System.out.println("还有短信余额："+yee.getOverage());
		}
		return this.getForward("systools/smsSend", req);
		
	}
	
	//查询余额
	@RequestMapping(value="admin/sms/smsRules")
	public String smsRules(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form=getSDynaActionForm(req);
		return "manage/admin/systools/smsRules";
		
	}
	
	
	
}
