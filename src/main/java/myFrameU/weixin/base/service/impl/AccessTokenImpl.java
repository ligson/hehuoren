package myFrameU.weixin.base.service.impl;


import org.json.JSONObject;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import myFrameU.weixin.base.service.AccessTokenInterface;

public class AccessTokenImpl implements AccessTokenInterface {
	
	@Override
	public String getAccess_token(UICacheManager uICacheManager)  throws Exception {
		String accessToken = (String)uICacheManager.getObjectCached("web", "weixin_access_token");
		return accessToken;
	}

	@Override
	public void refreshAccessToken(UICacheManager uICacheManager)  throws Exception {
		String URL_getAccessToken=myFrameU.weixin.init.InitMavenImpl.ic.getAccessTokenUrl();
		String accessToken=HttpClientUtil.get(URL_getAccessToken, null);
		if(null!=accessToken && !accessToken.equals("")){
			JSONObject a = new JSONObject(accessToken);
			accessToken=a.getString("access_token");
		}
		
		System.out.println("获取的微信access_token为："+accessToken);
		
		uICacheManager.putObjectCached("web", "weixin_access_token", accessToken);
	}

}
