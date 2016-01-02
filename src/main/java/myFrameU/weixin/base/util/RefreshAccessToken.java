package myFrameU.weixin.base.util;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.weixin.base.service.impl.AccessTokenImpl;


public class RefreshAccessToken {
	private UICacheManager uICacheManager;
	public RefreshAccessToken(){}
	public RefreshAccessToken(int second,UICacheManager uICacheManager){
		this.uICacheManager=uICacheManager;
		Timer timer=new Timer();
		timer.schedule(new RefreshAccessTokenTask(), 0, second*1000);
	}
	private class RefreshAccessTokenTask extends TimerTask{
		@Override
		public void run() {
			try{
				//在此处通过httpclient反复获取access token
				AccessTokenImpl i = new AccessTokenImpl();
				i.refreshAccessToken(uICacheManager);
			}catch(Exception e){
				
			}
		}
		
	}
}

