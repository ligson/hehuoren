package myFrameU.weixin.base.service;

import myFrameU.ehcache.util.UICacheManager;

public interface MenuInterface {
	public String createMenu(String params,UICacheManager uICacheManager) throws Exception;
	public String getMenuInfo(UICacheManager uICacheManager) throws Exception;
	public String deleteMenu(UICacheManager uICacheManager) throws Exception;
	
}
