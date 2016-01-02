package myFrameU.apply.biz;

import java.math.BigInteger;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import myFrame.cache.CacheKey;
import myFrame.quartz.MyJobManager;
import myFrameU.apply.entity.Apply;
import myFrameU.apply.util.ApplyConstant;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;

import org.springframework.stereotype.Service;
@Service("userHHRApplyBiz")
public class UserHHRApplyImpl extends AbstractBizImpl implements ApplyBizI {

	@Override
	public Apply applyOK(Apply apply, UICacheManager uICacheManager)
			throws Exception {
		System.out.println("管理员审批通过 .......................");
		/**
		 * 如果审核通过：
		 * 		1、从global拿出试用期多少天，试用期期间需要积累多少粉丝
		 * 		2、动态监听30天之后的job
		 */
		//User user = (User)req.getSession().getAttribute("myUser");
		
		if(null!=apply){
			String cn=apply.getSponsorClassName();
			int spId = apply.getSponsorId();
			User user = (User)aDao.queryObjectById("from User as u where u.id=?", new Object[]{spId});
			if(null!=user){
				String applyTypeKey = apply.getApplyTypeKey();
				if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
					Global g14=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 14);
					Global g15=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 15);
					Global g16=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 16);
					if(null!=g14 && null!=g15 && null!=g16){
						String g15Value =g15.getMyValue();
						String g16Value=g16.getMyValue();
						if(null!=g15Value && !g15Value.equals("") && null!=g16Value && !g16Value.equals("")){
							int syqTimeLongDay=new Integer(g15Value).intValue();
							int syqFSCount=new Integer(g16Value).intValue();
							user.setUserLevelId(2);
							user.setBeProbationDate(new Date());
							user.setFensiCount(0);
							aDao.updateObject(user);
							MyJobManager.USER_SYQ(user);
							
							aDao.updateObject(apply);
						}
					}
				}
			}
		}
		return apply;
	}

	@Override
	public Apply applyERROR(Apply apply, UICacheManager uICacheManager)
			throws Exception {
		aDao.updateObject(apply);
		return null;
	}

	@Override
	public void findById(Apply apply, HttpServletRequest req,
			UICacheManager uICacheManager) throws Exception {

	}

	@Override
	public Apply createApply(HttpServletRequest req,
			UICacheManager uICacheManager) throws Exception {
		Apply ap = null;
		/**
		 * 一、判断是否可以申请成为合伙人
		 * 1、该用户是否已经是合伙人了
		 * 2、该用户是否已经提出了成为合伙人申请了
		 * 3、绿色通道是否开了
		 *		A、如果开通了，则不需要用户购买一次产品即可提出申请
		 *		B、如果没有开通，则需要判断该用户是否已经购买过产品
		 *二、如果一旦申请成功 = 
		 *	在这里不做处理，需要在管理员审核通过的时候做
		 */
		User user=(User)req.getSession().getAttribute("myUser");
		if(null!=user){
			int levelId=user.getUserLevelId();
			if(levelId==1){
				//说明是普通用户，可以申请
				Integer lastApplyId=(Integer)aDao.j_queryObject
						("select max(a.id) from apply_apply as a where a.sponsorClassName=? and a.sponsorId=? and a.applyTypeKey=?", 
						new Object[]{User.class.getName(),user.getId(),ApplyConstant.APPLYTYPEKEY.UserLevelHHR.toString()});
				Apply oldApply = (Apply)aDao.queryObjectById("from Apply as a where a.id=?", 
						new Object[]{lastApplyId});
				boolean oldApplyOk=false;
				if(null==oldApply){
					//没有做出过申请
					oldApplyOk=true;
				}else{
					String speed = oldApply.getSpeed();
					if(EnumUtil.equalsE(Apply.SPEED.WAIT, speed)||EnumUtil.equalsE(Apply.SPEED.ING, speed)){
						throw new MyStrException("抱歉，您已经做出了合伙人申请，请等待审核");
					}else if(EnumUtil.equalsE(Apply.SPEED.FINISH, speed)){
						String result = oldApply.getResult();
						if(EnumUtil.equalsE(Apply.RESULT.APPLYOK, result)){
							throw new MyStrException("抱歉，您的合伙人申请已经审核通过，不能重复申请");
						}else if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, result)){
							oldApplyOk=true;
						}
					}
				}
				
				if(oldApplyOk){
					boolean lvsetongdao=false;
					Global g14=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 14);
					Global g15=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 15);
					Global g16=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 16);
					if(null!=g14 && null!=g15 && null!=g16){
						String g14Value = g14.getMyValue();
						if(null!=g14Value){
							if(g14Value.equals("1")){
								//说明现在开通了绿色通道，不需要买东西就可以直接申请成为合伙人
								lvsetongdao=true;
							}else if( g14Value.equals("0")){
								//说明现在没有开通绿色通道，必须先购买一次
								BigInteger maxMyOrderId = (BigInteger)aDao.j_queryObject("select max(id) from order_order as o where o.userId=? and o.payDate is not null",
										new Object[]{user.getId()});
								if(null==maxMyOrderId){
									
								}else{
									int mmoiint=maxMyOrderId.intValue();
									if(mmoiint>0){
										lvsetongdao=true;
									}else{
									}
								}
							}
						}
					}
					if(lvsetongdao){
						ap=new Apply();
						ap.setApplyContent(user.getNicheng()+"要申请成为合伙人");
						ap.setApplyTitle(user.getNicheng()+"要申请成为合伙人");
						ap.setApplyTypeKey(ApplyConstant.APPLYTYPEKEY.UserLevelHHR.toString());
						ap.setExtraData(null);
						ap.setRelDate(new Date());
						ap.setSpeed(Apply.SPEED.WAIT.toString());
						ap.setSponsorClassName(User.class.getName());
						ap.setSponsorId(user.getId());
						ap.setSponsorName(user.getNicheng());
						aDao.insertObject(ap);
						return ap;
					}else{
						throw new MyStrException("抱歉，您需要先购买一次产品之后，方能申请成为合伙人！");
					}
				}
			}else if(levelId==2){
				//说明在试用期的合伙人，不可做出申请
				throw new MyStrException("抱歉，您现在是试用期的合伙人，不能再作出申请");
			}else if(levelId==3){
				//说明已经是正式的合伙人了
				throw new MyStrException("抱歉，您已经是正式的合伙人了，不能再次作出申请");
			}
		}
		return ap;
	}

}
