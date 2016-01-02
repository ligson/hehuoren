package myFrameU.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.shop.entity.ShopUser;
import myFrameU.spring.mvc.FatherController;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.text.PhoneUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserAndShopPublicController extends FatherController{
	@RequestMapping(value={"getPwd_forgetPwd"})
	public void getPwd_forgetPwd(HttpServletRequest req,HttpServletResponse res) throws Exception{
		String msg = "";
		boolean result = false;
		String telPhone = req.getParameter("telPhone");
		String role = req.getParameter("role");
		if(null!=role && null!=telPhone && !telPhone.equals("")){
			String verPhone = PhoneUtil.vailterTelPhone(telPhone);
			if(null==verPhone){
				myFrameU.sms.util.Util.verYZM(req);
				if(role.equals("shop")){
					Integer shopId = (Integer)aBiz.j_queryObject("select phone from shop where phone=?", new Object[]{telPhone});
					if(null!=shopId && shopId.intValue()!=0){
						ShopUser su = (ShopUser)aBiz.j_queryObject("from ShopUser where shopId=?", new Object[]{shopId});
						if(null!=su){
							msg="ok-您的账号是："+su.getName()+",密码是："+su.getPassword();
							result=true;
						}
					}else{
						msg="该手机号没有注册";
					}
				}else if(role.equals("user")){
					User user = (User)aBiz.findObjectById("from User as u where u.telPhone=?", new Object[]{telPhone});
					if(null!=user){
						msg="ok-您的密码是："+user.getPassword();
						result=true;
					}else{
						msg="抱歉，该手机号没有注册账号";
					}
				}
			}else{
				throw new MyStrException(verPhone);
			}
		}
		if(result){
			renderData(res, msg);
		}else{
			throw new MyStrException(msg);
		}
	}
}
