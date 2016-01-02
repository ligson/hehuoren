package hhr.message;

import hhr.order.entity.Order;
import hhr.order.entity.OrderItem;
import hhr.order.entity.PickUpAddress;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import myFrame.cache.CacheKey;
import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.apply.entity.Apply;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.product.entity.ProductPrice;
import myFrameU.shop.entity.Shop;
import myFrameU.sms.util.SendSms;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.date.DateUtils;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;
import myFrameU.weixin.base.entity.TemplateMsg;
import myFrameU.weixin.base.service.impl.SendMessageImpl;
import myFrameU.weixin.base.util.SendTemplateMsgArgsUtils;




/**
 * 
 * 这个类主要的作用就是再次包装各种通知用户的短信息方式
 * 		1、微信模板消息提醒
 * 		2、短信提醒
 *
 */
public class MessageUtil {
	public static UICacheManager uICacheManager;
	public enum MssageWay{
		WEIXIN,DUANXIN,WEIXIN_DUANXIN
	}
	/**
	 * 
	 * 比如提现申请的审核通过，也算是apply
	 *
	 */
	public enum MssageTYPE{
		RECHARGE_SUCCESS,Order_Add_SUC,Order_Payed_SUC,Order_Payed_SUC_QH,Order_Payed_HDFK_SUC_QH,APPLY
		,TOTuijianren_icome_order,HHR_SYQOVER_OK
		,ProductCOUNT,Order_Payed_SUC_TOADMIN,RECHARGE_SUCCESS_TOADMIN
		,ModifyOrderItem_PickAddress
	}
	
	
	public static void sendMssage(String mssageType,String mssageWay,HashMap<String,Object> args,AbstractBizI aBiz){
		try{
			if(null!=mssageType && !mssageType.equals("")){
				HashMap<String,Boolean> wayMap = getWay(mssageWay);
				boolean wx = wayMap.get("wx");
				boolean dx=wayMap.get("dx");
				if(EnumUtil.equalsE(MssageTYPE.RECHARGE_SUCCESS, mssageType)){
					//充值成功
					AccountItem ai = (AccountItem)args.get("accountItem");
					Account a = (Account)args.get("account");
					if(null!=ai && null!=a){
						if(wx){
							String wxId = (String)args.get("wxId");
							if(null!=wxId && !wxId.equals("")){
								System.out.println("发送模板消息。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "您微信充值成功！");
								valuesMap.put("accountType", "用户名");
								valuesMap.put("account", a.getWhoName());
								valuesMap.put("amount", ai.getPrice()+"元");
								valuesMap.put("result", "充值成功");
								valuesMap.put("remark", "感谢您支持爱尔特官网");
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.RECHARGE_SUCCESS.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
							}
						}
						
						if(dx){
							String phone = getPhone( ai.getWhoclassName(), ai.getWhoId(), aBiz);
							if(null!=phone){
								SendSms.sdk_mt(phone,  "恭喜你，充值成功，充值金额"+ai.getPrice()+"元", aBiz, "", null);
							}
						}
						
					}
				}else if(EnumUtil.equalsE(MssageTYPE.APPLY, mssageType)){
					//审批成功
					Apply apply = (Apply)args.get("apply");
					if(null!=apply){
						if(dx){
							myFrameU.apply.util.ApplyUtil.smsSendApplyFinished_dx(aBiz, apply);
						}
						if(wx){
							myFrameU.apply.util.ApplyUtil.smsSendApplyFinished_wx(aBiz, apply);
						}
						
					}
				}else if(EnumUtil.equalsE(MssageTYPE.Order_Payed_SUC_QH, mssageType)){
					//下单成功,且付款成功，则发送信息告知取货地点
					Order order = (Order)args.get("order");
					if(null!=order){
						/**
						 * {{first.DATA}}
					订单号：{{keyword1.DATA}}
					订单金额：{{keyword2.DATA}}
					消费门店：{{keyword3.DATA}}
					{{remark.DATA}}
						 */
						List<Integer> pickIdList = new ArrayList<Integer>();
						Set<OrderItem> oiSet = order.getOiSet();
						StringBuffer sb = new StringBuffer();
						if(null!=oiSet){
							Iterator it = oiSet.iterator();
							OrderItem oi = null;
							int pickId = 0;
							while(it.hasNext()){
								oi = (OrderItem)it.next();
								if(null!=oi){
									pickId = oi.getPickupAddressId();
									if(!pickIdList.contains(pickId)){
										pickIdList.add(pickId);
										PickUpAddress pick = CacheKey.CKPickUpAddress.ALLMAP.getObject(uICacheManager, pickId);
										sb.append(pick.getAddressStr()).append("[").append(pick.getTelPhone()).append("][").append(pick.getMarkedNum()).append("],");
									}
								}
							}
						}
						if(wx){
							String wxId = (String)args.get("wxId");
							if(null!=wxId && !wxId.equals("")){
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "恭喜你，订单付款成功，请尽快去自提货品");
								valuesMap.put("keyword1", order.getMarkedNum());
								valuesMap.put("keyword2", order.getTotalPrice()+"元");
								valuesMap.put("keyword3", sb.toString());
								valuesMap.put("remark", "请尽快到以上地点自取货品");
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.Order_Payed_SUC_QH.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
								
							}
						}
						
						
						if(dx){
							String phone = order.getShouhuoTelphone();
							//String phone = getPhone(User.class.getName(), order.getUserId(), aBiz);
							if(null!=phone){
								SendSms.sdk_mt(phone,  "恭喜你，订单支付成功，金额"+order.getTotalPrice()+"元，请到"+sb.toString()+"取货", aBiz, "", null);
							}
						}
						
					}
				}else if(EnumUtil.equalsE(MssageTYPE.Order_Payed_HDFK_SUC_QH, mssageType)){
					//下单成功,且付款成功，则发送信息告知取货地点
					Order order = (Order)args.get("order");
					if(null!=order){
						/**
						 * {{first.DATA}}
					订单号：{{keyword1.DATA}}
					订单金额：{{keyword2.DATA}}
					消费门店：{{keyword3.DATA}}
					{{remark.DATA}}
						 */
						Set<OrderItem> oiSet = order.getOiSet();
						StringBuffer sb = new StringBuffer();
						if(null!=oiSet){
							List<Integer> pickIdList = new ArrayList<Integer>();
							Iterator it = oiSet.iterator();
							OrderItem oi = null;
							int pickId = 0;
							while(it.hasNext()){
								oi = (OrderItem)it.next();
								if(null!=oi){
									pickId = oi.getPickupAddressId();
									if(!pickIdList.contains(pickId)){
										pickIdList.add(pickId);
										PickUpAddress pick = CacheKey.CKPickUpAddress.ALLMAP.getObject(uICacheManager, pickId);
										sb.append(pick.getAddressStr()).append("[").append(pick.getTelPhone()).append("][").append(pick.getMarkedNum()).append("],");
									}
									
								}
							}
						}
						if(wx){
							String wxId = (String)args.get("wxId");
							if(null!=wxId && !wxId.equals("")){
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "恭喜你，您的订单选择为自提付款，请尽快去自提点自提");
								valuesMap.put("keyword1", order.getMarkedNum());
								valuesMap.put("keyword2", order.getTotalPrice()+"元");
								valuesMap.put("keyword3", sb.toString());
								valuesMap.put("remark", "请尽快到以上地点自取货品");
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.Order_Payed_SUC_QH.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
								
							}
						}
						
						
						if(dx){
							String phone = order.getShouhuoTelphone();
							//String phone = getPhone(User.class.getName(), order.getUserId(), aBiz);
							if(null!=phone){
								SendSms.sdk_mt(phone,  "恭喜您，您选择自提付款，金额"+order.getTotalPrice()+"元，请到"+sb.toString()+"取货", aBiz, "", null);
							}
						}
						
					}
				}else if(EnumUtil.equalsE(MssageTYPE.HHR_SYQOVER_OK, mssageType)){
					//试用期过了，成为正式的合伙人
					User user = (User)args.get("user");
					if(null!=user){
						if(dx){
							String phone = user.getTelPhone();
							if(null!=phone && !phone.equals("")){
								SendSms.sdk_mt(phone,  "恭喜你，试用期已过，您成为正式的合伙人", aBiz, "", null);
							}
						}
						if(wx){
							/**
							 * {{first.DATA}}
				申请人：{{keyword1.DATA}}
				申请时间：{{keyword2.DATA}}
				审核状态：{{keyword3.DATA}}
				{{remark.DATA}}
							 */
							String wxId = (String)user.getWxId();
							if(null!=wxId && !wxId.equals("")){
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "恭喜你，您作为试用期的合伙人，在试用期内吸纳的粉丝数合格，现在成为正式合伙人");
								valuesMap.put("keyword1",user.getNicheng());
								valuesMap.put("keyword2",DateUtils.format_all(user.getBeDate()) );
								valuesMap.put("keyword3", "成为正式合伙人");
								valuesMap.put("remark", "您已经成为正式合伙人，可以吸纳粉丝，赚取佣金");
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.HHR_SYQOVER_OK.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
								
							}
						}
					}
				}else if(EnumUtil.equalsE(MssageTYPE.TOTuijianren_icome_order, mssageType)){
					//试用期过了，成为正式的合伙人
					User user = (User)args.get("user");//推荐人
					Order order = (Order)args.get("order");
					if(null!=user && null!=order){
						if(dx){
							String phone = user.getTelPhone();
							if(null!=phone && !phone.equals("")){
								SendSms.sdk_mt(phone,   "恭喜你，您的粉丝成功下单，您获取销售佣金"+order.getToHehuorenPrice()+"元", aBiz, "", null);
							}
						}
						if(wx){
							/**
							 * {{first.DATA}}
								订单号：{{keyword1.DATA}}
								订单金额：{{keyword2.DATA}}
								分成金额：{{keyword3.DATA}}
								时间：{{keyword4.DATA}}
								{{remark.DATA}}
							 */
							String wxId = (String)user.getWxId();
							if(null!=wxId && !wxId.equals("")){
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "恭喜你，您的粉丝"+order.getUserName()+"成功下单，您获取销售佣金"+order.getToHehuorenPrice()+"元");
								valuesMap.put("keyword1",order.getMarkedNum());
								valuesMap.put("keyword2",order.getTotalPrice()+"");
								valuesMap.put("keyword3",order.getToHehuorenPrice()+"");
								Date payDate = order.getPayDate();
								valuesMap.put("keyword4",DateUtils.format_all(payDate));
								valuesMap.put("remark", "恭喜您已经成功获取了一笔销售佣金！");
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.TOTuijianren_icome_order.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
								
							}
						}
					}
				}else if(EnumUtil.equalsE(MssageTYPE.ProductCOUNT, mssageType)){
					//产品库存警告
					//Product p = (Product)args.get("product");
					ProductPrice pp = (ProductPrice)args.get("productPrice");
					if(null!=pp){
						if(dx){
							String adminPhone=(String)args.get("adminPhone");
							if(null!=adminPhone && !adminPhone.equals("")){
								String verPhone = PhoneUtil.vailterTelPhone(adminPhone);
								if(null==verPhone){
									//String pname = p.getName();
									String pname = pp.getProductName();
									if(null!=pname && pname.equals("")){
										if(pname.length()>30){
											pname=pname.substring(0,29);
										}
										String smsContent="系统中产品库存不足["+pname+",剩余"+pp.getProductCount()+"个]";
										SendSms.sdk_mt(adminPhone,   smsContent, aBiz, "", null);
									}
								}
							}
						}
						
						
						if(wx){
							String wxId = (String)args.get("wxId");
							if(null!=wxId && !wxId.equals("")){
								/**
								 * {{first.DATA}}
									仓库：{{keyword1.DATA}}
									商品：{{keyword2.DATA}}
									库存：{{keyword3.DATA}}
									时间：{{keyword4.DATA}}
									{{remark.DATA}}
								 */
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "您的系统中产品库存不足，请及时补货");
								valuesMap.put("keyword1","爱尔特合伙人");
								valuesMap.put("keyword2",pp.getProductName());
								valuesMap.put("keyword3",pp.getProductCount()+"");
								Date now = new Date();
								valuesMap.put("keyword4",DateUtils.format_all(now));
								valuesMap.put("remark", "情及时登录后台补货");
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.ProductCOUNT.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
							}
						}
						
						
						
					}
				}else if(EnumUtil.equalsE(MssageTYPE.Order_Payed_SUC_TOADMIN, mssageType)){
					//订单付款成功后，发送给admin一封信
					Order order = (Order)args.get("order");
					if(null!=order){
						if(dx){
							String adminPhone=(String)args.get("adminPhone");
							if(null!=adminPhone && !adminPhone.equals("")){
								String verPhone = PhoneUtil.vailterTelPhone(adminPhone);
								if(null==verPhone){
									String smsContent="恭喜您，本平台有新订单且支付成功，订单号："+order.getMarkedNum()+",订单金额："+order.getTotalPrice()+"元";
									SendSms.sdk_mt(adminPhone,   smsContent, aBiz, "", null);
								}
							}
						}
						
						if(wx){
							String wxId = (String)args.get("wxId");
							if(null!=wxId && !wxId.equals("")){
								
								/**
								 * {{first.DATA}}
									订单号：{{keyword1.DATA}}
									时间：{{keyword2.DATA}}
									{{remark.DATA}}
								 */
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "恭喜您，本平台有新订单且支付成功，请及时处理");
								valuesMap.put("keyword1",order.getMarkedNum());
								valuesMap.put("keyword2",DateUtils.format_all(order.getPayDate()));
								valuesMap.put("remark", "订单金额："+order.getTotalPrice()+"元");
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.Order_Payed_SUC_TOADMIN.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
								
							}
						}
					}
				}else if(EnumUtil.equalsE(MssageTYPE.RECHARGE_SUCCESS_TOADMIN, mssageType)){
					//给管理员的来款通知
					AccountItem ai = (AccountItem)args.get("accountItem");
					Account a = (Account)args.get("account");
					if(null!=ai && null!=a){
						if(dx){
							String adminPhone=(String)args.get("adminPhone");
							if(null!=adminPhone && !adminPhone.equals("")){
								String verPhone = PhoneUtil.vailterTelPhone(adminPhone);
								if(null==verPhone){
									String otName = null;
									String ot = ai.getOuterType();
									if(null!=ot && !ot.equals("")){
										if(ot.equals(AccountItem.OUTERTYPE.WEIXIN.toString())){
											otName="[微信支付]";
										}else if(ot.equals(AccountItem.OUTERTYPE.ZHIFUBAO.toString())){
											otName="[支付宝支付]";
										}
									}
									String smsContent="恭喜您，本平台有新的来款，来款明细单号"+ai.getMarkedNum()+",订单金额："+ai.getPrice()+"元"+otName+"";
									SendSms.sdk_mt(adminPhone,   smsContent, aBiz, "", null);
								}
							}
						}
						
						
						if(wx){
							String wxId = (String)args.get("wxId");
							if(null!=wxId && !wxId.equals("")){
								String otName = null;
								String ot = ai.getOuterType();
								if(null!=ot && !ot.equals("")){
									if(ot.equals(AccountItem.OUTERTYPE.WEIXIN.toString())){
										otName="[微信支付]";
									}else if(ot.equals(AccountItem.OUTERTYPE.ZHIFUBAO.toString())){
										otName="[支付宝支付]";
									}
								}
								/**
								 * {{first.DATA}}
									银行：{{keyword1.DATA}}
									金额：{{keyword2.DATA}}
									到帐时间：{{keyword3.DATA}}
									{{remark.DATA}}
								 */
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "恭喜您，本平台有新的来款");
								valuesMap.put("keyword1",otName);
								valuesMap.put("keyword2",ai.getPrice()+"元");
								valuesMap.put("keyword3",DateUtils.format_all(ai.getRelDate()));
								valuesMap.put("remark", "请尽快核实");
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.RECHARGE_SUCCESS_TOADMIN.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
							}
						}
						
						
						
					}
				}else if(EnumUtil.equalsE(MssageTYPE.ModifyOrderItem_PickAddress, mssageType)){
					//当一个订单下单后，修改取货地址，重新发送地址给用户。
					Order order = (Order)args.get("order");
					PickUpAddress pick = (PickUpAddress)args.get("pickUpAddress");
					if(null!=order && null!=pick){
						if(dx){
							String phone = order.getShouhuoTelphone();
							if(null!=phone){
								SendSms.sdk_mt(phone,  "您的订单更改自提地点成功，新自提点为"+pick.getAddressStr()+",电话"+pick.getTelPhone()+","+pick.getMarkedNum(), aBiz, "", null);
							}
						}
						
						if(wx){
							String wxId = (String)args.get("wxId");
							if(null!=wxId && !wxId.equals("")){
								
								/**
								 * {{first.DATA}}
									订单号：{{keyword1.DATA}}
									时间：{{keyword2.DATA}}
									{{remark.DATA}}
								 */
								HashMap<String,String> valuesMap = new HashMap<String,String>();
								valuesMap.put("first", "您的订单更改自提地点成功");
								valuesMap.put("keyword1",order.getMarkedNum());
								valuesMap.put("keyword2",DateUtils.format_all(new Date()));
								valuesMap.put("remark", "新自提点为："+pick.getAddressStr()+",电话"+pick.getTelPhone()+","+pick.getMarkedNum());
								
								TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.Order_Payed_SUC_TOADMIN.toString(),
										valuesMap);
								if(null!=tm){
									SendMessageImpl si = new SendMessageImpl();
									si.sendTemplate(tm, uICacheManager,aBiz);
								}
								
							}
						}
					}
					
					
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private static HashMap<String,Boolean> getWay(String mssageWay){
		HashMap<String,Boolean> map = new HashMap<String,Boolean>();
		boolean wx=false;
		boolean dx=false;
		if(EnumUtil.equalsE(MssageWay.WEIXIN_DUANXIN, mssageWay)){
			wx=true;
			dx=true;
			String dx_status = myFrameU.sms.init.InitMavenImpl.ic.getStatus();
			if(null==dx_status || !dx_status.equals("open")){
				dx=false;
			}
		}else if(EnumUtil.equalsE(MssageWay.WEIXIN, mssageWay)){
			wx=true;
		}else if(EnumUtil.equalsE(MssageWay.DUANXIN, mssageWay)){
			dx=true;
			String dx_status = myFrameU.sms.init.InitMavenImpl.ic.getStatus();
			if(null==dx_status || !dx_status.equals("open")){
				dx=false;
			}
		}
		map.put("wx", wx);
		map.put("dx", dx);
		
		
		
		return map;
	}
	
	
	
	
	private static String getPhone(String className,int whoId,AbstractBizI aBiz) throws Exception{
		String phone = null;
		if(null!=className && !className.equals("") && whoId!=0){
			if(className.equals(Shop.class.getName())){
				phone=(String)aBiz.j_queryObject("select phone from "+EntityTableUtil.tableNameC(Shop.class)+" as s where s.id=?", new Object[]{whoId});
			}else if(className.equals(User.class.getName())){
				phone=(String)aBiz.j_queryObject("select telPhone from "+EntityTableUtil.tableNameC(User.class)+" as s where s.id=?", new Object[]{whoId});
			}
		}
		return phone;
	}
	
	
	
}
