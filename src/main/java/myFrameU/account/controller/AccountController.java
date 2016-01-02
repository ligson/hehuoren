package myFrameU.account.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.account.biz.AccountBizI;
import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.account.util.AccountUtil;
import myFrameU.admin.entity.Admin;
import myFrameU.exception.exception.MyJSONException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.queryArgs.util.QueryArgsUtil;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.text.PasswordUtil;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;
import myFrameU.weixin.base.util.GetWXId;
import myFrameU.weixin.pay.util.WXPayUtil;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AccountController extends FatherController {
	@Autowired
	private AccountBizI accountBiz;
	
	public AccountBizI getAccountBiz() {
		return accountBiz;
	}

	public void setAccountBiz(AccountBizI accountBiz) {
		this.accountBiz = accountBiz;
	}

	@RequestMapping(value={"/admin/account/index","/shop/account/index","/user/account/index","/wrap/user/account/index","/wrap/shop/account/index"})
	public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String whoClassName = (String)req.getAttribute("roleClassName");
		Integer whoId=(Integer) req.getAttribute("roleId");
		
		
		if(null!=whoClassName && !whoClassName.equals("") && null!=whoId && whoId.intValue()!=0){
			Account account = accountBiz.account_findAccount(whoClassName, whoId);
			req.setAttribute("account", account);
			
			List<AccountItem> accountItemList = (List<AccountItem>)aBiz.findObjectList(
					AccountItem.class, 
					new Object[]{account.getId()},
					"from AccountItem as ai where ai.account.id=? order by ai.id desc",
					null, true, 0, 30);
			req.setAttribute("accountItemList", accountItemList);
			
		}
		/*if(isWrap(req)){
			return getForward("wrap/manage/user/account/index", req);
		}else{
			return getForward("account/index", req);
		}*/
		return getForward("account/index", req);
	}
	
	@RequestMapping(value={"/admin/account/toRecharge","/shop/account/toRecharge","/user/account/toRecharge","/wrap/shop/account/toRecharge","/wrap/user/account/toRecharge"})
	public String toRecharge(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		return getForward("account/recharge", req);
	}
	
	@RequestMapping(value={"/admin/account/recharge"})
	public void adminRecharge(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		Float price = form.getFloat("price");
		if(null!=price && price.floatValue()!=0){
			
		}else{
			throw new MyStrException("请输入充值金额");
		}
		if(null!=admin){
			if(myFrameU.account.init.InitConfig.passwordStatus){
				String password = form.getString("password");
				if(null==password || password.equals("")){
					throw new MyStrException("请输入财务密码！");
				}
				Account account = accountBiz.account_findAccount(Admin.class.getName(), admin.getId());
				if(null!=account){
					String pwd = account.getWithdrawalsPwd();
					if(pwd.equals(password)){
						accountBiz.rechage(account, price, AccountItem.STATUS.FINISH.toString(),AccountItem.OUTERTYPE.ADMIN.toString());
					}else{
						throw new MyStrException("财务密码错误！");
					}
				}
			}else{
				Account account = accountBiz.account_findAccount(Admin.class.getName(), admin.getId());
				if(null!=account){
					accountBiz.rechage(account, price, AccountItem.STATUS.FINISH.toString(),AccountItem.OUTERTYPE.ADMIN.toString());
				}
			}
		}
	}
	
	

	@RequestMapping(value={"/admin/account/toWithdrawals"})
	public String toWithdrawals(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		return "manage/admin/account/withdrawals";
	}
	
	
	
	@RequestMapping(value={"/admin/account/withdrawals"})
	public void adminWithdrawals(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		Float price = form.getFloat("price");
		if(null!=price && price.floatValue()!=0){
			
		}else{
			throw new MyStrException("请输入充值金额");
		}
		if(null!=admin){
			if(myFrameU.account.init.InitConfig.passwordStatus){
				String password = form.getString("password");
				if(null==password || password.equals("")){
					throw new MyStrException("请输入财务密码！");
				}
				Account account = accountBiz.account_findAccount(Admin.class.getName(), admin.getId());
				if(null!=account){
					String pwd = account.getWithdrawalsPwd();
					if(pwd.equals(password)){
						accountBiz.withDrawals(Admin.class.getName(), admin.getId(), account, price);
					}else{
						throw new MyStrException("财务密码错误！");
					}
				}
			}else{
				Account account = accountBiz.account_findAccount(Admin.class.getName(), admin.getId());
				if(null!=account){
					accountBiz.withDrawals(Admin.class.getName(), admin.getId(), account, price);
				}
			}
			
		}
	}
	
	
	
	@RequestMapping(value={"/shop/account/recharge","/user/account/recharge","/wrap/shop/account/recharge","/wrap/user/account/recharge"})
	public void recharge(HttpServletRequest req,HttpServletResponse res) throws Exception{
		JSONObject jo = new JSONObject();
		jo.put("error", "");
		/**
		 * PC端：
		 * 		1、支付宝PC网页支付
		 * 		2、微信扫码支付
		 * wrap端：
		 * 		1、支付宝手机网页支付
		 * 		2、微信JS支付。
		 */
		String reqfix = WebAop.getReqPrefix(req);
		if(null!=reqfix && !reqfix.equals("")){
			if(reqfix.contains("/wrap/")){
				jo.put("pcOrWrap", "wrap");
			}else{
				jo.put("pcOrWrap", "pc");
			}
		}else{
			jo.put("pcOrWrap", "pc");
		}
		
		
		SDynaActionForm form = getSDynaActionForm(req);
		String outerInterface=form.getString("outerInterface");//weixin  zhifubao，默认的是支付宝
		if(null==outerInterface || outerInterface.equals("")){
			outerInterface="zhifubao";
		}
		
		boolean weixinOk=false;
		myFrameU.weixin.init.InitConfig ic_wx=null;
		if(outerInterface.equals("weixin")){
			ic_wx = myFrameU.weixin.init.InitMavenImpl.ic;
			if(null!=ic_wx){
				String appId=ic_wx.getAppId();
				String mchId=ic_wx.getPayMchId();
				String notifyUrl=ic_wx.getPayNotifyUrl();
				if(null!=mchId && !mchId.equals("")){
					weixinOk=true;
				}
			}
			if(!weixinOk){
				throw new MyJSONException("抱歉，该平台没有接入微信支付");
			}
		}
		String wxPayType = null;
		if(outerInterface.equals("weixin")){
			//如果是微信支付，则需要穿进来微信支付的方式，wxPayType
			wxPayType=form.getString("wxPayType");
			if(null==wxPayType || wxPayType.equals("")){
				wxPayType=WXPayUtil.WXPAYTYPE.JSAPI.toString();
			}
		}
		
		
		jo.put("outerInterface", outerInterface);
		
		
		//String redirect=form.getString(CommonField.redirect);
		String redirect=null;
		
		String whoClassName = (String)req.getAttribute(CommonField.roleClassName);
		Integer whoId=(Integer) req.getAttribute(CommonField.roleId);
		
		String password = null;
		boolean passwordStatus = myFrameU.account.init.InitConfig.passwordStatus;
		if(passwordStatus){
			password = req.getParameter("password");
			if(null==password || password.equals("")){
				throw new MyJSONException("请输入财务密码！");
			}
		}
		
		Float price = form.getFloat("price");
		if(null!=whoClassName && !whoClassName.equals("") && null!=whoId && whoId.intValue()!=0 && null!=price && price.floatValue()>0){
			Account account = accountBiz.account_findAccount(whoClassName, whoId);
			if(null!=account){
				
				if(passwordStatus){
					String passwordDB = account.getWithdrawalsPwd();
					String zhifubao = account.getZhifubao();
					if(null==passwordDB || passwordDB.equals("")){
						throw new MyJSONException("抱歉，您还没有设置财务密码，请前去设置");
					}
					/*if(null==zhifubao || zhifubao.equals("")){
						throw new MyStrException("抱歉，您还没有设置支付宝账号，请前去设置");
					}*/
					if(!passwordDB.equals(password)){
						throw new MyJSONException("抱歉，您输入的财务密码不正确");
					}
				}
				
				
				//判断实名认证
				/*Certification c = (Certification)aBiz.findObjectById("from Certification as c where c.whoclassName=? and c.whoId=?", 
						new Object[]{whoClassName,whoId});
				if(null!=c){
					String shimingStatus = c.getShimingStatus();
					if(!EnumUtil.equalsE(Certification.STATUS.APPLYOK, shimingStatus)){
						throw new MyStrException("抱歉，您尚未实名认证，请前去实名认证！");	
					}
				}
				*/
				
				
				
				StringBuffer redirectSB=new StringBuffer();
				//InitConfig initConfig  = myFrameU.pay.alipay.init.InitMavenImpl.ic;
				//String webZFB=initConfig.getAlipay_web_seller_email();
				String webZFB=null;
				Account adminAccount = (Account)aBiz.findObjectById("from Account as a where a.id=?", 
						new Object[]{1});
				if(null!=adminAccount){
					String outerType=null;
					if(outerInterface.equals("zhifubao")){
						outerType=AccountItem.OUTERTYPE.ZHIFUBAO.toString();
					}else if(outerInterface.equals("weixin")){
						outerType=AccountItem.OUTERTYPE.WEIXIN.toString();
					}
					AccountItem ai = accountBiz.rechage(account, price,null,outerType);
					req.setAttribute("accountItem", ai);
					if(null!=ai){
						jo.put("accountItemId", ai.getId());
						
						if(outerInterface.equals("zhifubao")){
							webZFB = adminAccount.getZhifubao();
							if(null!=webZFB){
								String buyerEmail=null;
								/*if(whoClassName.equals(User.class.getName())){
									
								}else if(whoClassName.equals(Shop.class.getName())){
									
								}else if(whoClassName.equals(Admin.class.getName())){
									
								}*/
								buyerEmail=account.getZhifubao();
								
								redirectSB.append(PathUtil.getBasePath(req)).append("/pay/alipay/alipayapi?WIDseller_email=").append(webZFB)
								.append("&WIDout_trade_no=").append(ai.getMarkedNum()).append("&WIDsubject=艺术拍拍网充值&WIDtotal_fee=")
								.append(ai.getPrice()).append("&WIDbody=艺术拍拍网充值").append("&WIDshow_url=www.yishupaipai.com&WIDbuyer_email=")
								.append(buyerEmail);
								
								redirect=redirectSB.toString();
								System.out.println(redirect+"______________________");
								jo.put("redirect", redirect);
								//renderData(res, redirect);
								renderData(res, jo.toString());
							}
						}else if(outerInterface.equals("weixin")){
							/**
							 * 微信支付需要比支付宝支付多一个步骤，就是不仅仅要在咱们的系统中生成自己的订单，还要在微信支付系统中生成订单
							 * 生成订单之后，才能去支付。
							 */
							String pcOrWrap = (String)jo.get("pcOrWrap");
							if(pcOrWrap.equals("wrap")){
								//微信JS支付
								/*String appId=ic_wx.getAppId();
								String mchId=ic_wx.getPayMchId();
								String notifyUrl=ic_wx.getPayNotifyUrl();*/
								
								String wxId = GetWXId.getWxId(req);
								jo.put("wxId", wxId);
								jo.put("out_trade_no", ai.getMarkedNum());
								jo.put("payType", WXPayUtil.WXPAYTYPE.JSAPI);
								/*float aiPrice = ai.getPrice();
								int aiPriceInt = new Float(aiPrice).intValue();*/
								jo.put("total_fee",price);
								jo.put("body", ai.getInfo());
								
								/**
								 * allWeb + "weixin/pay/test?wxId=" + wxId
								+ "&out_trade_no=" + out_trade_no + "&payType="
								+ payType + "&total_fee=" + total_fee
								+ "&body=" + body,
								 */
								Global g23=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 23);
								if(null!=g23){
									String ym=g23.getMyValue();
									if(null!=ym && !ym.equals("")){
										StringBuffer redirectwxsb = new StringBuffer();
										redirectwxsb.append(ym)
										./*append(PathUtil.getBasePath(req)).append("weixin/pay/test/pay?wxId=")*/
										append("weixin/pay/pay?wxId=")
										.append(wxId).append("&out_trade_no=").append( ai.getMarkedNum())
										.append("&payType=").append(/*WXPayUtil.WXPAYTYPE.JSAPI*/wxPayType).append("&total_fee=").append(price)
										.append("&body=").append(ai.getInfo());
										redirect=redirectwxsb.toString();
										jo.put("redirect", redirect);
										
									}
								}
								
								
								renderData(res, jo.toString());
							}else if(pcOrWrap.equals("pc")){
								//微信扫码支付
							}
						}
					}
				}
			}
		}
		
	}
	
	

	
	@RequestMapping(value={"/admin/account/shopAccountList"})
	public String shopAccountList(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		req.setAttribute("whoclassName", Shop.class.getName());
		String queryArgs=form.getString("queryArgs");
		if(null==queryArgs || queryArgs.equals("")){
			queryArgs="[{'key':'whoclassName','value':'myFrameU.shop.entity.Shop'}]";
		}else{
			queryArgs=queryArgs.substring(0,queryArgs.length()-1);
			queryArgs=queryArgs+",{'key':'whoclassName','value':'myFrameU.shop.entity.Shop'}]";
		}
		
		aBiz.findEntitysByArgs(
				Account.class, EntityTableUtil.tableNameC(Account.class), 
				queryArgs, null, null, true, 
				0, "accountList", req);
		
		
		
	/*	Pager pager = new MyActionUtil(req, "").getPager(req, aBiz,
				"select count(id) from "+EntityTableUtil.tableName(Account.class.getName())
				+" as a where a.whoclassName=?", new Object[]{Shop.class.getName()});
		
		List<Account> accountList = (List<Account>)aBiz.findObjectList(
				Account.class, 
				new Object[]{Shop.class.getName()},
				"from Account as a where a.whoclassName=? order by a.id desc",
				null, true, pager.getStartRow(), pager.getPageSize());*/
		//req.setAttribute("accountList", accountList);
		return getForward("account/accountList", req);
	}
	
	@RequestMapping(value={"/admin/account/userAccountList"})
	public String userAccountList(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		req.setAttribute("whoclassName", User.class.getName());
		
		String queryArgs=form.getString("queryArgs");
		if(null==queryArgs || queryArgs.equals("")){
			queryArgs="[{'key':'whoclassName','value':'myFrameU.user.entity.User'}]";
		}else{
			queryArgs=queryArgs.substring(0,queryArgs.length()-1);
			queryArgs=queryArgs+",{'key':'whoclassName','value':'myFrameU.user.entity.User'}]";
		}
		
		aBiz.findEntitysByArgs(
				Account.class, EntityTableUtil.tableNameC(Account.class), 
				queryArgs, null, null, true, 
				0, "accountList", req);
		
		
		/*
		Pager pager = new MyActionUtil(req, "").getPager(req, aBiz,
				"select count(id) from "+EntityTableUtil.tableName(Account.class.getName())
				+" as a where a.whoclassName=?", new Object[]{User.class.getName()});
		
		
		List<Account> accountList = (List<Account>)aBiz.findObjectList(
				Account.class, 
				new Object[]{User.class.getName()},
				"from Account as a where a.whoclassName=? order by a.id desc",
				null, true,pager.getStartRow(), pager.getPageSize());
		req.setAttribute("accountList", accountList);*/
		return getForward("account/accountList", req);
	}
	
	
	@RequestMapping(value={"/admin/account/likeAccountList"})
	public String likeAccountList(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String whoName= form.getString("whoName");
		if(null!=whoName && !whoName.equals("")){
			String userIds=(String)aBiz.j_queryObject("select group_concat(convert(u.id,char)) from user_user as u where u.nicheng like '%"+whoName+"%'", null);
			if(null!=userIds && !userIds.equals("")){
				if(userIds.endsWith(",")){
					userIds = userIds.substring(0,userIds.length()-1);
				}
				if(!userIds.equals("")){
					List<User> userList = (List<User>)aBiz.findObjectList(User.class, null, 
							"from User as u where u.id in("+userIds+")", null, false, 0, 0);
					if(null!=userList){
						HashMap<String,User> userMap = new HashMap<String,User>();
						int size = userList.size();
						User user = null;
						String userIdStr = null;
						User userInMap = null;
						for(int i=0;i<size;i++){
							user= userList.get(i);
							userIdStr="userId_"+user.getId();
							userInMap = userMap.get(userIdStr);
							if(null==userInMap){
								userMap.put(userIdStr, user);
							}
						}
						
						List<Account> accountList = (List<Account>)aBiz.findObjectList(
								Account.class, 
								null,
								"from Account as a where a.whoId in("+userIds+") order by a.id desc",
								null, true, 0, 50);
						if(null!=accountList){
							List<Account> accountListLast = new ArrayList<Account>();
							int asize = accountList.size();
							Account a = null;
							int whoId=0;
							String whoIdStr=null;
							User auser = null;
							for(int i=0;i<asize;i++){
								a=accountList.get(i);
								whoId=a.getWhoId();
								whoIdStr="userId_"+whoId;
								auser=userMap.get(whoIdStr);
								if(null!=auser){
									a.setWhoName(auser.getNicheng());
								}
								accountListLast.add(a);
							}
							req.setAttribute("accountList", accountListLast);
						}
					}
				}
			}
		}
		return getForward("account/accountListAjax", req);
	}
	
	@RequestMapping(value={"/admin/account/accountItemList"})
	public String findAccountItemListA(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer accountId = form.getInteger("accountId");
		if(null!=accountId && accountId.intValue()!=0){
			
			String queryArgs=form.getString("queryArgs");
			if(null==queryArgs || queryArgs.equals("")){
				queryArgs="[{'key':'account.id|account_id','value':'"+accountId+"','filedType':'int'}]";
			}else{
				queryArgs=queryArgs.substring(0,queryArgs.length()-1);
				queryArgs=queryArgs+",{'key':'account.id|account_id','value':'"+accountId+"','filedType':'int'}]";
			}
			
			Account account = (Account)accountBiz.findObjectById("from Account as a where a.id=?", new Object[]{accountId});
			req.setAttribute("account", account);
			
			aBiz.findEntitysByArgs(AccountItem.class, EntityTableUtil.tableNameC(AccountItem.class), 
					queryArgs, null, null, true, 0, "accountItemList", req);
			
			
			/*
			Pager pager = new MyActionUtil(req, "").getPager(req, aBiz,
					"select count(id) from "+EntityTableUtil.tableName(AccountItem.class.getName())
					+" as ai where ai.account_id=?", new Object[]{accountId});
			
			List<AccountItem> aiList = (List<AccountItem>)aBiz.findObjectList(AccountItem.class, new Object[]{accountId}, "from AccountItem as ai where ai.account.id=? order by ai.id desc", null, true, pager.getStartRow(), pager.getPageSize());
			req.setAttribute("accountItemList", aiList);*/
		}
		return getForward("account/accountItemList", req);
	}
	
	@RequestMapping(value={"/admin/account/myAccountItemList"})
	public String findMyAccountItemListA(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		if(null!=admin){
			String queryArgs=form.getString("queryArgs");
			queryArgs=QueryArgsUtil.getRoleQueryArgs(null, null, "whoId", req);
			if(null!=queryArgs && !queryArgs.equals("")){
				queryArgs=queryArgs.substring(0,queryArgs.length()-1);
				queryArgs=queryArgs+",{'key':'whoclassName','value':'myFrameU.admin.entity.Admin'}]";
			}else{
				queryArgs="[{'key':'whoclassName','value':'myFrameU.admin.entity.Admin'}]";
			}
			aBiz.findEntitysByArgs(AccountItem.class, EntityTableUtil.tableNameC(AccountItem.class), queryArgs, null, null, true, 0, "accountItemList", req);
		}
		return "manage/admin/account/accountItemList1";
	}
	
	@RequestMapping(value={"/shop/account/accountItemList","/user/account/accountItemList","/wrap/user/account/accountItemList","/wrap/shop/account/accountItemList"})
	public String findAccountItemList(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String whoClassName = (String)req.getAttribute(CommonField.roleClassName);
		Integer whoId=(Integer) req.getAttribute(CommonField.roleId);
		if(null!=whoClassName && null!=whoId){
			
			String queryArgs=form.getString("queryArgs");
			queryArgs=QueryArgsUtil.getRoleQueryArgs("whoId", "whoId", null, req);
			if(null!=queryArgs && !queryArgs.equals("")){
				queryArgs=queryArgs.substring(0,queryArgs.length()-1);
				queryArgs=queryArgs+",{'key':'whoclassName','value':'"+whoClassName+"'}]";
			}else{
				queryArgs="[{'key':'whoclassName','value':'"+whoClassName+"'}]";
			}
			
			aBiz.findEntitysByArgs(AccountItem.class, EntityTableUtil.tableNameC(AccountItem.class), queryArgs, null, null, true, 0, "accountItemList", req);
		}
		return getForward("account/accountItemList", req);
	}
	
	
	@RequestMapping(value={"/admin/account/toOperation"})
	public String toOperation(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		
		return getForward("account/operation", req);
	}
	
	
	
	@RequestMapping(value={"/admin/account/operAccount"})
	public void operAccount(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer accountId = form.getInteger("accountId");
		String operType= form.getString("operType");
		Float price = form.getFloat("price");
		
		
		boolean passwordStatus = myFrameU.account.init.InitConfig.passwordStatus;
		if(passwordStatus){
			String password = form.getString("password");
			if(null==password || password.equals("")){
				throw new MyStrException("抱歉，请您输入财务密码");
			}
			AccountUtil.verPasswordOk(accountBiz, password, Admin.class.getName(), 1);
		}
		
		
		if(null!=accountId && accountId.intValue()!=0){
			if(null!=operType &&  !operType.equals("") ){
				if(null!=price && price.floatValue()>0){
					/**
					 * 操作user和shop的账户
					 * 1、充值
					 */
					
					Account account = (Account)accountBiz.findObjectById("from Account as a where a.id=?", new Object[]{accountId});
					if(null!=account){
						account = accountBiz.account_findAccount(account.getWhoclassName(), account.getWhoId());
						if(null!=account){
							if(operType.equals("RECHARGE")){
								//充值
								AccountItem accountItem = accountBiz.rechage(account, price,AccountItem.STATUS.FINISH.toString(),AccountItem.OUTERTYPE.ADMIN.toString());
							}else if(operType.equals("FROZEN")){
								//冻结
								//判断有没那么多可用资金
								float availablePrice = account.getAvailablePrice();
								if(availablePrice>=price){
									accountBiz.forzen(account, price, "", "管理员冻结您的资金");
								}else{
									throw new MyStrException("抱歉，该账户没有那么多可用资金");
								}
							}else if(operType.equals("SPENDING")){
								//支付
								//这里之操作支付给平台
								Account adminAccount = accountBiz.account_findAccount(Admin.class.getName(), 1);
								if(null!=adminAccount){
									boolean enought=accountBiz.account_enoughAvailablePriceInAccount(account, 0, price);
									if(enought){
										AccountItem spendingAI=accountBiz.spending(account, price, null, AccountItem.STATUS.FINISH.toString(), "管理员操作将该财务账户中的"+price+"元支付给平台");
										if(null!=spendingAI){
											AccountItem incomeAI=accountBiz.income(adminAccount, price, null, AccountItem.STATUS.FINISH.toString(),  "管理员操作将"+account.getWhoName()+"财务账户中的"+price+"元支付给平台");
											if(null!=incomeAI){
												accountBiz.matchAI(null, spendingAI, incomeAI);
											}
										}
									}else{
										throw new MyStrException("抱歉，该账户可用余额不足以支付");
									}
								}
							}else if(operType.equals("INCOME")){
								//该账户收入，这里只操作从平台账户支出
								Account adminAccount = accountBiz.account_findAccount(Admin.class.getName(), 1);
								if(null!=adminAccount){
									boolean enought=accountBiz.account_enoughAvailablePriceInAccount(adminAccount, 0, price);
									if(enought){
										AccountItem spendingAI=accountBiz.spending(adminAccount, price, null, AccountItem.STATUS.FINISH.toString(), "管理员操作平台账户内的"+price+"元支付给"+account.getWhoName());
										if(null!=spendingAI){
											AccountItem incomeAI=accountBiz.income(account, price, null, AccountItem.STATUS.FINISH.toString(),  "管理员操作将平台财务账户中的"+price+"元支付给您");
											if(null!=incomeAI){
												accountBiz.matchAI(null, incomeAI, spendingAI);
											}
										}
									}else{
										throw new MyStrException("抱歉，平台账户内没有足够的可用资金用于支付给该账户");
									}
								}
							}
						}
						
						/**
						 * 短信通知
						 */
					}
				}else{
					throw new MyStrException("抱歉，请输入大于0的金额");
				}
			}
		}
	}
	
	
	
	
	@RequestMapping(value={"/shop/account/toApplyTX","/user/account/toApplyTX","/wrap/user/account/toApplyTX","/wrap/shop/account/toApplyTX"})
	public String toApplyTX(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String roleClassName=(String) req.getAttribute(CommonField.roleClassName);
		Integer roleId=(Integer) req.getAttribute(CommonField.roleId);
		if(null!=roleClassName && null!=roleId && !roleClassName.equals("") && roleId.intValue()!=0){
			Account account = accountBiz.account_findAccount(roleClassName, roleId);
			if(null!=account){
				req.setAttribute("account", account);
			}
			Global g8=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 8);
			req.setAttribute("g", g8);
			
			int count = AccountUtil.getCount_tx_month(accountBiz, account.getId());
			if(null!=g8){
				int cishu=new Integer(g8.getMyValue()).intValue();
				int shengyuCount=cishu-count;
				req.setAttribute("shengyuCount", shengyuCount);
			}
			
			/**
			 * 本项目每次申请提现必须最少提现将销售佣金+可用宣传佣金提现出去
			 */
			/*float xsPrice = account.getXsPrice();
			float available_xcPrice=AccountUtilHHR.getAvailable_xcPrice(uICacheManager, account);
			float leastPrice = xsPrice+available_xcPrice;
			
			if(leastPrice!=0){
				req.setAttribute("leastPrice", leastPrice);
			}*/
			req.setAttribute("leastPrice", account.getAvailablePrice());
			
		}
		return getForward("account/applyTX", req);
	}
	

	
	
	//===============================================
	
	
	
	
	
	
	
	
	
	@RequestMapping(value={"shop/security/toAccountpassword","user/security/toAccountpassword","admin/security/toAccountpassword"
			,"wrap/user/security/toAccountpassword","wrap/shop/security/toAccountpassword"})
	public String security2Accountpassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String prefix = WebAop.getReqPrefix(req);
		if(prefix.equals("/shop/")||prefix.equals("/wrap/shop/")){
			Shop shop = (Shop)req.getSession().getAttribute("myShop");
			req.setAttribute("myShop", shop);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
			req.setAttribute("account", a);
			if(prefix.equals("/shop/")){
				return "manage/shop/security/accountPassword";
			}else{
				return "wrap/manage/shop/security/accountPassword";
			}
		}else if(prefix.equals("/user/")||prefix.equals("/wrap/user/")){
			User user = (User)req.getSession().getAttribute("myUser");
			req.setAttribute("myUser", user);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
			req.setAttribute("account", a);
			if(prefix.equals("/user/")){
				return "manage/user/security/accountPassword";
			}else{
				return "wrap/manage/user/security/accountPassword";
			}
		}else if(prefix.equals("/admin/")){
			Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
			req.setAttribute("myAdmin", admin);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Admin.class.getName(),admin.getId()});
			req.setAttribute("account", a);
			return "manage/admin/security/accountPassword";
		}
		return null;
		
	}
	@RequestMapping(value={"shop/security/accountpassword","user/security/accountpassword","admin/security/accountpassword"})
	public void securityModifyAccountpassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String withdrawalsPwd=form.getString("withdrawalsPwd");
		String withdrawalsPwdOld=form.getString("withdrawalsPwdOld");
		
		String prefix = WebAop.getReqPrefix(req);
		Account a=null;
		if(null!=withdrawalsPwd && !withdrawalsPwd.equals("")){
			if(prefix.equals("/shop/")){
				myFrameU.sms.util.Util.verYZM(req);
				Shop shop = (Shop)req.getSession().getAttribute("myShop");
				if(null!=shop){
					req.setAttribute("myShop", shop);
					a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
				}
			}else if(prefix.equals("/user/")){
				myFrameU.sms.util.Util.verYZM(req);
				User user = (User)req.getSession().getAttribute("myUser");
				req.setAttribute("myUser", user);
				if(null!=user){
					a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
				}
			}else if(prefix.equals("/admin/")){
				Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
				req.setAttribute("myAdmin", admin);
				if(null!=admin){
					a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Admin.class.getName(),admin.getId()});
				}
			}
		}else{
			throw new MyStrException("抱歉，请输入新支付密码");
		}
		if(null!=a){
			String oldPwdDB = a.getWithdrawalsPwd();
			if(null==oldPwdDB){
				oldPwdDB="";
			}
			if(withdrawalsPwdOld.equals(oldPwdDB)){
				if(!withdrawalsPwd.equals(withdrawalsPwdOld)){
					PasswordUtil.verPassword(withdrawalsPwd);
					a.setWithdrawalsPwd(withdrawalsPwd);
					aBiz.modifyObject(a);
				}else{
					throw new MyStrException("抱歉，您输入的新支付密码和原先的密码相同!");	
				}
			}else{
				throw new MyStrException("抱歉，您输入的老密码不正确");
			}
		}
	}
	@RequestMapping(value={"shop/security/toAccount","user/security/toAccount","admin/security/toAccount"})
	public String security2Account(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String prefix = WebAop.getReqPrefix(req);
		if(prefix.equals("/shop/")){
			Shop shop = (Shop)req.getSession().getAttribute("myShop");
			req.setAttribute("myShop", shop);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
			req.setAttribute("account", a);
			return "manage/shop/security/account";
		}else if(prefix.equals("/user/")){
			User user = (User)req.getSession().getAttribute("myUser");
			req.setAttribute("myUser", user);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
			req.setAttribute("account", a);
			return "manage/user/security/account";
		}else if(prefix.equals("/admin/")){
			Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
			req.setAttribute("myAdmin", admin);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Admin.class.getName(),admin.getId()});
			req.setAttribute("account", a);
			return "manage/admin/security/account";
		}else{
			return "";
		}
	}
	
	@RequestMapping(value={"shop/security/account","user/security/account"})
	public void securityModifyAccount(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String zhifubao=form.getString("zhifubao");
		myFrameU.sms.util.Util.verYZM(req);
		String prefix = WebAop.getReqPrefix(req);
		Account a=null;
		if(null!=zhifubao && !zhifubao.equals("")){
			if(prefix.equals("/shop/")){
				Shop shop = (Shop)req.getSession().getAttribute("myShop");
				if(null!=shop){
					req.setAttribute("myShop", shop);
					a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
				}
			}else{
				User user = (User)req.getSession().getAttribute("myUser");
				req.setAttribute("myUser", user);
				if(null!=user){
					a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
				}
			}
		}else{
			throw new MyStrException("抱歉，请输入新支付宝账号");
		}
		if(null!=a){
			String zhifubaoOld = a.getZhifubao();
			if(null==zhifubaoOld){
				zhifubaoOld="";
			}
			if(!zhifubao.equals(zhifubaoOld)){
				a.setZhifubao(zhifubao);
				aBiz.modifyObject(a);
			}else{
				throw new MyStrException("抱歉，您输入的新支付宝账号和原先的支付宝账号相同!");	
			}
		}
	}
	
	
	
	@RequestMapping(value={"admin/security/account"})
	public void securityModifyAccountAdmin(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String zhifubao=form.getString("zhifubao");
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		if(null!=admin){
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Admin.class.getName(),admin.getId()});
			String zhifubaoOld = a.getZhifubao();
			if(null==zhifubaoOld){
				zhifubaoOld="";
			}
			if(!zhifubao.equals(zhifubaoOld)){
				a.setZhifubao(zhifubao);
				aBiz.modifyObject(a);
			}else{
				throw new MyStrException("抱歉，您输入的新支付宝账号和原先的支付宝账号相同!");	
			}
		}
	}
	
	
	@RequestMapping(value={"shop/security/toLookPassword","user/security/toLookPassword","admin/security/toLookPassword",
			"/wrap/user/security/toLookPassword","/wrap/shop/security/toLookPassword"})
	public String security2lookPassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String prefix = WebAop.getReqPrefix(req);
		if(prefix.equals("/shop/")){
			Shop shop = (Shop)req.getSession().getAttribute("myShop");
			req.setAttribute("myShop", shop);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
			req.setAttribute("account", a);
			return "manage/shop/security/lookPwd";
		}else if(prefix.equals("/user/")){
			User user = (User)req.getSession().getAttribute("myUser");
			req.setAttribute("myUser", user);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
			req.setAttribute("account", a);
			return "manage/user/security/lookPwd";
		}else if(prefix.equals("/admin/")){
			Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
			req.setAttribute("myAdmin", admin);
			Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Admin.class.getName(),admin.getId()});
			req.setAttribute("account", a);
			return "manage/admin/security/lookPwd";
		}else{
			if(prefix.equals("/wrap/user/")){
				User user = (User)req.getSession().getAttribute("myUser");
				req.setAttribute("myUser", user);
				Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
				req.setAttribute("account", a);
				return "wrap/manage/user/security/lookPwd";
			}else if(prefix.equals("/wrap/shop/")){
				Shop shop = (Shop)req.getSession().getAttribute("myShop");
				req.setAttribute("myShop", shop);
				Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
				req.setAttribute("account", a);
				return "wrap/manage/shop/security/lookPwd";
			}else{
				return "";
			}
			
		}
	}
	
	@RequestMapping(value={"shop/security/lookPassword","user/security/lookPassword","admin/security/lookPassword"})
	public void securitylookPassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String prefix = WebAop.getReqPrefix(req);
		myFrameU.sms.util.Util.verYZM(req);
		Account a = null;
		if(prefix.equals("/shop/")){
			Shop shop = (Shop)req.getSession().getAttribute("myShop");
			req.setAttribute("myShop", shop);
			a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
		}else if(prefix.equals("/user/")){
			User user = (User)req.getSession().getAttribute("myUser");
			req.setAttribute("myUser", user);
			a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
		}else if(prefix.equals("/admin/")){
			Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
			req.setAttribute("myAdmin", admin);
			a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Admin.class.getName(),admin.getId()});
		}else{
		}
		if(null!=a){
			String pwd =  a.getWithdrawalsPwd();
			if(null==pwd || pwd.equals("")){
				pwd="您还没有设置财务密码";
			}
			
			this.renderData(res,pwd);
		}
	}
	
}
