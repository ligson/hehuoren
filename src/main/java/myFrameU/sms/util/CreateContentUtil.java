package myFrameU.sms.util;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import myFrameU.util.httpUtil.filter.RequestFilter;

public class CreateContentUtil {

	// 根据业务类型，返回相应的短信内容
	public static String getContentBySdkMtType(String sdkMtType) {
		String content = null;
		try {
			String myCreateContentUtil = myFrameU.sms.init.InitMavenImpl.ic
					.getMyCreateContentUtil();
			if (null != myCreateContentUtil) {
				if (!myCreateContentUtil.equals("")) {
					HttpServletRequest request = (HttpServletRequest) RequestFilter.threadLocal.get().get("request");
					Class c = Class.forName(myCreateContentUtil);
					Class types[] = new Class[1];
					types[0] = Class
							.forName("javax.servlet.http.HttpServletRequest");
					Method m = c.getMethod(sdkMtType, types);
					content = (String) m.invoke(c.newInstance(), request);
					/*if (null != content) {
						content = content+ myFrameU.sms.init.InitMavenImpl.ic.getSuffix();
					}
					*/
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}

	// 一些常用的
	// 注册时候发送验证码
	public String regist(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String yzm = myFrameU.sms.util.Util.createYZM();
		request.getSession().setAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma(), yzm);
		
		content.append("尊敬的客户，您注册的验证码为").append(yzm)
				.append("，请于30分钟内输入，切勿将验证码泄露于他人。");
		return content.toString();
	}

	
	// 修改密码的验证码
	public String forgetPassword(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String yzm = myFrameU.sms.util.Util.createYZM();
		request.getSession().setAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma(), yzm);
		content.append("您忘记密码,查看登录密码的验证码是").append(yzm)
				.append("，请于30分钟内输入，切勿将验证码泄露于他人。");
		
		System.out.println(content.toString()+"=============");
		
		return content.toString();
	}
	
	
	// 修改密码的验证码
	public String modifyPassword(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String yzm = myFrameU.sms.util.Util.createYZM();
		request.getSession().setAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma(), yzm);
		content.append("您忘记密码,修改成新密码时的验证码是").append(yzm)
				.append("，请于30分钟内输入，切勿将验证码泄露于他人。");
		
		System.out.println(content.toString()+"=============");
		
		return content.toString();
	}

	// 充值成功提醒短信
	public String accountRecharge(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String price=request.getParameter("price");
		content.append("您成功充值").append(price).append("元。");
		return content.toString();
	}

	
	
	// 修改绑定手机
	public String bindingTelphone(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String yzm = myFrameU.sms.util.Util.createYZM();
		request.getSession().setAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma(), yzm);
		
		content.append("尊敬的客户，您绑定手机的验证码为").append(yzm)
				.append("，请于30分钟内输入，切勿将验证码泄露于他人。");
		return content.toString();
	}
	
	// 修改支付密码
	public String modifyZhipassword(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String yzm = myFrameU.sms.util.Util.createYZM();
		request.getSession().setAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma(), yzm);
		
		content.append("尊敬的客户，您修改财务密码的验证码为").append(yzm)
				.append("，请于30分钟内输入，切勿将验证码泄露于他人。");
		return content.toString();
	}
	// 修改支付密码
	public String modifyZhifubao(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String yzm = myFrameU.sms.util.Util.createYZM();
		request.getSession().setAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma(), yzm);
		
		content.append("尊敬的客户，您修改支付宝账号的验证码为").append(yzm)
				.append("，请于30分钟内输入，切勿将验证码泄露于他人。");
		return content.toString();
	}
			
	// 提现申请审核通过。。。
	public String applyTxOk(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		content.append("尊敬的客户，您的提现申请已经审核通过，请注意查收您的支付宝账号余额");
		return content.toString();
	}
			
	
	//查看财务密码
	public String lookAccountPwd(HttpServletRequest request) {
		StringBuffer content = new StringBuffer("");
		String yzm = myFrameU.sms.util.Util.createYZM();
		request.getSession().setAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma(), yzm);
		
		content.append("尊敬的客户，您查看财务密码的验证码为").append(yzm)
				.append("，请于30分钟内输入，切勿将验证码泄露于他人。");
		return content.toString();
	}
		
	
	
	
	
	
	
	
	
	
	
	

}
