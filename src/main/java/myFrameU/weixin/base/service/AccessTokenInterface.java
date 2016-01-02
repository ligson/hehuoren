package myFrameU.weixin.base.service;


import myFrameU.ehcache.util.UICacheManager;

public interface AccessTokenInterface {
		//获取access token
		public String getAccess_token(UICacheManager uICacheManager) throws Exception ;
		//刷新access token
		public void refreshAccessToken(UICacheManager uICacheManager) throws Exception ;
}
