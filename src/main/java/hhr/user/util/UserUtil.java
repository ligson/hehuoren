package hhr.user.util;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import myFrame.quartz.MyJobManager;
import myFrameU.apply.entity.Apply;
import myFrameU.apply.util.ApplyConstant;
import myFrameU.biz.AbstractBizI;
import myFrameU.biz.util.MyActionUtil;
import myFrameU.pager.pager.Pager;
import myFrameU.quartz.util.ScheduleJob;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;

public class UserUtil {
	/**
	 * 找出一个user的粉丝数量
	 */
	public static int getFSCount(int userId,AbstractBizI aBiz) throws Exception{
		String sql="select count(u.id) from user_user as u where u.tuijianRenId=? and u.status='OK'";
		BigInteger count = (BigInteger)aBiz.j_queryObject(sql, new Object[]{userId});
		if(null==count){
			return 0;
		}else{
			return count.intValue();
		}
	}
	
	/**
	 * 找出一个user的所有的粉丝来
	 */
	public static List<User> getFSs(int userId,AbstractBizI aBiz,HttpServletRequest req) throws Exception{
		Pager pager = new MyActionUtil(req, "").getPager(req, aBiz.getaDao(),
				"select count(id) from user_user where tuijianRenId=? and status='OK'", new Object[]{userId});
		List<User> userList = (List<User>)aBiz.findObjectList(User.class, new Object[]{userId}, 
				"from User as u where u.tuijianRenId=? and u.status='OK'", null, true, pager.getStartRow(), pager.getPageSize());
		return userList;
	}
	
	
	
	
	/**
	 * 查找我的推荐人
	 */
	public static User getMyTJR(User my,AbstractBizI aBiz) throws Exception{
		User tjr = null;
		if(null!=my){
			int tjrId = my.getTuijianRenId();
			if(tjrId!=0){
				tjr = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{tjrId});
			}
		}
		return tjr;
	}
	
	
	
	
	
	public static void beZhengshiHHR_removeJOB(User user,AbstractBizI aBiz) throws Exception{

		/**
		 * 成为正式合伙人的时候，需要判断：
		 * 		1、如果有申请成为合伙人的apply
		 * 			1、等待审核
		 * 			2、审核通过为试用期合伙人。
		 */
		
		boolean needRemoveJob = false;
		int userLevelId=user.getUserLevelId();
		if(userLevelId==1){
			//普通人，这时候去查询最后一个apply，是不是等待审核
			Apply apply = (Apply)aBiz.findObjectById
					("from Apply as a where a.sponsorId=? and a.sponsorClassName=? and a.applyTypeKey=? order by a.id desc", 
							new Object[]{user.getId(),User.class.getName(),ApplyConstant.APPLYTYPEKEY.UserLevelHHR.toString()});
			if(null!=apply){
				String speed = apply.getSpeed();
				if(null!=speed && !speed.equals("")){
					if(EnumUtil.equalsE(Apply.SPEED.WAIT, speed) || EnumUtil.equalsE(Apply.SPEED.ING, speed)){
						//等待审核
						apply.setSpeed(Apply.SPEED.FINISH.toString());
						apply.setDealDate(new Date());
						apply.setResult(Apply.RESULT.APPLYOK.toString());
						aBiz.modifyObject(apply);
						needRemoveJob=true;
					}
				}
			}
		}else if(userLevelId==2){
			//试用期合伙人
			needRemoveJob=true;
		}
		if(needRemoveJob){
			
		}
		
		ScheduleJob job = MyJobManager.getScheduleJob("USER_", "SYQ_"+user.getId());
		if(null!=job){
			MyJobManager.resumeJob(job);
			MyJobManager.removeFromMap(job);	
		}
	}
	
	
	//======================================================
	
	public static boolean canUserHHRPrice(User user) throws Exception{
		if(null!=user){
			int userLevelId=user.getUserLevelId();
			if(userLevelId==1){
				int tjrId=user.getTuijianRenId();
				if(tjrId==0){
					return false;
				}else{
					return true;
				}
			}else{
				//2-3都是合伙人
				return true;
			}
		}
		return false;
	}
	
}
