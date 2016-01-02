package myFrameU.weixin.jssdk.pay.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import myFrameU.spring.mvc.FatherController;
@Controller("/weixin/pay/")
public class WXPayController extends FatherController {
	@RequestMapping("toPay")
	public String toPay(HttpServletRequest req,HttpServletResponse res) throws Exception{
		return "wrap/manage/user/account/wxcz";
	}
}
