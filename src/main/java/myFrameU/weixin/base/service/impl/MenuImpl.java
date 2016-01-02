package myFrameU.weixin.base.service.impl;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.weixin.base.service.MenuInterface;
import myFrameU.weixin.base.util.HttpClientConnectionManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class MenuImpl implements MenuInterface {
	public static DefaultHttpClient httpclient;
	static {    
        httpclient = new DefaultHttpClient();    
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端    
    }  
	
	@Override
	public String createMenu(String params,UICacheManager uICacheManager) throws Exception {
		AccessTokenImpl ati = new AccessTokenImpl();
		String accessToken=ati.getAccess_token(uICacheManager);
		
		HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken);    
        httpost.setEntity(new StringEntity(params, "UTF-8"));    
        HttpResponse response = httpclient.execute(httpost);    
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
        JSONObject a = new JSONObject(jsonStr);
        return a.getString("errmsg");  
	}

	@Override
	public String getMenuInfo(UICacheManager uICacheManager)  throws Exception {
		return null;
	}

	@Override
	public String deleteMenu(UICacheManager uICacheManager)  throws Exception {
		AccessTokenImpl ati = new AccessTokenImpl();
		String accessToken=ati.getAccess_token(uICacheManager);
		 	HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken);    
	        HttpResponse response = httpclient.execute(get);    
	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");    
	        JSONObject a = new JSONObject(jsonStr);
	        return a.getString("errmsg");   
	}

}
