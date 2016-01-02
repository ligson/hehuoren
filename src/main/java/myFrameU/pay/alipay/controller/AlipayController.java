package myFrameU.pay.alipay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.account.biz.AccountBizImpl;
import myFrameU.pay.alipay.com.alipay.config.AlipayConfig;
import myFrameU.pay.alipay.com.alipay.util.AlipayNotify;
import myFrameU.pay.alipay.com.alipay.util.AlipaySubmit;
import myFrameU.pay.alipay.com.alipay.util.ZFBCallbackEntity;
import myFrameU.pay.alipay.init.InitMavenImpl;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.httpUtil.path.PathUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/pay/alipay")
public class AlipayController extends FatherController {
	private AccountBizImpl accountBiz;
	public AccountBizImpl getAccountBiz() {
		return accountBiz;
	}

	public void setAccountBiz(AccountBizImpl accountBiz) {
		this.accountBiz = accountBiz;
	}

	@RequestMapping(value = "/alipayapi")
	public void alipayapi(HttpServletRequest req, HttpServletResponse res) {
		SDynaActionForm form = getSDynaActionForm(req);
		String notify_url = PathUtil.getBasePath_no(req)+"/pay/alipay/notifyUrl";
		String return_url=PathUtil.getBasePath_no(req)+"/pay/alipay/returnUrl";
		
		/*String notify_url = InitMavenImpl.ic.getAlipay_notify_url();
		String return_url = InitMavenImpl.ic.getAlipay_return_url();*/
		// 支付类型
		String payment_type = "1";
		// 卖家支付宝帐户必填
		String seller_email = form.getString("WIDseller_email");
		// 商户订单号必填
		String out_trade_no = form.getString("WIDout_trade_no");
		// 订单名称必填
		String subject = form.getString("WIDsubject");

		// 付款金额必填
		String total_fee = form.getString("WIDtotal_fee");

		// 订单描述
		String body = form.getString("WIDbody");

		// 商品展示地址
		String show_url = form.getString("WIDshow_url");
		// 防钓鱼时间戳
		String anti_phishing_key = "";
		// 客户端的IP地址
		String exter_invoke_ip = "";

		// 买家支付宝帐户
		String buyer_email = form.getString("WIDbuyer_email");

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组

		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

		if (null != buyer_email) {
			sParaTemp.put("buyer_email", buyer_email);
			sParaTemp.put("default_login", "Y");
		}

		// 建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		try {
			res.getWriter().println(sHtmlText);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/returnUrl")
	public void returnUrl(HttpServletRequest req, HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		PrintWriter out = res.getWriter();
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = req.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
			System.out.println(valueStr+"##################################");
			
			///////////////////////////获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			//String out_trade_no = new String(form.getString("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			//String trade_no = new String(form.getString("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			//String trade_status = new String(form.getString("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			///////////////////////////获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			String buyer_email=params.get("buyer_email");//买家支付宝账号
			String buyer_id=params.get("buyer_id");//买家ID
			String trade_no=params.get("trade_no");//交易号，就是支付宝里的订单号
			String gmt_payment=params.get("gmt_payment");//交易支付时间
			String total_fee=params.get("total_fee");//价格
			String quantity=params.get("quantity");//商品数量
			String trade_status=params.get("trade_status");//订单状态
			String out_trade_no=params.get("out_trade_no");//咱自己的订单号
			
			
			//将param组装成ZFBCallbackEntity
			ZFBCallbackEntity zfbcbe=new ZFBCallbackEntity();
			zfbcbe.setBuyer_email(buyer_email);
			zfbcbe.setBuyer_id(buyer_id);
			zfbcbe.setTrade_no(trade_no);
			zfbcbe.setGmt_payment(gmt_payment);
			zfbcbe.setTotal_fee(total_fee);
			zfbcbe.setQuantity(quantity);
			zfbcbe.setTrade_status(trade_status);
			zfbcbe.setOut_trade_no(out_trade_no);
			
			
			
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					//////////////////acBiz.trans_account_zfb_callback(zfbcbe);
					accountBiz.alipayCallback(zfbcbe);
				}
				
				//该页面可做页面美工编辑
				out.println("验证成功<br />");
				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{
				//该页面可做页面美工编辑
				out.println("验证失败");
			}
		}
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/notifyUrl")
	public void notifyUrl(HttpServletRequest req, HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		PrintWriter out = res.getWriter();
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = req.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
			System.out.println(valueStr+"##################################");
			
			///////////////////////////获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			//String out_trade_no = new String(form.getString("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			//String trade_no = new String(form.getString("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			//String trade_status = new String(form.getString("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			///////////////////////////获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			
			String buyer_email=params.get("buyer_email");//买家支付宝账号
			String buyer_id=params.get("buyer_id");//买家ID
			String trade_no=params.get("trade_no");//交易号，就是支付宝里的订单号
			String gmt_payment=params.get("gmt_payment");//交易支付时间
			String total_fee=params.get("total_fee");//价格
			String quantity=params.get("quantity");//商品数量
			String trade_status=params.get("trade_status");//订单状态
			String out_trade_no=params.get("out_trade_no");//咱自己的订单号
			
			
			//将param组装成ZFBCallbackEntity
			ZFBCallbackEntity zfbcbe=new ZFBCallbackEntity();
			zfbcbe.setBuyer_email(buyer_email);
			zfbcbe.setBuyer_id(buyer_id);
			zfbcbe.setTrade_no(trade_no);
			zfbcbe.setGmt_payment(gmt_payment);
			zfbcbe.setTotal_fee(total_fee);
			zfbcbe.setQuantity(quantity);
			zfbcbe.setTrade_status(trade_status);
			zfbcbe.setOut_trade_no(out_trade_no);
			
			
			
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			if(verify_result){//验证成功
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//该种交易状态只在两种情况下出现
					//1、开通了普通即时到账，买家付款成功后。
					//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
					accountBiz.alipayCallback(zfbcbe);
					
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
						
					//注意：
					//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
					accountBiz.alipayCallback(zfbcbe);
				}
				out.println("success");	//请不要修改或删除
			}else{
				//该页面可做页面美工编辑
				out.println("fail");
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
