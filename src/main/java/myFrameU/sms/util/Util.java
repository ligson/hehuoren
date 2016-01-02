package myFrameU.sms.util;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import myFrameU.exception.exception.MyStrException;

public class Util {
	//生成验证码吗
	public static String createYZM(){
		Random rnd = new Random();
		int num = rnd.nextInt(89999) + 100000;
		System.out.println(num);
		return num+"";
	}
	
	public static void verYZM(HttpServletRequest req) throws Exception{
		String yzm = req.getParameter(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma());
		if(null!=yzm && !yzm.equals("")){
			int len = yzm.length();
			if(len==6){
				String sessionYZM = (String) req.getSession().getAttribute(myFrameU.sms.init.InitMavenImpl.ic.getSmsYanzhengma());
				if(yzm.equals(sessionYZM)){
					
				}else{
					throw new MyStrException("请输入正确的验证码");	
				}
			}else{
				throw new MyStrException("请输入正确的验证码");	
			}
		}else{
			throw new MyStrException("请输入验证码");
		}
	}
	
	
}
