package myFrameU.weixin.pay.controller;

import hhr.message.MessageUtil;
import hhr.order.biz.OrderBizI;
import hhr.order.entity.Order;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.account.biz.AccountBizI;
import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.admin.entity.Admin;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.twoDimensional.QRCodeEventsZXING;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.weixin.pay.entity.WxPayBackEntity;
import myFrameU.weixin.pay.entity.WxPayJSAPINeedEntity;
import myFrameU.weixin.pay.entity.WxPaySucBackEntity;
import myFrameU.weixin.pay.service.WXPayService;
import myFrameU.weixin.pay.util.CreateJSAPIPayStr;
import myFrameU.weixin.pay.util.ReceiveXmlProcess;
import myFrameU.weixin.pay.util.WXPayConstant;
import myFrameU.weixin.pay.util.WXPayUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class WXPayController extends FatherController {
	@Autowired
	private AccountBizI accountBiz;
	public AccountBizI getAccountBiz() {
		return accountBiz;
	}
	public void setAccountBiz(AccountBizI accountBiz) {
		this.accountBiz = accountBiz;
	}
	@Autowired
	private OrderBizI orderBiz;
	public OrderBizI getOrderBiz() {
		return orderBiz;
	}
	public void setOrderBiz(OrderBizI orderBiz) {
		this.orderBiz = orderBiz;
	}
	@RequestMapping(value={"/weixin/pay/test/pay","/weixin/pay/pay"})
	public String addWXOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		//是充值下单，还是支付订单,根据out_trade_no查询，看看到底是充值的ai，还是order
		
		
		
		String wxId = form.getString("wxId");
		String out_trade_no = form.getString("out_trade_no");
		String payType = form.getString("payType");
		String total_fee = form.getString("total_fee");
		String body = form.getString("body");
		String pcOrWrap=form.getString("pcOrWrap");
		
		
		/*String wxId="o9WE8uGHLCF15kQaFAjBKSdiNsyg";
		String out_trade_no="1436714639647";
		String payType="JSAPI";
		String total_fee="1";
		String attach="";
		String body="test";*/
		String chongzhiOrPayOrder=WXPayConstant.chongzhiOrPayOrder.PAYORDER.toString();
		AccountItem ai = null;
		Order order = null;
		if(null!=wxId && !wxId.equals("") && null!=out_trade_no && !out_trade_no.equals("")
				&& null!=payType && !payType.equals("") && null!=total_fee && !total_fee.equals("")
				&& null!=body && !body.equals("")
				){
			float priceFL=new Float(total_fee).floatValue();
			int price=WXPayUtil.priceYUAN2FEN(priceFL);
			//int price = new Integer(total_fee);
			if(price>0){
				WXPayService wxps = new WXPayService();
				WxPayBackEntity wpbe=wxps.addWXPayOrder(wxId,out_trade_no, payType, 
						price+"", "", body);
				
				
				if(null!=wpbe){
					String prepay_id=wpbe.getPrepay_id();
					if(null!=prepay_id && !prepay_id.equals("")){
						//成功
						order = (Order)aBiz.findObjectById("from Order as o where o.markedNum=?", new Object[]{out_trade_no});
						if(null==order){
							ai = (AccountItem)aBiz.findObjectById("from AccountItem as ai where ai.markedNum=?", new Object[]{out_trade_no});
							if(null!=ai){
								chongzhiOrPayOrder=WXPayConstant.chongzhiOrPayOrder.CHONGZHI.toString();
							}
						}
						
						if(EnumUtil.equalsE(WXPayConstant.chongzhiOrPayOrder.CHONGZHI, chongzhiOrPayOrder)){
							ai.setOuterMarkedNum(prepay_id);
							aBiz.modifyObject(ai);
							req.setAttribute("accountItem", ai);
						}else if(EnumUtil.equalsE(WXPayConstant.chongzhiOrPayOrder.PAYORDER, chongzhiOrPayOrder)){
							order.setOuterType(Order.OUTERTYPE.WEIXIN.toString());
							order.setOuterMarkedNum(prepay_id);
							aBiz.modifyObject(order);
							req.setAttribute("order", order);
						}
						WxPayJSAPINeedEntity wpjsapiNeed = CreateJSAPIPayStr.createWxPayJSAPINeedEntity(prepay_id);
						req.setAttribute("wxPayJSAPINeedEntity", wpjsapiNeed);
						//JSONObject jo=CreateJSAPIPayStr.createJSAPIPayStr(prepay_id);
						//System.out.println("统一支付成功后，需要我们自己返回给页面jsapi需要用到的json数据"+jo.toString());
						//this.renderData(res, jo.toString());
						
						if(payType.equals(WXPayUtil.WXPAYTYPE.NATIVE.toString())){
							//扫码支付方式，需要生成二维码
							String code_url=wpbe.getCode_url();
							if(null!=code_url && !code_url.equals("")){
								String imagePathName = "/img/pay/"+out_trade_no+".png";
								QRCodeEventsZXING.createImg(PathUtil.getPhysicsPath()+imagePathName,
										code_url, 400);
								
							}
						}
						
					}
				}
			}
		}
		if(EnumUtil.equalsE(WXPayConstant.chongzhiOrPayOrder.CHONGZHI, chongzhiOrPayOrder)){
			String className = ai.getWhoclassName();
			if(null!=className && !className.equals("")){
				if(className.equals(Shop.class.getName())){
					return "wrap/manage/shop/account/accountItemRecharge";
				}else if(className.equals(User.class.getName())){
					return "wrap/manage/user/account/accountItemRecharge";
				}
			}
		}else if(EnumUtil.equalsE(WXPayConstant.chongzhiOrPayOrder.PAYORDER, chongzhiOrPayOrder)){
			return "wrap/manage/user/order/payOrder";
		}
		return null;
	}
	
	@RequestMapping(value={"/weixin/pay/test/wxPayCallback","/weixin/pay/wxPayCallback"})
	public void wxPayCallback(HttpServletRequest req,HttpServletResponse res) throws Exception{
		/**
		 * 第一步：获取过来的值
		 * 第二步：解析过来的xml
		 * 
		 * 		获得返回的out_trade_no，根据这个值，查下是ai还是order
		 * 
		 * 第三步：自己的系统处理，
		 * 		1、操作账号余额
		 * 		2、将ai的callbackHaveddeal设置为true，表明回调处理过了
		 * 		3、发送充值成功微信模板信息。
		 * 		4、手机短信
		 * 
		 * 第四步：给微信返回数据
		 */
		//第一================================
		String xml=null;
		StringBuffer sb = new StringBuffer();
		InputStream is = req.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String s= "";
		while((s=br.readLine())!=null){
			sb.append(s);
		}
		xml = sb.toString();
		System.out.println(xml+"******************************这是回调从微信过来的数据");
		/**
		 * <xml>
		 * 	<appid><![CDATA[wx247c7e42bcde403d]]></appid>
		 * 	<bank_type><![CDATA[CMB_DEBIT]]></bank_type>
		 * 	<cash_fee><![CDATA[1]]></cash_fee>
		 * 	<fee_type><![CDATA[CNY]]></fee_type>
		 * 	<is_subscribe><![CDATA[Y]]></is_subscribe>
		 * 	<mch_id><![CDATA[1247893201]]></mch_id>
		 * 	<nonce_str><![CDATA[1vh528cgwapeb8z30er7su1i2mspqwr3]]></nonce_str>
		 * 	<openid><![CDATA[o9WE8uGHLCF15kQaFAjBKSdiNsyg]]></openid>
		 * 	<out_trade_no><![CDATA[1436871156744]]></out_trade_no>
		 * 	<result_code><![CDATA[SUCCESS]]></result_code>
		 * 	<return_code><![CDATA[SUCCESS]]></return_code>
		 * 	<sign><![CDATA[DA6C7B2B364CCFB1E90493D1E2C0CCC1]]></sign>
		 * 	<time_end><![CDATA[20150713185319]]></time_end>
		 * 	<total_fee>1</total_fee>
		 * 	<trade_type><![CDATA[JSAPI]]></trade_type>
		 * 	<transaction_id><![CDATA[1007980797201507130399782646]]></transaction_id>
		 *</xml>
		 * 
		 */
		String chongzhiOrPayOrder=WXPayConstant.chongzhiOrPayOrder.PAYORDER.toString();
		if(null!=xml){
			//第二===========================================
			WxPaySucBackEntity e = ReceiveXmlProcess.getMsgEntity(xml, WxPaySucBackEntity.class);
			
			AccountItem ai=null;
			Order order =null;
			//第三=============================================
			String openId = e.getOpenid();
			if(null!=openId && !openId.equals("")){
				String markedNum = e.getOut_trade_no();
				if(null!=markedNum && !markedNum.equals("")){
					order = (Order)aBiz.findObjectById("from Order as o where o.markedNum=?", new Object[]{markedNum});
					if(null==order){
						ai = (AccountItem)aBiz.findObjectById("from AccountItem as ai where ai.markedNum=?", new Object[]{markedNum});
						if(null!=ai){
							chongzhiOrPayOrder=WXPayConstant.chongzhiOrPayOrder.CHONGZHI.toString();
						}
					}
					if(EnumUtil.equalsE(WXPayConstant.chongzhiOrPayOrder.CHONGZHI, chongzhiOrPayOrder)){
						int accountId=ai.getAccount().getId();
						synchronized(getLock(ai.getWhoclassName(),ai.getWhoId())){
							Account a = accountBiz.account_findAccount(ai.getWhoclassName(),ai.getWhoId());
							
							if(null!=a){
								if(!ai.getCallbackHaveddeal()){
									//还没有处理
									String itemType=ai.getItemType();
									if(EnumUtil.equalsE(AccountItem.ITEMTYPE.RECHARGE, itemType)){
										//如果这个accountItem是充值记录
										//把ai的状态修改成finish的
										ai.setStatus(AccountItem.STATUS.FINISH.toString());
										//a.setTotalPrice(a.getTotalPrice()+ai.getPrice());
										//a.setAvailablePrice(a.getAvailablePrice()+ai.getPrice());
										ai.setCallbackHaveddeal(true);
										ai.setOuterMarkedNum2(e.getTransaction_id());
										aBiz.modifyObject(ai);
										//aBiz.modifyObject(a);
										
										accountBiz.account_computingAccountPrice(a);
										
										
										HashMap<String,Object> args=new HashMap<String,Object>();
										args.put("accountItem", ai);
										args.put("account", a);
										args.put("wxId", openId);
										MessageUtil.sendMssage(MessageUtil.MssageTYPE.RECHARGE_SUCCESS.toString(),
												MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
												args,aBiz);
										
										
										
										//给管理员发来款通知
										HashMap<String,Object> args_admin=new HashMap<String,Object>();
										args_admin.put("accountItem", ai);
										args_admin.put("account", a);
										args_admin.put("wxId", openId);
										MessageUtil.sendMssage(MessageUtil.MssageTYPE.RECHARGE_SUCCESS_TOADMIN.toString(),
												MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
												args_admin,aBiz);
										
										
									}
								}
							}
						}
					}else if(EnumUtil.equalsE(WXPayConstant.chongzhiOrPayOrder.PAYORDER, chongzhiOrPayOrder)){
						if(null!=order){
							//修改order的状态、outerMarkedNum1，payDate
							order.setStatus(Order.STATUS.PAYED.toString());
							order.setPayDate(new Date());
							order.setOuterMarkedNum1(e.getTransaction_id());
							
							
							User newUser = orderBiz.payOrder_3(order, accountBiz, uICacheManager);
							req.getSession().setAttribute("myUser", newUser);
							
							
							
						}
					}
					
					
					
				}
			}
		}
		
		
		
		
		
		
		//第四：===============================
		StringBuffer returnTxtSb = new StringBuffer();
		returnTxtSb.append("<xml>");
		returnTxtSb.append("<return_code><![CDATA[SUCCESS]]></return_code>");
		returnTxtSb.append("<return_msg><![CDATA[OK]]></return_msg>");
		returnTxtSb.append("</xml>");
		
		this.renderData(res, returnTxtSb.toString());
	}
	
	
	//==========================================================
	/**
	 * 扫码支付：
	 * 		第一步：生成二维码
	 * 		第二步：在回调中接收参数，并调用统一下单接口，在微信系统内部生成订单。
	 * 		第三步：jsapi支付。
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	//=============================================================
	private String getLock(String whoClassName,int whoId){
		String lock=null;
		if(whoClassName.equals(Admin.class.getName())){
			lock=Account.adminAccountLock_pre+""+whoId;
		}else if(whoClassName.equals(User.class.getName())){
			lock=Account.userAccountLock_pre+""+whoId;
		}else if(whoClassName.equals(Shop.class.getName())){
			lock=Account.shopAccountLock_pre+""+whoId;
		}
		return lock;
	}
	
	//======================================================
	@RequestMapping(value={"/weixin/pay/test/toPayAi","/weixin/pay/toPayAi"})
	public String toRecharge(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer accountItemId = form.getInteger("accountItemId");
		AccountItem ai=null;
		if(null!=accountItemId && accountItemId.intValue()!=0){
			 ai = (AccountItem)aBiz.findObjectById("from AccountItem as ai where ai.id=?", new Object[]{accountItemId});
			if(null!=ai){
				req.setAttribute("accountItem", ai);
				WxPayJSAPINeedEntity wpjsapiNeed = CreateJSAPIPayStr.createWxPayJSAPINeedEntity(ai.getOuterMarkedNum());
				req.setAttribute("wxPayJSAPINeedEntity", wpjsapiNeed);
			}
		}
		if(null!=ai){
			String className = ai.getWhoclassName();
			if(null!=className && !className.equals("")){
				if(className.equals(Shop.class.getName())){
					return "wrap/manage/shop/account/accountItemRecharge";
				}else if(className.equals(User.class.getName())){
					return "wrap/manage/user/account/accountItemRecharge";
				}
			}
		}
		return null;
	}
	
	
	@RequestMapping(value={"/weixin/pay/test/payAi","/weixin/pay/payAi"})
	public void payAccountItem(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer accountItemId = form.getInteger("accountItemId");
		if(null!=accountItemId && accountItemId.intValue()!=0){
			AccountItem ai = (AccountItem)aBiz.findObjectById("from AccountItem as ai where ai.id=?", new Object[]{accountItemId});
			if(null!=ai){
				String outerType = ai.getOuterType();
				if(EnumUtil.equalsE(AccountItem.OUTERTYPE.WEIXIN, outerType)){
					String outerMak=ai.getOuterMarkedNum();
					if(null!=outerMak && !outerMak.equals("")){
						JSONObject jo=CreateJSAPIPayStr.createJSAPIPayStr(outerMak);
						this.renderData(res, jo.toString());
					}
				}
			}
		}
	}
	
	//====================================================================================
	@RequestMapping(value={"/weixin/pay/test/toPayOrder","/weixin/pay/toPayOrder"})
	public String toPayOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer orderId = form.getInteger("orderId");
		Order order=null;
		if(null!=orderId && orderId.intValue()!=0){
			 order = (Order)aBiz.findObjectById("from Order as ai where ai.id=?", new Object[]{orderId});
			if(null!=order){
				req.setAttribute("order", order);
				WxPayJSAPINeedEntity wpjsapiNeed = CreateJSAPIPayStr.createWxPayJSAPINeedEntity(order.getOuterMarkedNum());
				req.setAttribute("wxPayJSAPINeedEntity", wpjsapiNeed);
			}
		}
		if(null!=order){
			return "wrap/manage/user/order/payOrder";
		}
		return null;
	}
	
	
	
	@RequestMapping(value={"/weixin/pay/test/payOrder","/weixin/pay/payOrder"})
	public void payOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer orderId = form.getInteger("orderId");
		if(null!=orderId && orderId.intValue()!=0){
			Order  order = (Order)aBiz.findObjectById("from Order as ai where ai.id=?", new Object[]{orderId});
			if(null!=order){
				String outerType = order.getOuterType();
				if(EnumUtil.equalsE(Order.OUTERTYPE.WEIXIN, outerType)){
					String outerMak = order.getOuterMarkedNum();
					if(null!=outerMak && !outerMak.equals("")){
						JSONObject jo=CreateJSAPIPayStr.createJSAPIPayStr(outerMak);
						this.renderData(res, jo.toString());
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
}
