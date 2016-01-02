package myFrameU.weixin.base.service;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.weixin.base.entity.WXUser;

public interface UserInterface {
	public WXUser getWXUserByOpenId(String openId,UICacheManager uICacheManager,String shopOrUser) throws Exception ;
	
	//获取关注的users的openIds
	public String getUserList(UICacheManager uICacheManager) throws Exception ;
	
	
	
	
	
	public String getUserOpenId(String code) throws Exception;
	
	
}
