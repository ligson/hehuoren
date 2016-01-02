package myFrameU.weixin.base.service;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.weixin.base.entity.IfNosubscribe_subscribeEvent;

public interface AutoRegistAndLogin {
	
	public User autoRegistLogin(User user,String code,UserBizI userBiz,UICacheManager uICacheManager,IfNosubscribe_subscribeEvent ifNosubscribe_subscribeEvent) throws Exception;
}
