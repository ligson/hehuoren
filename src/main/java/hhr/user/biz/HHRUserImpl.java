package hhr.user.biz;

import hhr.order.entity.Order;
import hhr.user.util.UserUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import myFrame.cache.CacheKey;
import myFrameU.account.biz.AccountBizI;
import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.admin.entity.Admin;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.global.entity.Global;
import myFrameU.user.entity.User;

import org.springframework.stereotype.Service;
@Service("hhrUserBiz")
public class HHRUserImpl extends AbstractBizImpl implements HHRUserI {

	@Override
	public void beFensi(User user, User he, UICacheManager uICacheManager,
			AccountBizI accountBiz) throws Exception {

		/**
		 * 成为他的粉丝，就是我有了推荐人了，我一旦有了推荐人，
		 * 		客户的新需求是，一旦我指定了推荐人，那么我就是成为正式的合伙人了
		 */
		int userOldLevelId=user.getUserLevelId();
		if(userOldLevelId!=3){
			UserUtil.beZhengshiHHR_removeJOB(user, this);
			
			user.setUserLevelId(3);
			user.setBeDate(new Date());
		}
		user.setTuijianRenId(he.getId());
		user.setTuijianRenNicheng(he.getNicheng());
		aDao.updateObject(user);
		
		
		
		
		he.setFensiCount(he.getFensiCount()+1);
		aDao.updateObject(he);
		
		
		//找到这个人的粉丝数量，如果这次是第31个粉丝，就不给他宣传佣金了，变态的SB客户。
		int fsCount = UserUtil.getFSCount(he.getId(), this);
		if(fsCount<=30){
			/**
			 * 一旦成为他的粉丝，平台要给这个合伙人宣传佣金。
			 * 		admin支出
			 * 		推荐人收款
			 * 			宣传佣金
			 * 				1、根据账户的销售佣金来将宣传佣金拆分成可用资金和冻结资金。	
			 */
			Account tjrAccount=accountBiz.account_findAccount(User.class.getName(), he.getId());
			Account accountAdmin = accountBiz.account_findAccount(Admin.class.getName(), 1);
			if(null!=accountAdmin && null!=tjrAccount){
				Global g19=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 19);
				if(null!=g19){
					String g19value = g19.getMyValue();
					if(null!=g19value && !g19value.equals("")){
						float g19ValueFloat = new Float(g19value).floatValue();
						if(g19ValueFloat>0){
							AccountItem ai_adminSpending = accountBiz.spending(accountAdmin, g19ValueFloat, null, AccountItem.STATUS.FINISH.toString(), "用户成为["+he.getName()+"]的粉丝，平台为支付给该推荐人宣传佣金");
							//先将该宣传资金放入到该账户的xcPrice中去。
							AccountItem ai_tjrXC=accountBiz.income_xsxc(tjrAccount, g19ValueFloat, null,  AccountItem.STATUS.FINISH.toString(), "你增加1位粉丝，平台支付给你宣传佣金。", AccountItem.PRICETYPE.XC.toString());
							if(null!=ai_adminSpending && null!=ai_tjrXC){
								accountBiz.matchAI(ai_adminSpending, ai_tjrXC, null);
							}
						}
					}
				}
			}
		}
		
		
	}

	@Override
	public HashMap<String, Object> getUser_fs_and_fsOrders(User user)
			throws Exception {
		int levelId=user.getUserLevelId();
		if(levelId==2 ||  levelId==3){
			//普通人，肯定没有粉丝
			//错误，如果试用期过了不是了的呢
		}else{
			
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		List<User> fensiList = (List<User>)aDao.queryObjectList(User.class, new Object[]{user.getId()}, 
				"from User as u where u.tuijianRenId=? and u.status='OK'", null, false, 0, 0);
		map.put("fensiList",fensiList);
		
		if(null!=fensiList){
			User fensi = null;
			int size = fensiList.size();
			StringBuffer fensiIdSB = new StringBuffer("");
			for(int i=0;i<size;i++){
				fensi = fensiList.get(i);
				if(i==(size-1)){
					fensiIdSB.append(fensi.getId());
				}else{
					fensiIdSB.append(fensi.getId()).append(",");
				}
			}
			String fensiIds = fensiIdSB.toString();
			if(null!=fensiIds && !fensiIds.equals("")){
				List<Order> orderList = (List<Order>)aDao.queryObjectList(
						Order.class, 
						null, 
						"from Order as o where o.userId in("+fensiIds+")", 
						null,
						false, 0, 0);
				
				map.put("fensiOrderList",orderList);
				
			}
		}
		return map;
	}

}
