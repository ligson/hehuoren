package myFrameU.account.util;

import java.math.BigInteger;
import java.util.HashMap;

import myFrame.account.AccountConstant;
import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.account.init.InitConfig;
import myFrameU.admin.entity.Admin;
import myFrameU.biz.AbstractBizI;
import myFrameU.exception.exception.MyStrException;
import myFrameU.shop.entity.Shop;
import myFrameU.user.entity.User;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;


public class AccountUtil {
	//找到唯一的一个，由于申请提现而冻结的ai，状态是finish的。
	public static AccountItem findUniqueAI_forzen_forTX(AbstractBizI aBiz,int roleId,int accountId) throws Exception{
		//String extraData="{'accountId':'"+accountId+"',"+"'roleId':'"+roleId+"','unique':'applyWITHDRAWALS'}";
		
		HashMap<String,String> argsMap = new HashMap<String,String>();
		argsMap.put("accountId", accountId+"");
		argsMap.put("roleId", roleId+"");
		//String extraData = AccountConstant.getExtraData(AccountConstant.ACCOUNTITEM_EXTRA_KEY.WITHDRAWALS_FORZEN.toString(), argsMap);
		//extraData=extraData.replaceAll("'", "''");//不需要转义，因为外面没有单引号
		
		
		AccountItem forzenAI_TX=(AccountItem)aBiz.
				findObjectById(
						"from AccountItem as ai where ai.account.id=? and ai.itemType=? and ai.status=?",
						new Object[]{accountId,AccountItem.ITEMTYPE.FROZEN.toString(),AccountItem.STATUS.FINISH.toString()
								});
		return forzenAI_TX;
	}
	
	
	/**
	 * 找出同一时间内，唯一一个，由于升级而冻结的ai，（因为一旦审核通过|审核不通过，都会close掉这个ai）
	 */
	public static AccountItem findUniqueAI_forzen_forUpdateLevel(AbstractBizI aBiz,int roleId,int accountId) throws Exception{
			String extraData = AccountConstant.getExtraData(AccountConstant.ACCOUNTITEM_EXTRA_KEY.UPDATEVIPSHOP_FORZEN.toString(), null);
			AccountItem forzenAI_UpdateLevel=(AccountItem)aBiz.
					findObjectById(
							"from AccountItem as ai where ai.account.id=? and ai.itemType=? and ai.status=? and ai.extraData=?",
							new Object[]{accountId,AccountItem.ITEMTYPE.FROZEN.toString(),AccountItem.STATUS.FINISH.toString()
									,extraData});
			return forzenAI_UpdateLevel;
		}

	
	
	/**
	 * 找出同一时间内，唯一一个，由于升级而冻结的ai，（因为一旦审核通过|审核不通过，都会close掉这个ai）
	 */
	public static AccountItem findUniqueAI_forzen_forUpdateUserLevel(AbstractBizI aBiz,int roleId,int accountId) throws Exception{
			String extraData = AccountConstant.getExtraData(AccountConstant.ACCOUNTITEM_EXTRA_KEY.UPDATEVIPUSER_FORZEN.toString(), null);
			AccountItem forzenAI_UpdateLevel=(AccountItem)aBiz.
					findObjectById(
							"from AccountItem as ai where ai.account.id=? and ai.itemType=? and ai.status=? and ai.extraData=?",
							new Object[]{accountId,AccountItem.ITEMTYPE.FROZEN.toString(),AccountItem.STATUS.FINISH.toString()
									,extraData});
			return forzenAI_UpdateLevel;
		}
	
	
	//找出一个月提现的次数
	public static int getCount_tx_month(AbstractBizI aBiz,int accountId) throws Exception{
		//date_format(submittime,’%Y-%m’)=date_format(now(),’%Y-%m’)
		String sql = "select count(id) from "+EntityTableUtil.tableNameC(AccountItem.class)+"  as ai where ai.account_id=? and ai.itemType=? and ai.status=? and date_format(ai.relDate,'%Y-%m')=date_format(now(),'%Y-%m')";
		BigInteger count = (BigInteger)aBiz.j_queryObject(sql, 
				new Object[]{accountId,AccountItem.ITEMTYPE.WITHDRAWALS.toString(),AccountItem.STATUS.FINISH.toString()});
		if(null==count){
			return 0;
		}else {
			if(count.intValue()==0){
				return 0;
			}else{
				return count.intValue();
			}
		}
	}
	
	
	
	
	
	public static String getLock(String whoClassName,int whoId){
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
	
	
	
	//判断财务密码是否正确
	public static void verPasswordOk(AbstractBizI aBiz,String password,String whoClassName,int whoId) throws Exception{
		boolean passwordStatus = myFrameU.account.init.InitConfig.passwordStatus;
		if(passwordStatus){
			String withdrawalsPwd = (String)aBiz.j_queryObject("select a.withdrawalsPwd from account as a where a.whoclassName=? and a.whoId=?", 
					new Object[]{whoClassName,whoId});
			if(null!=withdrawalsPwd && !withdrawalsPwd.equals("")){
				if(null!=password && !password.equals("")){
					if(password.equals(withdrawalsPwd)){
					}else{
						throw new MyStrException("抱歉您输入的财务密码不正确！");
					}
				}else{
					throw new MyStrException("抱歉请您输入财务密码！");
				}
			}else{
				throw new MyStrException("抱歉您还没有设置财务密码！");
			}	
		}else{
			
		}
		
	}
	
	//判断财务密码是否正确
		public static boolean verPasswordOk_boolean(AbstractBizI aBiz,String password,String whoClassName,int whoId) throws Exception{
			boolean passwordStatus = myFrameU.account.init.InitConfig.passwordStatus;
			if(passwordStatus){
				String withdrawalsPwd = (String)aBiz.j_queryObject("select a.withdrawalsPwd from account as a where a.whoclassName=? and a.whoId=?", 
						new Object[]{whoClassName,whoId});
				if(null!=withdrawalsPwd && !withdrawalsPwd.equals("")){
					if(null!=password && !password.equals("")){
						if(password.equals(withdrawalsPwd)){
							return true;
						}else{
							return false;
						}
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return true;
			}
			
		}
	
	
	
	public static HashMap<String,String> getMarkedNumFromAmatch(String amatch){
		HashMap<String,String> map = new HashMap<String,String>();
		if(null!=amatch && !amatch.equals("")){
			if(amatch.length()>20){
				amatch=amatch.substring(1, amatch.length()-1);
				String[] mnArray=amatch.split("]\\[");
				if(null!=mnArray){
					int len = mnArray.length;
					if(len==3){
						map.put("userSpendingAIMarkerdNum", mnArray[0]);
						map.put("shopIncomeAIMarkerdNum", mnArray[1]);
						map.put("adminIncomeAIMarkerdNum", mnArray[2]);
					}else if(len==2){
						map.put("userSpendingAIMarkerdNum", mnArray[0]);
						map.put("shopIncomeAIMarkerdNum", mnArray[1]);
					}
				}
			}
		}
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
