package myFrameU.account.biz;

import hhr.message.MessageUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.account.util.AccountUtilHHR;
import myFrameU.admin.entity.Admin;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyStrException;
import myFrameU.pay.alipay.com.alipay.util.ZFBCallbackEntity;
import myFrameU.shop.entity.Shop;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("accountBiz")
public class AccountBizImpl extends AbstractBizImpl implements AccountBizI {
	@Autowired
	public UICacheManager uICacheManager;
	
	
	public UICacheManager getuICacheManager() {
		return uICacheManager;
	}
	public void setuICacheManager(UICacheManager uICacheManager) {
		this.uICacheManager = uICacheManager;
	}
	private String getLock(Account account){
		String whoClassName=account.getWhoclassName();
		int whoId=account.getWhoId();
		//充值的时候，一定要加锁，其他的都先别操作了，我先充值
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
	
	@Override
	public AccountItem rechage(Account account, float price,String status,String outerType) throws Exception {
		AccountItem item=null;
		if(null!=account){
			synchronized(getLock(account)){
				String markedNum=new Date().getTime()+"";
				item=new AccountItem();
				//item.setOuterMarkedNum(outerType);
				item.setOuterType(outerType);
				item.setAccount(account);
				item.setPrice(price);
				item.setExtraData("");
				item.setInfo("充值"+price+"元");
				item.setItemType(AccountItem.ITEMTYPE.RECHARGE.toString());
				item.setMarkedNum(markedNum);
				if(null==status || status.equals("")){
					item.setStatus(AccountItem.STATUS.WAIT.toString());
				}else{
					item.setStatus(status);
				}
				
				item.setWhoclassName(account.getWhoclassName());
				item.setWhoId(account.getWhoId());
				item.setRelDate(new Date());
				aDao.insertObject(item);
				if(item.getId()>0){
					if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
						account_computingAccountPrice(account);
						/*account.setTotalPrice(account.getTotalPrice()+price);
						account.setAvailablePrice(account.getAvailablePrice()+price);
						aDao.updateObject(account);*/
					}
				}
			}
		}
		return item;
	}

