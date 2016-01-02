package hhr.order.controller;

import hhr.message.MessageUtil;
import hhr.order.biz.OrderBizI;
import hhr.order.entity.Cart;
import hhr.order.entity.CartItem;
import hhr.order.entity.Order;
import hhr.order.entity.OrderItem;
import hhr.order.entity.PickUpAddress;
import hhr.order.util.OrderUtil;
import hhr.order.util.PickUpAddressUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrame.quartz.MyJobManager;
import myFrameU.account.biz.AccountBizI;
import myFrameU.admin.entity.Admin;
import myFrameU.exception.exception.MyJSONException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.product.entity.ProductPrice;
import myFrameU.quartz.util.ScheduleJob;
import myFrameU.queryArgs.util.QueryArgsUtil;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;
import myFrameU.weixin.base.service.impl.AutoRegistAndLoginImpl;
import myFrameU.weixin.pay.util.WXPayUtil;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class OrderController extends FatherController {
	@Autowired
	private UserBizI userBiz;
	public UserBizI getUserBiz() {
		return userBiz;
	}
	public void setUserBiz(UserBizI userBiz) {
		this.userBiz = userBiz;
	}

	
	@Autowired
	private OrderBizI orderBiz;

	public OrderBizI getOrderBiz() {
		return orderBiz;
	}
	public void setOrderBiz(OrderBizI orderBiz) {
		this.orderBiz = orderBiz;
	}


	@Autowired
	private AccountBizI accountBiz;
	
	public AccountBizI getAccountBiz() {
		return accountBiz;
	}
	public void setAccountBiz(AccountBizI accountBiz) {
		this.accountBiz = accountBiz;
	}
	@RequestMapping(value={"/wrap/user/order/addOrder"})
	public void addOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		User user = (User)req.getSession().getAttribute("myUser");
		Cart myCart = (Cart)req.getSession().getAttribute("myCart");
		SDynaActionForm form = this.getSDynaActionForm(req);
		String shouhuoTelphone=form.getString("shouhuoTelphone");
		String shouhuoName=form.getString("shouhuoName");
		JSONObject jo = new JSONObject();
		jo.put("error", "");
		if(null!=user && null!=myCart){
			Order o = new Order();
			try{
				o=OrderUtil.fillShouhuoRen(req, accountBiz, o, user, shouhuoName, shouhuoTelphone);
			}catch(MyStrException e){
				throw new MyJSONException(e.getMessage());
			}
			
			o.setOrderPayType(Order.ORDERPAYTYPE.Unknown.toString());
			o.setMarkedNum(new Date().getTime()+"");
			o.setCreateDate(new Date());
			o.setMyAddressId(0);
			o.setPayDate(null);
			o.setRemarks(null);
			o.setStatus(Order.STATUS.WAITPAY.toString());
			o.setToHehuorenPrice(myCart.getToHehuorenPrice());
			o.setToWebPrice(myCart.getToWebPrice());
			o.setTotalCount(myCart.getTotalCount());
			o.setTotalPrice(myCart.getTotalPrice());
			o.setTuijianRenId(myCart.getTuijianRenId());
			o.setTujianRenName(myCart.getTujianRenName());
			o.setUserId(myCart.getUserId());
			o.setUserName(myCart.getUserName());
			o.setShouhuoName(shouhuoName);
			o.setShouhuoTelphone(shouhuoTelphone);
			
			
			HashSet<OrderItem> oiSet= new HashSet<OrderItem>();
			HashSet<CartItem> ciSet = (HashSet<CartItem>) myCart.getCartItemSet();
			if(null!=ciSet){
				int ciSize = ciSet.size();
				if(ciSize>0){
					CartItem ci = null;
					Iterator it = ciSet.iterator();
					while(it.hasNext()){
						ci = (CartItem)it.next();
						OrderItem oi = new OrderItem();
						oi.setOcount(ci.getOcount());
						oi.setOrder(o);
						oi.setPickupAddressId(ci.getPickupAddressId());
						oi.setPrice(ci.getPrice());
						oi.setProductId(ci.getProductId());
						oi.setProductPriceId(ci.getProductPriceId());
						oi.setTprice(ci.getTprice());
						oi.setToHehuorenPrice(ci.getToHehuorenPrice());
						oi.setToWebPrice(ci.getToWebPrice());
						oiSet.add(oi);
					}
				}
			}
			
			o.setOiSet(oiSet);
			
			orderBiz.addOrder(o);
			
			req.getSession().removeAttribute("myCart");
			
			
			//注册监听job
			MyJobManager.ORDER_WAIT_CLOSE(o);
			
			
			/*req.setAttribute("order", o);
			req.getSession().removeAttribute("myCart");
			HashMap<String,Product> productMap= OrderUtil.getProductMap(o, accountBiz);
			req.setAttribute("productMap", productMap);
			*/
			
			/*int tjrId = user.getTuijianRenId();
			if(tjrId!=0){
				User tjr = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{tjrId});
				req.setAttribute("myTJR", tjr);
			}
			*/
			
			/*List<PickUpAddress> pickUpAddressList=
					PickUpAddressUtil.findPickUpAddressList(user.getAddressId(), uICacheManager);
			req.setAttribute("pickUpAddressList", pickUpAddressList);
			*/
			
			jo.put("orderId", o.getId());
			this.renderData(res, jo.toString());
		}
		//return getForward("order/order", req);
	}
	
	
	//to3PayOrder选择outerType的支付方式
	@RequestMapping(value={"/wrap/user/order/to3PayOrder"})
	public void to3PayOrder(HttpServletRequest req,HttpServletResponse res) throws Exception{
		JSONObject jo = new JSONObject();
		jo.put("error", "");
		
		User user = (User)req.getSession().getAttribute("myUser");
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		String outerType = form.getString("outerType");
		/*String password = form.getString("password");
		if(null!=password && !password.equals("")){
			boolean verPassword=AccountUtil.verPasswordOk_boolean(accountBiz, password, User.class.getName(), user.getId());
			if(!verPassword){
				jo.put("error", "error");
				throw new MyJSONException("财务密码不正确");
			}
		}else{
			jo.put("error", "error");
			throw new MyJSONException("请输入财务密码");
		}*/
		Global g23=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 23);
		StringBuffer redirectwxsb = new StringBuffer();
		if(null!=user && null!=id && null!=outerType && !outerType.equals("") && null!=g23){
			jo.put("outerType", outerType);
			Order o = (Order)aBiz.findObjectById("get", Order.class, id, new String[]{"getOiSet"});
			if(null!=o){
				if(EnumUtil.equalsE(Order.OUTERTYPE.WEIXIN, outerType)){
					
					String wxId = user.getWxId();
					if(null!=wxId && !wxId.equals("")){
						o.setOuterType(Order.OUTERTYPE.WEIXIN.toString());
						String ym=g23.getMyValue();
						if(null!=ym && !ym.equals("")){
							String body=o.getUserName()+"下单，订单金额"+o.getTotalPrice()+"元";
							redirectwxsb.append(ym).
							append("weixin/pay/pay?wxId=")
							.append(wxId).append("&out_trade_no=").append(o.getMarkedNum())
							.append("&payType=").append(WXPayUtil.WXPAYTYPE.JSAPI).append("&total_fee=").append(o.getTotalPrice())
							.append("&body=").append(body);
							String redirect=redirectwxsb.toString();
							jo.put("redirect", redirect);
						}
					}
				}else if(EnumUtil.equalsE(Order.OUTERTYPE.ZHIFUBAO, outerType)){
					o.setOuterType(Order.OUTERTYPE.ZHIFUBAO.toString());
				}else if(EnumUtil.equalsE(Order.OUTERTYPE.ACCOUNT, outerType)){
					o.setOuterType(Order.OUTERTYPE.ACCOUNT.toString());
				}
				o.setOrderPayType(Order.ORDERPAYTYPE.outerType.toString());
				aBiz.modifyObject(o);
				
				//req.setAttribute("order", o);
				renderData(res, jo.toString());
			}
		}
	}
	
	
	
	//充account支付
	@RequestMapping(value={"/wrap/user/order/pay"})
	public void pay(HttpServletRequest req,HttpServletResponse res) throws Exception{
		User user = (User)req.getSession().getAttribute("myUser");
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		//String password = form.getString("password");
		if(null!=user && null!=id/* && null!=password && !password.equals("")*/){
			//判断财务密码是否正确
			//AccountUtil.verPasswordOk(accountBiz, password, User.class.getName(), user.getId());
			//Order o = (Order)aBiz.findObjectById("from Order as o where o.id=?", new Object[]{id});
			Order o = (Order)aBiz.findObjectById("get", Order.class, id, new String[]{"getOiSet"});
			if(null!=o){
				String orderType = o.getOuterType();
				if(EnumUtil.equalsE(Order.OUTERTYPE.ACCOUNT, orderType)){
					user = orderBiz.payOrder(user, o, accountBiz,uICacheManager);
					req.getSession().setAttribute("myUser", user);
				}
			}
		}
	}
	
	@RequestMapping(value={"/admin/order/finds","/wrap/user/order/finds"})
	public String finds(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String orderBy = form.getString("orderBy");
		String queryArgs = form.getString("queryArgs");
		String reqPrefix = WebAop.getReqPrefix(req);
		if(null!=reqPrefix && !reqPrefix.equals("")){
			if(reqPrefix.endsWith("/user/")){
				
				
				/**
				 * 用户，微信自动登录
				 */
				User user = (User)req.getSession().getAttribute("myUser");
				if(null==user){
					String code = req.getParameter("code");
					if(null!=code && !code.equals("")){
						AutoRegistAndLoginImpl wx_aral = new AutoRegistAndLoginImpl();
						user = wx_aral.autoRegistLogin(user, code, userBiz, uICacheManager,null);
						req.getSession().setAttribute("myUser", user);
					}
				}
				
				queryArgs=QueryArgsUtil.getRoleQueryArgs(null, "userId", null, req);
			}
		}
		
		
		
		aBiz.findEntitysByArgs(
				Order.class, 
				EntityTableUtil.tableName(Order.class.getName()), 
				queryArgs, orderBy, null, true, 0, "orderList", req);
		
		
		List<PickUpAddress> puaList  = CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
		req.setAttribute("puaList", puaList);
		List<Order> orderList =  (List<Order>) req.getAttribute("orderList");
		HashMap<String,PickUpAddress> pickMap = CacheKey.CKPickUpAddress.ALLMAP.getMap(uICacheManager);
		HashMap<String,List<PickUpAddress>> orderPickMap = (HashMap<String, List<PickUpAddress>>) OrderUtil.getPickListMap(orderList, pickMap);
		req.setAttribute("orderPickMap", orderPickMap);
		
		if(null!=reqPrefix && !reqPrefix.equals("")){
			if(reqPrefix.endsWith("/admin/")){
				//加载oi的productPrice
				orderList =  (List<Order>) req.getAttribute("orderList");
				String productPriceIds =  OrderUtil.getProductPriceIds(orderList);
				if(null!=productPriceIds && !productPriceIds.equals("")){
					HashMap<String,ProductPrice> productPriceMap = new HashMap<String,ProductPrice>();
					List<ProductPrice> productPriceList = (List<ProductPrice>)aBiz.findObjectList(
							ProductPrice.class, null, 
							"from ProductPrice as p where p.id in("+productPriceIds+")", null, false, 0, 0);
					int size = productPriceList.size();
					ProductPrice pp = null;
					for(int i=0;i<size;i++){
						pp= productPriceList.get(i);
						productPriceMap.put("productPriceId_"+pp.getId(), pp);
					}
					req.setAttribute("productPriceMap", productPriceMap);
				}
				
				//加载oi的product
				/*List<Order> orderList =  (List<Order>) req.getAttribute("orderList");
				String productIds =  OrderUtil.getProductPriceIds(orderList);
				if(null!=productIds && !productIds.equals("")){
					
					HashMap<String,Product> productMap = new HashMap<String,Product>();	
					List<Product> productList = (List<Product>)aBiz.findObjectList(
							Product.class, null, 
							"from Product as p where p.id in("+productIds+")", null, false, 0, 0);
					int size = productList.size();
					Product p = null;
					for(int i=0;i<size;i++){
						p= productList.get(i);
						productMap.put("productId_"+p.getId(), p);
					}
					
					req.setAttribute("productMap", productMap);
				}*/
			}
		}
		
		return this.getForward("order/orderList", req);
	}
	
	
	
	
	
	@RequestMapping(value={"/admin/order/findId","/wrap/user/order/findId"})
	public String findId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id && id.intValue()!=0){
			Order order = (Order)aBiz.findObjectById("get", Order.class, id, new String[]{"getOiSet"});
			req.setAttribute("order", order);
			
			if(null!=order){
				int userId= order.getUserId();
				User user=(User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{userId});
				if(null!=user){
					req.getSession().setAttribute("myUser", user);
					List<PickUpAddress> pickUpAddressList=
							PickUpAddressUtil.findPickUpAddressList(user.getAddressId(), uICacheManager);
					req.setAttribute("pickUpAddressList", pickUpAddressList);
				}else{
					List<PickUpAddress> puaList  = CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
					req.setAttribute("pickUpAddressList", puaList);
				}
				
				
				
			/*	
				List<PickUpAddress> puaList  = CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
				req.setAttribute("puaList", puaList);
				*/
				HashMap<String,ProductPrice> productPriceMap= OrderUtil.getProductPriceMap(order, accountBiz);
				req.setAttribute("productPriceMap", productPriceMap);
				
				/*String fix = WebAop.getReqPrefix(req);
				if(null!=fix){
					if(fix.contains("/wrap/user")){
						user = (User)req.getSession().getAttribute("myUser");
						if(null!=user){
							int tjrId = user.getTuijianRenId();
							if(tjrId!=0){
								User tjr = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{tjrId});
								req.setAttribute("myTJR", tjr);
							}
						}
					}
				}*/
				
			}
			
			
		}
		return this.getForward("order/order", req);
	}
	
	
	@RequestMapping(value={"/wrap/user/order/modifyOIPUAId"})
	public void modifyOIPUAId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		Integer pickUpAddressId = form.getInteger("pickUpAddressId");
		Integer orderItemId = form.getInteger("orderItemId");
		if(null!=orderItemId && orderItemId.intValue()!=0 && null!=pickUpAddressId && pickUpAddressId.intValue()!=0 && null!=user){
			OrderItem oi = (OrderItem)aBiz.findObjectById("from OrderItem as oi where oi.id=?", new Object[]{orderItemId});
			if(null!=oi){
				int pickupAddressIdOld = oi.getPickupAddressId();
				if(pickupAddressIdOld!=pickUpAddressId){
					Order o = oi.getOrder();
					if(null!=o){
						int oId = o.getId();
						o = (Order)aBiz.findObjectById("get", Order.class, oId, new String[]{"getOiSet"});
						//o=(Order)aBiz.findObjectById("from Order as o where o.id=?", new Object[]{oId});
						if(null!=o){
							String status = o.getStatus();
							if(EnumUtil.equalsE(Order.STATUS.CLOSE, status)){
								throw new MyStrException("抱歉，该订单已经关闭，不能修改");
							}else if(EnumUtil.equalsE(Order.STATUS.PICKUPED, status)){
								throw new MyStrException("抱歉，该订单已经取货，不能修改");
							}else{
								//WAITPAY  PAYED
								oi.setPickupAddressId(pickUpAddressId);
								aBiz.modifyObject(oi);
								
								/**
								 * 将该订单中，同一个product下的，所有的cartItem的pick都修改为新的
								 */
								aBiz.j_execute("update order_orderItem set pickupAddressId=? where order_id=? and productId=?", 
										new Object[]{pickUpAddressId,oi.getOrder().getId(),oi.getProductId()});
								
								
								PickUpAddress pickUpAddress=CacheKey.CKPickUpAddress.ALLMAP.getObject(uICacheManager, pickUpAddressId);
								if(null!=pickUpAddress){
									/**
									 * 重新修改了取货地址，发送信息。
									 */
									HashMap<String,Object> args=new HashMap<String,Object>();
									args.put("wxId", user.getWxId());
									args.put("order", o);
									args.put("pickUpAddress", pickUpAddress);
									MessageUtil.sendMssage(MessageUtil.MssageTYPE.ModifyOrderItem_PickAddress.toString(),
											MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
											args,aBiz);
									
								}
							
							}
						}
					}
				}else{
					throw new MyStrException("新老自提点相同");
				}
			}
		}
	}
	
	
	//================================

	@RequestMapping(value={"/wrap/user/order/close","/admin/order/close"})
	public void close(HttpServletRequest req,HttpServletResponse res) throws Exception{
		User user = (User)req.getSession().getAttribute("myUser");
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=user && null!=id){
			//Order o = (Order)aBiz.findObjectById("from Order as o where o.id=?", new Object[]{id});
			Order o = (Order)aBiz.findObjectById("get", Order.class, id, new String[]{"getOiSet"});
			if(null!=o){
				String status = o.getStatus();
				if(EnumUtil.equalsE(Order.STATUS.WAITPAY, status)||EnumUtil.equalsE(Order.STATUS.PAYING, status)){
					o.setStatus(Order.STATUS.CLOSE.toString());
					aBiz.modifyObject(o);
				}else{
					throw new MyStrException("抱歉，该订单为非待付款状态，不能关闭");
				}
			}
		}
	}
	
	
	@RequestMapping(value={"/wrap/user/order/picked","/admin/order/picked"})
	public void picked(HttpServletRequest req,HttpServletResponse res) throws Exception{
		User user = (User)req.getSession().getAttribute("myUser");
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=user && null!=id){
			//Order o = (Order)aBiz.findObjectById("from Order as o where o.id=?", new Object[]{id});
			Order o = (Order)aBiz.findObjectById("get", Order.class, id, new String[]{"getOiSet"});
			if(null!=o){
				/**
				 * 1、如果该订单的支付方式为第三方在线支付
				 * 			则给推荐人的佣金与成为正式的合伙人两个步骤均在第三方的支付成功回调方法内执行
				 * 2、如果该订单的支付方式为自提付款
				 * 			则当用户选择为自提付款之后，又点击选择确认已经提货，在点击确认已经提货（即本方法内）
				 * 			执行佣金计算+成为正式合伙人。
				 */
				
				String status = o.getStatus();
				if(EnumUtil.equalsE(Order.STATUS.PAYED, status)){
					//选择的是第三方在线支付，且已经支付成功，且已经回调完成
					o.setStatus(Order.STATUS.PICKUPED.toString());
					o.setPickDate(new Date());
					aBiz.modifyObject(o);
				}else if(EnumUtil.equalsE(Order.STATUS.PAYING, status)){
					//选择的是自提付款，且已经选择了自提付款，在这里要确认提货了
					/**
					 * 判断管理员输入的密码
					 */
					Global g24=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 24);
					if(null!=g24){
						String g24value = g24.getMyValue();
						if(null==g24value){
							g24value="";
						}
						String pwd = form.getString("pwd");
						if(null!=pwd && !pwd.equals("")){
							if(pwd.equals(g24value)){
								User newUser = orderBiz.zitifukuan_picked(o, accountBiz, uICacheManager);
								req.getSession().setAttribute("myUser", newUser);
								try{
									if(null!=newUser){
										ScheduleJob job  = MyJobManager.getScheduleJob("ORDER_", "WAITCLOSE_"+o.getId());
										if(null!=job){
											MyJobManager.deleteJob(job);
											MyJobManager.removeFromMap(job);
										}
									}
								}catch(Exception e){
									e.printStackTrace();
								}
							}else{
								throw new MyStrException("抱歉，管理员输入的自提确认密码错误！");
							}
						}else{
							throw new MyStrException("抱歉，管理员输入的自提确认密码错误！");
						}
					}
					
					/*o.setStatus(Order.STATUS.PICKUPED.toString());
					o.setPickDate(new Date());
					aBiz.modifyObject(o);*/
					
				}else{
					throw new MyStrException("抱歉，该订单为非已付款状态，不能确定为已自提");
				}
				
			}
		}
	}
	
	//============================================
	//选择付款方式为货到付款
	/*@RequestMapping(value={"/wrap/user/order/orderPayType_hdfk"})
	public void orderPayType_hdfk(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		//String orderPayType=form.getString("orderPayType");
		if(null!=id && id.intValue()!=0){
			Order order = (Order)aBiz.findObjectById("get", Order.class, id, null);
			req.setAttribute("order", order);
			if(null!=order){
				order.setOrderPayType(Order.ORDERPAYTYPE.Huodaofukuan.toString());
				aBiz.modifyObject(order);
			}
		}
	}*/
	@RequestMapping(value={"/wrap/user/order/orderPayType_hdfk"})
	public void orderPayType_hdfk(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		User user = (User)req.getSession().getAttribute("myUser");
		//String orderPayType=form.getString("orderPayType");
		if(null!=id && id.intValue()!=0 && null!=user){
			Order order = (Order)aBiz.findObjectById("get", Order.class, id, new String[]{"getOiSet"});
			req.setAttribute("order", order);
			if(null!=order){
				String status = order.getStatus();
				if(null!=status && !status.equals("")){
					if(EnumUtil.equalsE(Order.STATUS.WAITPAY, status)){
						order.setStatus(Order.STATUS.PAYING.toString());
						order.setOrderPayType(Order.ORDERPAYTYPE.Huodaofukuan.toString());
						aBiz.modifyObject(order);		
						
						
						
						
						/**
						 * 取货通知--给用户
						 * 新单通知--给管理员
						 */
						
						Admin admin = (Admin)aBiz.findObjectById("from Admin as a where a.id=1", null);
						HashMap<String,Object> argstoAdmin = new HashMap<String,Object>();
						argstoAdmin.put("order", order);
						argstoAdmin.put("wxId", admin.getWxId());
						argstoAdmin.put("adminPhone", admin.getTelPhone());
						MessageUtil.sendMssage(MessageUtil.MssageTYPE.Order_Payed_SUC_TOADMIN.toString(),
								MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), argstoAdmin, aBiz);
						
						

						//该用户付款成功后，短信通知
						HashMap<String,Object> args=new HashMap<String,Object>();
						args.put("wxId", user.getWxId());
						args.put("order", order);
						MessageUtil.sendMssage(MessageUtil.MssageTYPE.Order_Payed_HDFK_SUC_QH.toString(),
								MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
								args,aBiz);
						
												
						
						
					}
				}
				
			}
		}
	}
	
	
	
	
	//=============================================================================================

	//充account支付
	@RequestMapping(value={"/admin/order/findAllShouhuoren"})
	public String findAllShouhuoren(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String orderBy = form.getString("orderBy");
		String queryArgs = form.getString("queryArgs");
		
		aBiz.findEntitysByArgs(
				Order.class, 
				EntityTableUtil.tableName(Order.class.getName()), 
				queryArgs, orderBy, null, true, 0, "orderList", req);
		
		//遍历所有的order，去除重复的
		//
		/*Map<String,String> shouhuoren_phone_name_map = new HashMap<String,String>();
		List<Order> orderList = new ArrayList<Order>();
		List<Order> orderList_req = (List<Order>)req.getAttribute("orderList");
		if(null!=orderList_req){
			int size = orderList_req.size();
			Order o = null;
			String shouhuorenName = null;
			String shouhuorenPhone = null;
			for(int i=0;i<size;i++){
				o = orderList_req.get(i);
				shouhuorenName = o.getShouhuoName();
				shouhuorenPhone = o.getShouhuoTelphone();
				if(null!=shouhuorenName && shouhuorenName.equals("") && null!=shouhuorenPhone && !shouhuorenPhone.equals("")){
					String name = shouhuoren_phone_name_map.get(shouhuorenPhone);
					if(null==name){
						//说明根本就没有这个手机号为key的键值对
						shouhuoren_phone_name_map.put(shouhuorenPhone, shouhuorenName);
						orderList.add(o);
					}else{
						//如果有
					}
				}
			}
			req.setAttribute("orderList", orderList);
		}*/
		
		
		return "manage/admin/order/allShouhuoren";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
