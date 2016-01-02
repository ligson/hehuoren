package myFrame.user;

import hhr.user.util.UserUtil;

import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import myFrame.cache.CacheKey;
import myFrameU.account.biz.AccountBizI;
import myFrameU.account.entity.Account;
import myFrameU.apply.entity.Apply;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.global.entity.Global;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.date.DateUtils;
import myFrameU.weixin.base.entity.WXUser;

import org.springframework.context.ApplicationContext;


public class AfterUser {
	public void afterRegist(User user,HttpServletRequest req,UICacheManager cache,AbstractBizI aBiz) throws Exception{
		System.out.println("用户注册成功了，，我要做一些事情");
		/**
		 * 如注册账户
		 * 送积分
		 */
		
		
		
		
		
		
		
		
		
		
		
	}
	public void afterLogin(User user,HttpServletRequest req,UICacheManager cache,AbstractBizI aBiz) throws Exception{
		System.out.println("用户登录成功了，，我要做一些事情");
		ApplicationContext applicationContext = (ApplicationContext) req.getSession().getServletContext().getAttribute("applicationContext");
		AccountBizI accountBiz=(AccountBizI)applicationContext.getBean("accountBiz");
		if(null!=user){
			int userId = user.getId();
			WXUser wxUser=(WXUser)req.getSession().getAttribute("myUserWX");
			if(null!=wxUser && null!=user){
				String wxId = user.getWxId();
				if(null==wxId || wxId.equals("")){
					user.setWxId(wxUser.getOpenid());
					aBiz.modifyObject(user);
					req.getSession().setAttribute("myUser", user);
				}
			}
			
			user = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{userId});
			req.getSession().setAttribute("myUser", user);
			Account account = accountBiz.account_findAccount(User.class.getName(), userId);
			req.setAttribute("account", account);
			
			/**
			 * 待取货订单数
			 * 待付款订单数
			 * 粉丝数量
			 * 可用余额
			 */
			BigInteger orderCount_waitpay = (BigInteger)aBiz.j_queryObject("select count(id) from order_order where userId=? and status='WAITPAY'", new Object[]{userId});
			req.setAttribute("orderCount_waitpay", orderCount_waitpay);
			BigInteger orderCount_waitpick = (BigInteger)aBiz.j_queryObject("select count(id) from order_order where userId=? and (status='PAYED' or status='PAYING')", new Object[]{userId});
			req.setAttribute("orderCount_waitpick", orderCount_waitpick);
			
			
			int fensiCount = UserUtil.getFSCount(userId, aBiz);
			req.setAttribute("fensiCount", fensiCount);
			
		
			/**
			 * 如果是试用期的
			 * 		计算出还有多少天，还有多少个粉丝
			 */
			int levelId=user.getUserLevelId();
			if(levelId==1){
				//找到这个人最后一次的申请记录，看看是什么情况，是没有申请过，还是申请不通过
				Apply apply = (Apply)aBiz.findObjectById("from Apply as a where a.sponsorClassName=? and a.sponsorId=? and a.applyTypeKey=? order by a.id", 
						new Object[]{User.class.getName(),userId,"UserLevelHHR"});
				req.setAttribute("apply", apply);
			}else if(levelId==2){
				Global g15=CacheKey.CKGolbal.ALLMAP.getObjectTree(cache, 15);
				Global g16=CacheKey.CKGolbal.ALLMAP.getObjectTree(cache, 16);
				if(null!=g15 && null!=g16){
					String g15Value=g15.getMyValue();
					String g16Value = g16.getMyValue();
					if(null!=g15Value && !g15Value.equals("") && null!=g16Value && !g16Value.equals("")){
						int g15ValueI = new Integer(g15Value);
						int g16ValueI = new Integer(g16Value);
						Date now = new Date();
						long yiguo_day = DateUtils.getIntevalDays(user.getBeProbationDate(), now);
						int yiguo_dayI=(int)yiguo_day;
						int shengyu_day=0;
						if(g15ValueI>=yiguo_dayI){
							shengyu_day=g15ValueI-yiguo_dayI;
						}
						int shengyu_fensi=0;
						if(g16ValueI>=fensiCount){
							shengyu_fensi=g16ValueI-fensiCount;
						}else{}
						req.setAttribute("shengyu_day", shengyu_day);
						req.setAttribute("shengyu_fensi", shengyu_fensi);
					}
				}
			}
			
			
		}
		
		
		
		
		
	}
}