	@Override
	public AccountItem alipayCallback(ZFBCallbackEntity zfbcbe) throws Exception {
		
		String out_trade_no=zfbcbe.getOut_trade_no();
		String trade_status=zfbcbe.getTrade_status();
		if(null!=trade_status && null!=out_trade_no){
			if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
				AccountItem ai=(AccountItem)aDao.queryObjectById("from AccountItem as ai where ai.markedNum=?", new Object[]{out_trade_no});
				if(null!=ai){
					int accountId=ai.getAccount().getId();
					synchronized(getLock(ai.getWhoclassName(),ai.getWhoId())){
						Account a=(Account)aDao.queryObjectById("from Account as a where a.id=?", new Object[]{accountId});
						if(null!=a){
							if(!ai.getCallbackHaveddeal()){
								//还没有处理
								String itemType=ai.getItemType();
								if(EnumUtil.equalsE(AccountItem.ITEMTYPE.RECHARGE, itemType)){
									//如果这个accountItem是充值记录
									//把ai的状态修改成finish的
									ai.setStatus(AccountItem.STATUS.FINISH.toString());
									ai.setOuterMarkedNum2(zfbcbe.getTrade_no());
									//a.setTotalPrice(a.getTotalPrice()+ai.getPrice());
									//a.setAvailablePrice(a.getAvailablePrice()+ai.getPrice());
									ai.setCallbackHaveddeal(true);
									aDao.updateObject(ai);
									//aDao.updateObject(a);
									account_computingAccountPrice(a);
									
									
									HashMap<String,Object> args=new HashMap<String,Object>();
									args.put("accountItem", ai);
									MessageUtil.sendMssage(MessageUtil.MssageTYPE.RECHARGE_SUCCESS.toString(),
											MessageUtil.MssageWay.DUANXIN.toString(), 
											args,this);
									
								}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.WITHDRAWALS, itemType)){
									//提现记录
								}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.INCOME, itemType)){
									//收入
								}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.SPENDING, itemType)){
									//支出
								}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.FROZEN, itemType)){
									//冻结
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	//先判断里面有没有这么多多钱
	@Override
	public AccountItem forzen(Account account, float price, String extraData,String info)
			throws Exception {
		Transaction trans = aDao.getHt().getSessionFactory().getCurrentSession().beginTransaction();
		System.out.println(trans+"-forzen");
		
		AccountItem item=null;
		if(null!=account){
			synchronized(getLock(account)){
				float availablePrice = account.getAvailablePrice();
				if(availablePrice>=price){
					String markedNum=new Date().getTime()+"";
					item=new AccountItem();
					item.setAccount(account);
					item.setPrice(price);
					item.setExtraData(extraData);
					item.setInfo(info);
					item.setItemType(AccountItem.ITEMTYPE.FROZEN.toString());
					item.setMarkedNum(markedNum);
					item.setStatus(AccountItem.STATUS.FINISH.toString());
					item.setWhoclassName(account.getWhoclassName());
					item.setWhoId(account.getWhoId());
					item.setRelDate(new Date());
					aDao.insertObject(item);
					if(item.getId()>0){
						account_computingAccountPrice(account);
					}
				}else{
					return null;
				}
			}
		}
		return item;
	}

	@Override
	public AccountItem closeAccountItem(AccountItem item) throws Exception {
		if(null!=item){
			Account a = account_findAccount(item.getWhoclassName(), item.getWhoId());
			if(null!=a){
				synchronized(getLock(a)){
					String oldStatus=item.getStatus();
					if(!EnumUtil.equalsE(AccountItem.STATUS.CLOSE, oldStatus)){
						String itemType=item.getItemType();
						if(EnumUtil.equalsE(AccountItem.ITEMTYPE.FROZEN, itemType)){
							//如果这个AI是要冻结一部分钱的，现在close，意思是解冻
							item.setStatus(AccountItem.STATUS.CLOSE.toString());
							aDao.updateObject(item);
							
							account_computingAccountPrice(a);
							return item;
						}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.SPENDING, itemType)){
							if(EnumUtil.equalsE(AccountItem.STATUS.WAIT, oldStatus)){
								//如果这个AI是支付的，现在close，就是要关闭交易
								//比如在买家中标之后，立马程序自动生成一个支付的交易明细，但是买家看到了却关闭了它，
								//意思就是我不要了，冻结的我也不要了
								item.setStatus(AccountItem.STATUS.CLOSE.toString());
								aDao.updateObject(item);
								return item;
								//因为你之前的状态是wait，所以不需要级联修改account
							}else if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, oldStatus)){
								//已经交易成功了的话，不可能要关闭交易
							}
						}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.INCOME, itemType)){
							//如果这个AI是收入的，收入的就没有需求要关闭它了。
							
						}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.RECHARGE, itemType)){
							//如果这个Ai是充值的，close的意思是我不充值了。
							if(EnumUtil.equalsE(AccountItem.STATUS.WAIT, oldStatus)){
								item.setStatus(AccountItem.STATUS.CLOSE.toString());
								aDao.updateObject(item);
								return item;
							}
						}else if(EnumUtil.equalsE(AccountItem.ITEMTYPE.WITHDRAWALS, itemType)){
							//如果这个Ai是提现的，也没有需求
							
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public AccountItem income(Account account, float price,String extraData, String status,String info)
			throws Exception {
		if(price==0){
			return null;
		}
		AccountItem item=null;
		if(null!=account){
			synchronized(getLock(account)){
				String markedNum=new Date().getTime()+"";
				item=new AccountItem();
				item.setAccount(account);
				item.setPrice(price);
				item.setExtraData(extraData);
				item.setInfo(info);
				item.setItemType(AccountItem.ITEMTYPE.INCOME.toString());
				item.setMarkedNum(markedNum);
				item.setWhoclassName(account.getWhoclassName());
				item.setWhoId(account.getWhoId());
				item.setRelDate(new Date());
				if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
					item.setStatus(AccountItem.STATUS.FINISH.toString());
				}else if(EnumUtil.equalsE(AccountItem.STATUS.WAIT, status)){
					item.setStatus(AccountItem.STATUS.WAIT.toString());
					//等待收入
				}
				aDao.insertObject(item);
				if(item.getId()>0){
					if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
						account_computingAccountPrice(account);
					}
				}
				return item;
			}
		}
		return null;
	}
	@Override
	public AccountItem spending(Account account, float price,
			String extraData, String status, String info) throws Exception {
		if(price==0){
			return null;
		}
		AccountItem item=null;
		if(null!=account){
			synchronized(getLock(account)){
				String markedNum=new Date().getTime()+"";
				item=new AccountItem();
				item.setAccount(account);
				item.setPrice(price);
				item.setExtraData(extraData);
				item.setInfo(info);
				item.setItemType(AccountItem.ITEMTYPE.SPENDING.toString());
				item.setMarkedNum(markedNum);
				item.setWhoclassName(account.getWhoclassName());
				item.setWhoId(account.getWhoId());
				item.setRelDate(new Date());
				
				if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
					item.setStatus(AccountItem.STATUS.FINISH.toString());
				}else if(EnumUtil.equalsE(AccountItem.STATUS.WAIT, status)){
					item.setStatus(AccountItem.STATUS.WAIT.toString());
					//等待支出
				}
				aDao.insertObject(item);
				if(item.getId()>0){
					if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
						account_computingAccountPrice(account);
					}
				}
				return  item;
			}
		}
		return null;
	}
	
	
	//
	@Override
	public List<AccountItem> findAIs(String whoClassName,int whoId,String status, String itemType,
			String extra) throws Exception {
		StringBuffer sb = new StringBuffer("from AccountItem as c where ");
		if(null==whoClassName || whoClassName.equals("")){
		}else{
			if(whoId!=0){
				sb.append("c.whoclassName='").append(whoClassName).append("' and c.whoId=").append(whoId);
			}
		}
		
		
		if(null==status || status.equals("")){
		}else{
			if(sb.toString().equals("")){
				sb.append("c.status='").append(status).append("' ");
			}else{
				sb.append(" and c.status='").append(status).append("' ");
			}
		}
		if(null==itemType || itemType.equals("")){
		}else{
			if(sb.toString().equals("")){
				sb.append("c.itemType='").append(itemType).append("' ");
			}else{
				sb.append(" and c.itemType='").append(itemType).append("' ");
			}
		}
		if(null==extra || extra.equals("")){
		}else{
			extra=extra.replaceAll("'", "''");
			if(sb.toString().equals("")){
				sb.append(" regexp(c.extraData,'").append(extra).append("')=1");
			}else{
				sb.append(" and  regexp(c.extraData,'").append(extra).append("')=1");
			}
		}
		List<AccountItem> list = null;
		if(!sb.toString().equals("")){
			//c.whoclassName=user_user and c.whoId=1 and c.status=FINISH  and c.itemType=FROZEN  and  regexp(c.extraData,'{''auctionPeriodId'':''12''}')=1
			
			System.out.println("================================================="+sb.toString());
			list = (List<AccountItem>)aDao.queryObjectList(AccountItem.class, null, sb.toString(), null, false, 0, 0);
		}
		
		return list;
	}
	@Override
	public void matchAI(AccountItem spendingAI, AccountItem shopIncomeAI,
			AccountItem adminIncomeAI) throws Exception {
		/**
		 * adminIncomeAI不一定存在
		 */
		StringBuffer amatch=new StringBuffer();
		if(null!=adminIncomeAI){
			if(null!=spendingAI && null!=shopIncomeAI){
				amatch.append("[").append(spendingAI.getMarkedNum()).append("][").append(shopIncomeAI.getMarkedNum()).append("][")
				.append(adminIncomeAI.getMarkedNum()).append("]");
				spendingAI.setAmatch(amatch.toString());
				shopIncomeAI.setAmatch(amatch.toString());
				adminIncomeAI.setAmatch(amatch.toString());
				aDao.updateObject(spendingAI);
				aDao.updateObject(shopIncomeAI);
				aDao.updateObject(adminIncomeAI);
			}else if(null==spendingAI && null!=shopIncomeAI){
				amatch.append("[").append(shopIncomeAI.getMarkedNum()).append("][")
				.append(adminIncomeAI.getMarkedNum()).append("]");
				shopIncomeAI.setAmatch(amatch.toString());
				adminIncomeAI.setAmatch(amatch.toString());
				aDao.updateObject(shopIncomeAI);
				aDao.updateObject(adminIncomeAI);
			}else if(null!=spendingAI && null==shopIncomeAI){
				amatch.append("[").append(adminIncomeAI.getMarkedNum()).append("][")
				.append(adminIncomeAI.getMarkedNum()).append("]");
				spendingAI.setAmatch(amatch.toString());
				adminIncomeAI.setAmatch(amatch.toString());
				aDao.updateObject(spendingAI);
				aDao.updateObject(adminIncomeAI);
			}
		}else{
			amatch.append("[").append(spendingAI.getMarkedNum()).append("][").append(shopIncomeAI.getMarkedNum()).append("]");
			spendingAI.setAmatch(amatch.toString());
			shopIncomeAI.setAmatch(amatch.toString());
			aDao.updateObject(spendingAI);
			aDao.updateObject(shopIncomeAI);
		}
		
		
		
		
	}
	@Override
	public Account account_findAccount(String whoClassName, int whoId) throws Exception {
		Transaction trans = aDao.getHt().getSessionFactory().getCurrentSession().beginTransaction();
		System.out.println(trans+"-account_findAccount");
		
		Account a = null;
		synchronized(getLock(whoClassName,whoId)){
			a = (Account)aDao.queryObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{whoClassName,whoId});
			a=account_computingAccountPrice(a);
		}
		return a;
	}
	@Override
	public Map<String,AccountItem> forzen2Spending(AccountItem forzenAI, Account shopIncomeA,String spendingInfo,String incomeInfo)
			throws Exception {
		Map<String,AccountItem> map=null;
		synchronized(getLock(forzenAI.getWhoclassName(),forzenAI.getWhoId())){
			synchronized(getLock(shopIncomeA.getWhoclassName(),shopIncomeA.getWhoId())){
				map = new HashMap<String,AccountItem>();
				closeAccountItem(forzenAI);
				
				//创建两个ai，一个是支出，一个是收入
				//这里的保证金，web不会要提成
				Account userAccount = (Account)aDao.queryObjectById("from Account as a where a.id=?", new Object[]{forzenAI.getAccount().getId()});
				if(null!=userAccount){
					AccountItem spendingAI=spending(userAccount, forzenAI.getPrice(), null, AccountItem.STATUS.FINISH.toString(),spendingInfo);
					AccountItem shopIncomeAI=income(shopIncomeA, forzenAI.getPrice(), null, AccountItem.STATUS.FINISH.toString(),incomeInfo);
					matchAI(spendingAI, shopIncomeAI, null);
					map.put("spendingAI", spendingAI);
					map.put("shopIncomeAI", shopIncomeAI);
				}
			}
		}
		return map;
		
	}

	/*@Override
	public Map<String, AccountItem> trade(float price, float adminTCPrice,
			Account spendingAccount, Account shopIncomeAccount,
			Account adminIncomeAccount,Map<String,String> aiInfoMap,Map<String,String> extraMap) throws Exception {
		Map<String,AccountItem> map = null;
		synchronized(getLock(spendingAccount.getWhoclassName(),spendingAccount.getWhoId())){
			synchronized(getLock(shopIncomeAccount.getWhoclassName(),shopIncomeAccount.getWhoId())){
				synchronized(getLock(adminIncomeAccount.getWhoclassName(),adminIncomeAccount.getWhoId())){
					map = new HashMap<String,AccountItem>();
					if(null!=aiInfoMap && null!=spendingAccount && null!=shopIncomeAccount && null!=adminIncomeAccount){
						if(price>0 && adminTCPrice>=0){
							AccountItem spendingAI=spending(spendingAccount, price+adminTCPrice, extraMap.get("spendingAccountItemExtra"), AccountItem.STATUS.FINISH.toString(),aiInfoMap.get("spendingAccountItemInfo"));
							AccountItem shopIncomeAI=income(shopIncomeAccount, price,  extraMap.get("shopIncomeAccountItemExtra"), AccountItem.STATUS.FINISH.toString(), aiInfoMap.get("shopIncomeAccountItemInfo"));
							AccountItem adminIncomeAI=income(adminIncomeAccount, adminTCPrice,  extraMap.get("adminIncomeAccountItemExtra"), AccountItem.STATUS.FINISH.toString(), aiInfoMap.get("adminIncomeAccountItemInfo"));
							
							
							matchAI(spendingAI, shopIncomeAI, adminIncomeAI);
							map.put("spendingAI", spendingAI);
							map.put("shopIncomeAI", shopIncomeAI);
							map.put("adminIncomeAI", adminIncomeAI);
							
						}
					}
				}
			}
		}
		return map;
	}*/
	@Override
	public Map<String, AccountItem> trade(float price, float tjrTCPrice,
			Account spendingAccount, Account tjrIncomeAccount,
			Account adminIncomeAccount,Map<String,String> aiInfoMap,Map<String,String> extraMap) throws Exception {
		Map<String,AccountItem> map = null;
		synchronized(getLock(spendingAccount.getWhoclassName(),spendingAccount.getWhoId())){
			synchronized(getLock(tjrIncomeAccount.getWhoclassName(),tjrIncomeAccount.getWhoId())){
				synchronized(getLock(adminIncomeAccount.getWhoclassName(),adminIncomeAccount.getWhoId())){
					map = new HashMap<String,AccountItem>();
					if(null!=aiInfoMap && null!=spendingAccount && null!=tjrIncomeAccount && null!=adminIncomeAccount){
						if(price>0 && tjrTCPrice>=0){
							AccountItem spendingAI=spending(spendingAccount, price+tjrTCPrice, extraMap.get("spendingAccountItemExtra"), AccountItem.STATUS.FINISH.toString(),aiInfoMap.get("spendingAccountItemInfo"));
							AccountItem tjrIncomeAI=income(tjrIncomeAccount, tjrTCPrice,  extraMap.get("tjrIncomeAccountItemExtra"), AccountItem.STATUS.FINISH.toString(), aiInfoMap.get("tjrIncomeAccountItemInfo"));
							AccountItem adminIncomeAI=income(adminIncomeAccount, price,  extraMap.get("adminIncomeAccountItemExtra"), AccountItem.STATUS.FINISH.toString(), aiInfoMap.get("adminIncomeAccountItemInfo"));
							
							
							matchAI(spendingAI, tjrIncomeAI, adminIncomeAI);
							map.put("spendingAI", spendingAI);
							map.put("tjrIncomeAI", tjrIncomeAI);
							map.put("adminIncomeAI", adminIncomeAI);
							
						}
					}
				}
			}
		}
		return map;
	}
	@Override
	public boolean account_enoughAvailablePriceInAccount(Account account,
			float oneForzen,float spendingPrice) throws Exception {
		synchronized(getLock(account)){
			float availablePrice=account.getAvailablePrice();
			availablePrice=availablePrice+oneForzen;
			if(availablePrice>=spendingPrice){
				return true;
			}
		}
		return false;
	}
	
	
	//直接提现成功的。前提是你得给我穿进来由于申请提现而冻结的这个AI
	@Override
	public AccountItem account_withDrawals(AccountItem forzenAI_TX) throws Exception {
		synchronized(getLock(forzenAI_TX.getWhoclassName(),forzenAI_TX.getWhoId())){
			AccountItem item=null;
			if(null!=forzenAI_TX){
				//Account account = account_findAccount(forzenAI_TX.getWhoclassName(), forzenAI_TX.getWhoId());
				//System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb=="+account.getFrozenPrice());//okkkk
				Account account = (Account)aDao.queryObjectById("from Account as a where a.id=?", new Object[]{forzenAI_TX.getAccount().getId()});
				//synchronized(getLock(account)){
					//if(null!=forzenAI_TX){
						//第一）关闭这个被冻结的ai
						forzenAI_TX.setStatus(AccountItem.STATUS.CLOSE.toString());
						aDao.updateObject(forzenAI_TX);
						//aDao.getHt().flush();
						
						//第二）生成提现ai
						String markedNum=new Date().getTime()+"";
						item=new AccountItem();
						item.setAccount(account);
						item.setPrice(forzenAI_TX.getPrice());
						item.setExtraData("");
						item.setInfo("提现资金");
						item.setItemType(AccountItem.ITEMTYPE.WITHDRAWALS.toString());
						item.setMarkedNum(markedNum);
						item.setStatus(AccountItem.STATUS.FINISH.toString());
						item.setWhoclassName(account.getWhoclassName());
						item.setWhoId(account.getWhoId());
						item.setRelDate(new Date());
						aDao.insertObject(item);
						//aDao.getHt().flush();
						
						
						
						//第三）修改account
						account_computingAccountPrice(account);
						
					//}
				//}
			}
		}
		return null;
	}
	@Override
	public Account account_computingAccountPrice_test(Account account) throws Exception {
		//aDao.getHt().flush();
		try{
			if(null!=account){
				int accountId = account.getId();
				//RECHARGE(充值),WITHDRAWALS(提现),INCOME(收入),SPENDING(支出),FROZEN(冻结)
				//WAIT(等待),FINISH(完结),CLOSE(关闭)
				//第一）计算冻结的金额
				//冻结成功的才算，冻结close的不算
				float forzePrice_float=0;
				Double forzePrice = (Double)aDao.queryObjectById("select sum(ai.price) from AccountItem as ai where ai.account.id=? and ai.itemType='FROZEN' and ai.status='FINISH'", new Object[]{accountId});
				/*Double forzePrice = (Double)aDao.j_queryObject("select sum(ai.price) from accountItem as ai where ai.account_id=? and ai.itemType='FROZEN' and ai.status='FINISH'", 
						new Object[]{accountId});*/
				if(null==forzePrice){
					forzePrice_float=0;
				}else{
					forzePrice_float=(float) forzePrice.doubleValue();
				}
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA=="+forzePrice_float);
				String whoClassName = account.getWhoclassName();
				if(User.class.getName().equals(whoClassName)){
					System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBB=="+forzePrice_float);
					float avalable_xcPrice = AccountUtilHHR.getAvailable_xcPrice(uICacheManager, account);
					System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC=="+avalable_xcPrice);
					float forzen_xcPrice=account.getXcPrice()-avalable_xcPrice;
					System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD=="+forzen_xcPrice);
					forzePrice_float=forzePrice_float+forzen_xcPrice;
					System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEE=="+forzen_xcPrice);
				}else{
				}
				account.setFrozenPrice(forzePrice_float);
				System.out.println("ccccccccccccccccccccccccccccccccccc=="+account.getFrozenPrice());
				
				
				//第二）计算总金额
				/**
				 * 1、所有的充值成功记录
				 * 2、所有的收入成功记录
				 * 3、所有提现成功记录
				 * 4、所有支出成功记录
				 * 1+2=入账
				 * 3+4=出账
				 * 入账-出账=总额
				 */
				float ruzhang_float=0;
				Double ruzhang=(Double)aDao.j_queryObject(
				"select sum(ai.price) from accountItem as ai where ai.account_id=? and (ai.itemType='RECHARGE' or ai.itemType='INCOME') and ai.status='FINISH'", 
				new Object[]{accountId});
				if(null==ruzhang){
					ruzhang_float=0;
				}else{
					ruzhang_float=(float) ruzhang.doubleValue();
				}
				
				float chuzhang_float=0;
				Double chuzhang=(Double)aDao.j_queryObject(
				"select sum(ai.price) from accountItem as ai where ai.account_id=? and (ai.itemType='WITHDRAWALS' or ai.itemType='SPENDING') and ai.status='FINISH'", 
				new Object[]{accountId});
				if(null==chuzhang){
					chuzhang_float=0;
				}else{
					chuzhang_float=(float) chuzhang.doubleValue();
				}
				
				float totalPrice = ruzhang_float-chuzhang_float;
				account.setTotalPrice(totalPrice);
				
				//第三）计算可用金额
				float avaiPrice = totalPrice-forzePrice_float;
				
				account.setAvailablePrice(avaiPrice);
				
				aDao.updateObject(account);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return account;
	}
	@Override
	public Account account_computingAccountPrice(Account account) throws Exception {
		//aDao.getHt().flush();
		Transaction trans = aDao.getHt().getSessionFactory().getCurrentSession().beginTransaction();
		System.out.println(trans+"-account_computingAccountPrice");

		if(null!=account){
			int accountId = account.getId();
			//RECHARGE(充值),WITHDRAWALS(提现),INCOME(收入),SPENDING(支出),FROZEN(冻结)
			//WAIT(等待),FINISH(完结),CLOSE(关闭)
			//第一）计算冻结的金额
			//冻结成功的才算，冻结close的不算
			float forzePrice_float=0;
			Double forzePrice = (Double)aDao.queryObjectById("select sum(ai.price) from AccountItem as ai where ai.account.id=? and ai.itemType='FROZEN' and ai.status='FINISH'", new Object[]{accountId});
			if(null==forzePrice){
				forzePrice_float=0;
			}else{
				forzePrice_float=(float) forzePrice.doubleValue();
			}
			
			String whoClassName = account.getWhoclassName();
			if(User.class.getName().equals(whoClassName)){
				float avalable_xcPrice = AccountUtilHHR.getAvailable_xcPrice(uICacheManager, account);
				float forzen_xcPrice=account.getXcPrice()-avalable_xcPrice;
				forzePrice_float=forzePrice_float+forzen_xcPrice;
			}else{
			}
			account.setFrozenPrice(forzePrice_float);
			
			
			
			//第二）计算总金额
			/**
			 * 1、所有的充值成功记录
			 * 2、所有的收入成功记录
			 * 3、所有提现成功记录
			 * 4、所有支出成功记录
			 * 1+2=入账
			 * 3+4=出账
			 * 入账-出账=总额
			 */
			float ruzhang_float=0;
			Double ruzhang=(Double)aDao.queryObjectById(
			"select sum(ai.price) from AccountItem as ai where ai.account.id=? and (ai.itemType='RECHARGE' or ai.itemType='INCOME') and ai.status='FINISH'", 
			new Object[]{accountId});
			if(null==ruzhang){
				ruzhang_float=0;
			}else{
				ruzhang_float=(float) ruzhang.doubleValue();
			}
			
			float chuzhang_float=0;
			Double chuzhang=(Double)aDao.queryObjectById(
			"select sum(ai.price) from AccountItem as ai where ai.account.id=? and (ai.itemType='WITHDRAWALS' or ai.itemType='SPENDING') and ai.status='FINISH'", 
			new Object[]{accountId});
			if(null==chuzhang){
				chuzhang_float=0;
			}else{
				chuzhang_float=(float) chuzhang.doubleValue();
			}
			
			float totalPrice = ruzhang_float-chuzhang_float;
			account.setTotalPrice(totalPrice);
			
			//第三）计算可用金额
			float avaiPrice = totalPrice-forzePrice_float;
			
			account.setAvailablePrice(avaiPrice);
			
			aDao.updateObject(account);
			
		}
		return account;
	}
	@Override
	public AccountItem findAccountItemById(int accountItemId) throws Exception {
		AccountItem ai = (AccountItem)aDao.queryObjectById("from AccountItem as ai where ai.id=?", new Object[]{accountItemId});
		return ai;
	}
	@Override
	public AccountItem withDrawals(String whoclassName,int whoId,Account account, float price) throws Exception {
		AccountItem item = null;
		synchronized(getLock(whoclassName,whoId)){
			synchronized(getLock(account)){
				//第二）生成提现ai
				String markedNum=new Date().getTime()+"";
				item=new AccountItem();
				item.setAccount(account);
				item.setPrice(price);
				item.setExtraData("");
				item.setInfo("提现资金");
				item.setItemType(AccountItem.ITEMTYPE.WITHDRAWALS.toString());
				item.setMarkedNum(markedNum);
				item.setStatus(AccountItem.STATUS.FINISH.toString());
				item.setWhoclassName(account.getWhoclassName());
				item.setWhoId(account.getWhoId());
				item.setRelDate(new Date());
				aDao.insertObject(item);
				
				//第三）修改account
				/**
				 * 1、冻结资金-forzen price
				 * 2、总额- price
				 */
				
				account_computingAccountPrice(account);
				
			}
		}
		return item;
	}
	@Override
	public AccountItem income_xsxc(Account account, float price,
			String extraData, String status, String info,String priceType) throws Exception {
		if(price==0){
			return null;
		}
		AccountItem item=null;
		if(null!=account){
			synchronized(getLock(account)){
				String markedNum=new Date().getTime()+"";
				item=new AccountItem();
				item.setAccount(account);
				item.setPrice(price);
				item.setExtraData(extraData);
				item.setInfo(info);
				item.setPriceType(AccountItem.PRICETYPE.XC.toString());
				item.setItemType(AccountItem.ITEMTYPE.INCOME.toString());
				item.setMarkedNum(markedNum);
				item.setWhoclassName(account.getWhoclassName());
				item.setWhoId(account.getWhoId());
				item.setRelDate(new Date());
				if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
					item.setStatus(AccountItem.STATUS.FINISH.toString());
					if(EnumUtil.equalsE(AccountItem.PRICETYPE.XC, priceType)){
						account.setXcPrice(account.getXcPrice()+price);
						aDao.updateObject(account);
					}else if(EnumUtil.equalsE(AccountItem.PRICETYPE.XS, priceType)){
						account.setXsPrice(account.getXsPrice()+price);
						aDao.updateObject(account);
					}
				}else if(EnumUtil.equalsE(AccountItem.STATUS.WAIT, status)){
					item.setStatus(AccountItem.STATUS.WAIT.toString());
					//等待收入
				}
				aDao.insertObject(item);
				if(item.getId()>0){
					if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
						account_computingAccountPrice(account);
					}
				}
				return item;
			}
		}
		return null;
	}
	@Override
	public Map<String, AccountItem> trade_xsxc(float price, float tjrTCPrice,
			Account spendingAccount, Account tjrIncomeAccount,
			Account adminIncomeAccount, Map<String, String> aiInfoMap,
			Map<String, String> extraMap) throws Exception {
		Map<String,AccountItem> map = null;
		synchronized(getLock(spendingAccount.getWhoclassName(),spendingAccount.getWhoId())){
			synchronized(getLock(tjrIncomeAccount.getWhoclassName(),tjrIncomeAccount.getWhoId())){
				synchronized(getLock(adminIncomeAccount.getWhoclassName(),adminIncomeAccount.getWhoId())){
					map = new HashMap<String,AccountItem>();
					if(null!=aiInfoMap && null!=spendingAccount && null!=tjrIncomeAccount && null!=adminIncomeAccount){
						if(price>0 && tjrTCPrice>=0){
							AccountItem spendingAI=spending_xsxc(spendingAccount, price+tjrTCPrice, extraMap.get("spendingAccountItemExtra"), AccountItem.STATUS.FINISH.toString(),aiInfoMap.get("spendingAccountItemInfo"));
							AccountItem tjrIncomeAI=income_xsxc(tjrIncomeAccount, tjrTCPrice,  extraMap.get("tjrIncomeAccountItemExtra"), AccountItem.STATUS.FINISH.toString(), aiInfoMap.get("tjrIncomeAccountItemInfo"),AccountItem.PRICETYPE.XS.toString());
							AccountItem adminIncomeAI=income(adminIncomeAccount, price,  extraMap.get("adminIncomeAccountItemExtra"), AccountItem.STATUS.FINISH.toString(), aiInfoMap.get("adminIncomeAccountItemInfo"));
							
							
							matchAI(spendingAI, tjrIncomeAI, adminIncomeAI);
							map.put("spendingAI", spendingAI);
							map.put("tjrIncomeAI", tjrIncomeAI);
							map.put("adminIncomeAI", adminIncomeAI);
							
						}
					}
				}
			}
		}
		return map;
	}
	@Override
	public AccountItem spending_xsxc(Account account, float price,
			String extraData, String status, String info) throws Exception {
		/**
		 * 先花销售佣金，再花宣传佣金，再花充值的费用
		 */
		if(price==0){
			return null;
		}
		AccountItem item=null;
		if(null!=account){
			synchronized(getLock(account)){
				String markedNum=new Date().getTime()+"";
				item=new AccountItem();
				item.setAccount(account);
				item.setPrice(price);
				item.setExtraData(extraData);
				item.setInfo(info);
				item.setItemType(AccountItem.ITEMTYPE.SPENDING.toString());
				item.setMarkedNum(markedNum);
				item.setWhoclassName(account.getWhoclassName());
				item.setWhoId(account.getWhoId());
				item.setRelDate(new Date());
				
				if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
					item.setStatus(AccountItem.STATUS.FINISH.toString());
				}else if(EnumUtil.equalsE(AccountItem.STATUS.WAIT, status)){
					item.setStatus(AccountItem.STATUS.WAIT.toString());
					//等待支出
				}
				aDao.insertObject(item);
				if(item.getId()>0){
					if(EnumUtil.equalsE(AccountItem.STATUS.FINISH, status)){
						
						
						
						float xcPrice = account.getXcPrice();
						float avaliable_xcPrice=AccountUtilHHR.getAvailable_xcPrice(uICacheManager, account);
						float xsPrice = account.getXsPrice();
						
						
						if(price<=xsPrice){
							//销售佣金足够了
							account.setXsPrice(xsPrice-price);
						}else if(price>xsPrice && price<=(xsPrice+avaliable_xcPrice)){
							//如果销售佣金不够，但是销售佣金+可用宣传佣金足够
							account.setXsPrice(0);
							float xcPrice_used = price-xsPrice;
							float newXCprice = xcPrice-xcPrice_used;
							account.setXcPrice(newXCprice);
						}else if(price>(xsPrice+avaliable_xcPrice)){
							//如果销售资金+宣传佣金，加起来都不够 这次支付的，那都
							account.setXsPrice(0);
							account.setXcPrice(xcPrice-avaliable_xcPrice);
						}
						aDao.updateObject(account);
						
						account_computingAccountPrice(account);
					}
				}
				return  item;
			}
		}
		return null;
	}
	
	

}
