package hhr.order.biz;

import hhr.message.MessageUtil;
import hhr.order.entity.Order;
import hhr.order.entity.OrderItem;
import hhr.order.util.OrderUtil;
import hhr.user.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myFrame.cache.CacheKey;
import myFrameU.account.biz.AccountBizI;
import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.admin.entity.Admin;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductPrice;
import myFrameU.product.util.ProductPriceUtil;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;

import org.springframework.stereotype.Service;
@Service("orderBiz")
public class OrderBizImpl extends AbstractBizImpl implements OrderBizI {

	@Override
	public void addOrder(Order o) throws Exception {
		aDao.insertObject(o);
	}

	@Override
	public User payOrder(User user,Order o,AccountBizI aBiz, UICacheManager uICacheManager) throws Exception {
		/**
		 * 判断o的状态是否可以支付
		 * 支付的时候要操作admin账户、user账户、推荐人账号三者
		 * 
		 * 		修改product的仓库
		 * 		用户积分
		 */
		Admin admin = (Admin)aBiz.findObjectById("from Admin as a where a.id=1", null);
		String status = o.getStatus();
		if(EnumUtil.equalsE(Order.STATUS.WAITPAY, status)){
			/**
			 * 1、判断钱够不够
			 * 2、用户支付
			 * 3、匹配
			 */
			Account userAccount = aBiz.account_findAccount(User.class.getName(), user.getId());
			if(null!=userAccount){
				boolean enough = aBiz.account_enoughAvailablePriceInAccount(userAccount, 0, o.getTotalPrice());
				if(enough){
					//钱足够
					boolean geiTJR=false;
					int tuijianRenId=o.getTuijianRenId();
					if(tuijianRenId==0){
						geiTJR=false;
					}else{
						float toHehuorenPrice = o.getToHehuorenPrice();
						if(toHehuorenPrice==0){
							geiTJR=false;
						}else{
							geiTJR=true;
						}
					}
					
					
					
					
					int tjrId = user.getTuijianRenId();
					Account tjrIncomeAccount=aBiz.account_findAccount(User.class.getName(), tjrId);
					Account adminIncomeAccount=aBiz.account_findAccount(Admin.class.getName(), 1);
					Map<String,String> aiInfoMap = new HashMap<String,String>();
					aiInfoMap.put("spendingAccountItemInfo", "用户下单购买产品");
					aiInfoMap.put("tjrIncomeAccountItemInfo", "您的粉丝["+user.getNicheng()+"]购买产品，支付给您的销售佣金");
					aiInfoMap.put("adminIncomeAccountItemInfo", "用户下单购买产品，支付给平台");
					
					Map<String,String> extraMap = new HashMap<String,String>();
					String extra="{'orderId':'"+o.getId()+"'}";
					extraMap.put("spendingAccountItemExtra", extra);
					extraMap.put("tjrIncomeAccountItemExtra", extra);
					extraMap.put("adminIncomeAccountItemExtra", extra);
					
					
					List<Integer> proIdList  = new ArrayList<Integer>();
					Map<String,AccountItem> aiMap=aBiz.trade_xsxc(o.getToWebPrice(), o.getToHehuorenPrice(), userAccount, tjrIncomeAccount, adminIncomeAccount, aiInfoMap, extraMap);
					if(null!=aiMap){
						//付款之后，要修改order状态
						o.setStatus(Order.STATUS.PAYED.toString());
						o.setPayDate(new Date());
						aDao.updateObject(o);
						
						

						/**
						 * 处理产品的销量、库存
						 */
						HashMap<Integer,Integer> pIdCountMap = new HashMap<Integer,Integer>();
						Set<OrderItem> oiSet = o.getOiSet();
						if(null!=oiSet){
							OrderItem oi = null;
							Iterator it = oiSet.iterator();
							int productId=0;
							int ocount = 0;
							while(it.hasNext()){
								oi=(OrderItem)it.next();
								productId = oi.getProductId();
								proIdList.add(productId);
								ocount = oi.getOcount();
								aDao.j_execute("update product_product set productCount=productCount-?,saleCount=saleCount+? where id=?", new Object[]{ocount,ocount,productId});
							}
						}
					}
					
					
					//付款之后，用户积分
					Global g18=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 18);
					if(null!=g18){
						String g18value = g18.getMyValue();
						if(null!=g18value && !g18value.equals("") && !g18value.equals("0")){
							int g18valueInit = Integer.parseInt(g18value);
							if(g18valueInit>0){
								float totalPrice = o.getTotalPrice();
								float thetotalScore=(totalPrice/100)*g18valueInit;
								user.setTotalScore(user.getTotalScore()+thetotalScore);
								aBiz.modifyObject(user);
							}
						}
					}
					
					
					if(geiTJR){
						//给推荐人销售佣金信息
						User tjr = (User)aDao.queryObjectById("from User as u where u.id=?", new Object[]{tjrId});
						if(null!=tjr){
							HashMap<String,Object> args=new HashMap<String,Object>();
							args.put("user", tjr);
							args.put("order", o);
							MessageUtil.sendMssage(MessageUtil.MssageTYPE.TOTuijianren_icome_order.toString(),
									MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
									args,this);
						}
					}else{
						//不给推荐人费用
						
					}
					
					//该用户付款成功后，短信通知
					HashMap<String,Object> args=new HashMap<String,Object>();
					args.put("wxId", user.getWxId());
					args.put("order", o);
					MessageUtil.sendMssage(MessageUtil.MssageTYPE.Order_Payed_SUC_QH.toString(),
							MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
							args,this);
					
					
					
					
					
					/**
					 * 给管理员发新单通知
					 */
					
					HashMap<String,Object> argstoAdmin = new HashMap<String,Object>();
					argstoAdmin.put("order", o);
					argstoAdmin.put("wxId", admin.getWxId());
					argstoAdmin.put("adminPhone", admin.getTelPhone());
					MessageUtil.sendMssage(MessageUtil.MssageTYPE.Order_Payed_SUC_TOADMIN.toString(),
							MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), argstoAdmin, aBiz);
					
					
					
					
					
					/**
					 * 给管理员发库存存活不足通知
					 */
					Global g17 = CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 17);
					if(null!=g17){
						String g17Value = g17.getMyValue();
						if(null!=g17Value && !g17Value.equals("")){
							int g17ValueI = new Integer(g17Value);
							if(g17ValueI>0){
								if(null!=proIdList){
									int size = proIdList.size();
									if(size>0){
										int productId = 0;
										Product p  = null;
										for(int i=0;i<size;i++){
											productId = proIdList.get(i);
											p=(Product)aDao.queryObjectById("from Product as p where p.id=?", new Object[]{productId});
											if(null!=p){
												int pcount = p.getProductCount();
												if(pcount<=g17ValueI){
													//发送存货不足通知
													HashMap<String,Object> argswx = new HashMap<String,Object>();
													argswx.put("product", p);
													argswx.put("wxId", admin.getWxId());
													
													argswx.put("adminPhone", admin.getTelPhone());
													MessageUtil.sendMssage(MessageUtil.MssageTYPE.ProductCOUNT.toString(),
															MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), argswx, aBiz);
												}
											}
										}
										
										
									}
								}
							}
						}
					}
					
					
				}else{
					throw new MyStrException("抱歉，您的可用余额不足，请充值");
				}
			}
		}else{
			throw new MyStrException("该订单状态为非等待支付状态");
		}
		return user;
	}
	

	@Override
	public void closeOrder(Order o) throws Exception {
		/**
		 * 判断能否关闭
		 * 	1、等待支付的状态可以被关闭，其他状态不能关闭
		 */
		if(null!=o){
			String status = o.getStatus();
			if(EnumUtil.equalsE(Order.STATUS.WAITPAY, status)){
				o.setStatus(Order.STATUS.CLOSE.toString());
				aDao.updateObject(o);
			}else{
				throw new MyStrException("抱歉，该订单状态为非待支付，不能关闭");
			}
		}
	}

	
	
	//============================================================================
	@Override
	public User payOrder_3(Order o,AccountBizI aBiz, UICacheManager uICacheManager) throws Exception {
		/**
		 * 判断o的状态是否可以支付
		 * 支付的时候要操作admin账户、user账户、推荐人账号三者
		 * 
		 * 		修改product的仓库
		 * 		用户积分
		 */
		User user = null;
		Admin admin = (Admin)aBiz.findObjectById("from Admin as a where a.id=1", null);
		String status = o.getStatus();
		if(EnumUtil.equalsE(Order.STATUS.PAYED, status)){
			int userId = o.getUserId();
			int tjrId = o.getTuijianRenId();
			
			user = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{userId});
			if(null!=user){

				/*o.setStatus(Order.STATUS.PAYED.toString());
				o.setPayDate(new Date());*/
				aDao.updateObject(o);
				
				
				
				/**
				 * 付款成功，现在客户要求，只要订购了一次产品，即可成为正式的合伙人
				 * 		哎，那就按照客户的新新需求来修改吧
				 */
				int levelId = user.getUserLevelId();
				if(levelId!=3){
					//一定要记住，先removejob，因为在removejob里是根据user的levelId判断的
					UserUtil.beZhengshiHHR_removeJOB(user, this);
					user.setUserLevelId(3);
					user.setBeDate(new Date());
					aDao.updateObject(user);
				}

				/**
				 * 处理产品的销量、库存
				 * 		----> 
				 * 		产品价格的销量库存
				 */
				List<Integer> proIdList  = new ArrayList<Integer>();
				HashMap<Integer,Integer> pIdCountMap = new HashMap<Integer,Integer>();
				Set<OrderItem> oiSet = o.getOiSet();
				if(null!=oiSet){
					OrderItem oi = null;
					Iterator it = oiSet.iterator();
					//int productId=0;
					int productPriceId = 0;
					int ocount = 0;
					while(it.hasNext()){
						oi=(OrderItem)it.next();
						//productId = oi.getProductId();
						productPriceId=oi.getProductPriceId();
						proIdList.add(productPriceId);
						ocount = oi.getOcount();
						aDao.j_execute("update product_productPrice set productCount=productCount-?,saleCount=saleCount+? where id=?", 
								new Object[]{ocount,ocount,productPriceId});
					}
				}
				
				
				//统一处理product的库存和销量
				OrderUtil.againJSProducts_counts(o, aBiz);

				//付款之后，用户积分
				Global g18=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 18);
				if(null!=g18){
					String g18value = g18.getMyValue();
					if(null!=g18value && !g18value.equals("") && !g18value.equals("0")){
						int g18valueInit = Integer.parseInt(g18value);
						if(g18valueInit>0){
							float totalPrice = o.getTotalPrice();
							float thetotalScore=(totalPrice/100)*g18valueInit;
							user.setTotalScore(user.getTotalScore()+thetotalScore);
							aBiz.modifyObject(user);
						}
					}
				}
				
				
				//该用户付款成功后，短信通知
				HashMap<String,Object> args=new HashMap<String,Object>();
				args.put("wxId", user.getWxId());
				args.put("order", o);
				MessageUtil.sendMssage(MessageUtil.MssageTYPE.Order_Payed_SUC_QH.toString(),
						MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
						args,this);
				
				
				
				
				//给推荐人销售佣金信息
				if(tjrId!=0){
					float toHHRPrice = o.getToHehuorenPrice();
					if(toHHRPrice==0){
						System.out.println("佣金为0，不给！");
					}else if(toHHRPrice<=0.001){
						System.out.println("佣金小于0.001，不给！");
					}else{
						User tjr = (User)aDao.queryObjectById("from User as u where u.id=?", new Object[]{tjrId});
						if(null!=tjr){
							Account tjrIncomeAccount=aBiz.account_findAccount(User.class.getName(), tjrId);
							if(null!=tjrIncomeAccount){
								String extraData="{'orderId':'"+o.getId()+"'}";
								String info = "您的粉丝["+user.getNicheng()+"]购买产品，支付给您的销售佣金";
								AccountItem ai = aBiz.income_xsxc(tjrIncomeAccount, toHHRPrice, extraData, 
										AccountItem.STATUS.FINISH.toString(), info, 
										AccountItem.PRICETYPE.XS.toString());
								if(null!=ai){
									HashMap<String,Object> args_tjr=new HashMap<String,Object>();
									args_tjr.put("user", tjr);
									args_tjr.put("order", o);
									MessageUtil.sendMssage(MessageUtil.MssageTYPE.TOTuijianren_icome_order.toString(),
											MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
											args_tjr,this);
								}
							}
						}
					}
				}
				
				
				

				/**
				 * 给管理员发新单通知
				 */
				
				HashMap<String,Object> argstoAdmin = new HashMap<String,Object>();
				argstoAdmin.put("order", o);
				argstoAdmin.put("wxId", admin.getWxId());
				argstoAdmin.put("adminPhone", admin.getTelPhone());
				MessageUtil.sendMssage(MessageUtil.MssageTYPE.Order_Payed_SUC_TOADMIN.toString(),
						MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), argstoAdmin, aBiz);
				
				
				
				
				
				

				/**
				 * 给管理员发库存存活不足通知
				 */
				Global g17 = CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 17);
				if(null!=g17){
					String g17Value = g17.getMyValue();
					if(null!=g17Value && !g17Value.equals("")){
						int g17ValueI = new Integer(g17Value);
						if(g17ValueI>0){
							if(null!=proIdList){
								int size = proIdList.size();
								if(size>0){
									//int productId = 0;
									//Product p  = null;
									int productPriceId=0;
									ProductPrice pp = null;
									for(int i=0;i<size;i++){
										//productId = proIdList.get(i);
										productPriceId=proIdList.get(i);
										pp=(ProductPrice)aDao.queryObjectById("from ProductPrice as pp where pp.id=?", 
												new Object[]{productPriceId});
										//p=(Product)aDao.queryObjectById("from Product as p where p.id=?", new Object[]{productId});
										if(null!=pp){
											int pcount=pp.getProductCount();
											//int pcount = p.getProductCount();
											if(pcount<=g17ValueI){
												//发送存货不足通知
												HashMap<String,Object> argswx = new HashMap<String,Object>();
												argswx.put("productPrice", pp);
												argswx.put("wxId", admin.getWxId());
												
												argswx.put("adminPhone", admin.getTelPhone());
												MessageUtil.sendMssage(MessageUtil.MssageTYPE.ProductCOUNT.toString(),
														MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), argswx, aBiz);
											}
										}
									}
									
									
								}
							}
						}
					}
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			}
			
			
			
			
		}else{
			throw new MyStrException("该订单状态为非等待支付状态");
		}
		return user;
	}
	
	
	
	
	@Override
	public User zitifukuan_picked(Order o,AccountBizI aBiz, UICacheManager uICacheManager) throws Exception {
		/**
		 * 判断o的状态是否可以支付
		 * 支付的时候要操作admin账户、user账户、推荐人账号三者
		 * 
		 * 		修改product的仓库
		 * 		用户积分
		 */
		User user = null;
		Admin admin = (Admin)aBiz.findObjectById("from Admin as a where a.id=1", null);
		String status = o.getStatus();
		if(EnumUtil.equalsE(Order.STATUS.PAYING, status)){
			int userId = o.getUserId();
			int tjrId = o.getTuijianRenId();
			
			user = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{userId});
			if(null!=user){
				
				/**
				 * 付款成功，现在客户要求，只要订购了一次产品，即可成为正式的合伙人
				 * 		哎，那就按照客户的新新需求来修改吧
				 */
				int levelId = user.getUserLevelId();
				if(levelId!=3){
					//一定要记住，先removejob，因为在removejob里是根据user的levelId判断的
					UserUtil.beZhengshiHHR_removeJOB(user, this);
					user.setUserLevelId(3);
					user.setBeDate(new Date());
					aDao.updateObject(user);
				}

				/**
				 * 处理产品的销量、库存
				 */
				List<Integer> proIdList  = new ArrayList<Integer>();
				HashMap<Integer,Integer> pIdCountMap = new HashMap<Integer,Integer>();
				Set<OrderItem> oiSet = o.getOiSet();
				if(null!=oiSet){
					OrderItem oi = null;
					Iterator it = oiSet.iterator();
					//int productId=0;
					int productPriceId=0;
					int ocount = 0;
					while(it.hasNext()){
						oi=(OrderItem)it.next();
						//productId = oi.getProductId();
						productPriceId = oi.getProductPriceId();
						//proIdList.add(productId);
						proIdList.add(productPriceId);
						ocount = oi.getOcount();
						aDao.j_execute("update product_productPrice set productCount=productCount-?,saleCount=saleCount+? where id=?", 
								new Object[]{ocount,ocount,productPriceId});
						
					}
				}
				
				//统一处理product的库存和销量
				OrderUtil.againJSProducts_counts(o, aBiz);

				//付款之后，用户积分
				Global g18=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 18);
				if(null!=g18){
					String g18value = g18.getMyValue();
					if(null!=g18value && !g18value.equals("") && !g18value.equals("0")){
						int g18valueInit = Integer.parseInt(g18value);
						if(g18valueInit>0){
							float totalPrice = o.getTotalPrice();
							float thetotalScore=(totalPrice/100)*g18valueInit;
							user.setTotalScore(user.getTotalScore()+thetotalScore);
							aBiz.modifyObject(user);
						}
					}
				}
				
				
				
				
				//给推荐人销售佣金信息
				if(tjrId!=0){
					float toHHRPrice = o.getToHehuorenPrice();
					if(toHHRPrice==0){
						System.out.println("佣金为0，不给！");
					}else if(toHHRPrice<=0.001){
						System.out.println("佣金小于0.001，不给！");
					}else{
						User tjr = (User)aDao.queryObjectById("from User as u where u.id=?", new Object[]{tjrId});
						if(null!=tjr){
							Account tjrIncomeAccount=aBiz.account_findAccount(User.class.getName(), tjrId);
							if(null!=tjrIncomeAccount){
								String extraData="{'orderId':'"+o.getId()+"'}";
								String info = "您的粉丝["+user.getNicheng()+"]购买产品，支付给您的销售佣金";
								AccountItem ai = aBiz.income_xsxc(tjrIncomeAccount, toHHRPrice, extraData, 
										AccountItem.STATUS.FINISH.toString(), info, 
										AccountItem.PRICETYPE.XS.toString());
								if(null!=ai){
									HashMap<String,Object> args_tjr=new HashMap<String,Object>();
									args_tjr.put("user", tjr);
									args_tjr.put("order", o);
									MessageUtil.sendMssage(MessageUtil.MssageTYPE.TOTuijianren_icome_order.toString(),
											MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
											args_tjr,this);
								}
							}
						}
					}
				}
				
				

				/**
				 * 给管理员发库存存活不足通知
				 */
				Global g17 = CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 17);
				if(null!=g17){
					String g17Value = g17.getMyValue();
					if(null!=g17Value && !g17Value.equals("")){
						int g17ValueI = new Integer(g17Value);
						if(g17ValueI>0){
							if(null!=proIdList){
								int size = proIdList.size();
								if(size>0){
									//int productId = 0;
									int productPriceId=0;
									//Product p  = null;
									ProductPrice pp = null;
									for(int i=0;i<size;i++){
										productPriceId = proIdList.get(i);
										pp=(ProductPrice)aDao.queryObjectById("from ProductPrice as pp where pp.id=?", new Object[]{productPriceId});
										//p=(Product)aDao.queryObjectById("from Product as p where p.id=?", new Object[]{productId});
										if(null!=pp){
											int pcount = pp.getProductCount();
											if(pcount<=g17ValueI){
												//发送存货不足通知
												HashMap<String,Object> argswx = new HashMap<String,Object>();
												argswx.put("productPrice", pp);
												argswx.put("wxId", admin.getWxId());
												
												argswx.put("adminPhone", admin.getTelPhone());
												MessageUtil.sendMssage(MessageUtil.MssageTYPE.ProductCOUNT.toString(),
														MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), argswx, aBiz);
											}
										}
									}
									
									
								}
							}
						}
					}
				}
				
				
				o.setStatus(Order.STATUS.PICKUPED.toString());
				o.setPickDate(new Date());
				aBiz.modifyObject(o);
				
				
			}
		}else{
			throw new MyStrException("该订单状态为非等待支付状态");
		}
		return user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
