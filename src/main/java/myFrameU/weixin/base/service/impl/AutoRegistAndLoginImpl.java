package myFrameU.weixin.base.service.impl;

import java.util.Date;
import java.util.Map;

import myFrame.cache.CacheKey;
import myFrameU.address.entity.Address;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.json.JSONUtils;
import myFrameU.weixin.base.entity.IfNosubscribe_subscribeEvent;
import myFrameU.weixin.base.entity.WXUser;
import myFrameU.weixin.base.service.AutoRegistAndLogin;
import myFrameU.weixin.base.util.GetWXUserUtil;

public class AutoRegistAndLoginImpl implements AutoRegistAndLogin {
	/**
	 * ,String ifNosubscribe_subscribeEvent
	 * 代表在自动登录/自动注册的时候，去判断有没有关注微信公共平台
	 * 如果没有关注微信公共平台，则在用户下一步关注微信公共平台的时候，需要做什么事情
	 */
	@Override
	public User autoRegistLogin(User user,String code,UserBizI userBiz,UICacheManager uICacheManager,IfNosubscribe_subscribeEvent ifNosubscribe_subscribeEvent) throws Exception {
		if(null==user){
			//只有在用户未登录、用户为注册两种情况下进行根据code查询openId
			if(null!=code && !code.equals("")){
				 myFrameU.weixin.base.service.impl.UserImpl ui = new myFrameU.weixin.base.service.impl.UserImpl();
				 String openId=GetWXUserUtil.getWXID();
				 if(null==openId || openId.equals("")){
					 openId = ui.getUserOpenId(code);
				 }
				 if(null!=openId && !openId.equals("")){
					// String allUserIds = ui.getUserList(uICacheManager);
					// if(allUserIds.contains(openId)){
						 //已经关注了
					 WXUser wxuser = GetWXUserUtil.getWXUser();
					 if(null==wxuser){
						 wxuser = ui.getWXUserByOpenId(openId, uICacheManager,"user");
					 }
						 if(null!=wxuser){
							 String subscribe=wxuser.getSubscribe();
							 if(null==subscribe || subscribe.equals("")){
								 subscribe="0";
							 }
							 user=(User)userBiz.findObjectById("from User as u where u.wxId=?", new Object[]{openId});
							 if(null==user){
								 if(subscribe.equals("1")){
									 user=createUser(subscribe, wxuser.getCity(), wxuser.getNickname(), openId, userBiz, uICacheManager,null);
								 }else{
									 user=createUser(subscribe, null, null, openId, userBiz, uICacheManager,ifNosubscribe_subscribeEvent);
								 }
							 }else{
								 
							 }
							 
						 }
					 //}
					 
				 }else{
					 System.out.println("没有获取到openId没有获取到openId没有获取到openId没有获取到openId没有获取到openId........没有获取到openId,还没有关注微信平台");
					 return null;
				 }
		}else{
			 System.out.println("没有获取到code没有获取到code没有获取到code没有获取到code没有获取到code........没有获取到code,还没有关注微信平台");
		}
		
			 
		}else{
			//重新从数据库读取user
			user = (User)userBiz.findObjectById("from User as u where u.id=?", new Object[]{user.getId()});
		}
		return user;
	}

	
	
	private static User createUser(String subscribe,String city,String nickName,String wxId,UserBizI userBiz,UICacheManager uICacheManager,IfNosubscribe_subscribeEvent ifNosubscribe_subscribeEvent) throws Exception{
		User user = null;
		if(subscribe.equals("1")){
			//已经关注 了微信公共平台的user
			int addressId = 420;
			Address cityAddress =null;
			if(null!=city && !city.equals("")){
				if(!city.endsWith("市")){
					city=city+"市";
				}
				cityAddress = (Address)userBiz.findObjectById("from Address as a where a.name=?", new Object[]{city});
				if(null==cityAddress){
					cityAddress = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, 420);
				}
				if(null!=cityAddress){
					addressId=cityAddress.getId();
				}
			}
			
			String name = new Date().getTime()+"";
			user = new User();
			user.setUserLevelId(1);
			user.setNicheng(nickName);
			user.setWxId(wxId);
			user.setAddressId(addressId);
			if(null!=cityAddress){
				user.setAddressTreeIds(cityAddress.getTreeId());	
			}
			
			
			user.setName(name);
			user.setPassword("123456");
			user.setTelPhone("");
			user.setTotalScore(0);
			user.setGrade(0);
			user.setZhuceDate(new Date());
			user.setTouxiang(wxId+".jpg");
			user.setStatus(User.STATUS.OK.toString());
			userBiz.registUser(user, cityAddress);
		}else{
			
			//尚未关注微信公共的user
			String name = new Date().getTime()+"";
			user = new User();
			user.setUserLevelId(1);
			user.setNicheng("");
			user.setWxId(wxId);
			user.setAddressId(420);
			user.setAddressTreeIds("[28][420]");
			user.setName(name);
			user.setPassword("123456");
			user.setTelPhone("");
			user.setTotalScore(0);
			user.setGrade(0);
			user.setZhuceDate(new Date());
			user.setTouxiang(wxId+".jpg");
			user.setStatus(User.STATUS.OK.toString());
			Address cityAddress=CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, 420);
			
			
			
			
			
			if(null!=ifNosubscribe_subscribeEvent){
				/*String eventType = ifNosubscribe_subscribeEvent.getEventType();
				Map<String,String> args = ifNosubscribe_subscribeEvent.getArgs();*/
				String s = JSONUtils.toJSONString(ifNosubscribe_subscribeEvent);
				user.setIfNosubscribe_subscribeEvent(s);
			}
			
			
			userBiz.registUser(user, cityAddress);
		}
		return user;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
