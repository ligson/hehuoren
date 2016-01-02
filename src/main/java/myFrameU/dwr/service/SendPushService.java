package myFrameU.dwr.service;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

public class SendPushService {
	// 发送消息
	public static void send(final String msg,final String toWho) {
		System.out.println("==========调用了send方法==========");
		Runnable run = new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				// 设置要调用的 js及参数
				if(toWho.equals("admin")){
					script.appendCall("myAdminMessage", msg);
				}else if(toWho.equals("shop")){
					script.appendCall("myShopMessage", msg);
				}else if(toWho.equals("user")){
					script.appendCall("myUserMessage", msg);
				}
			
				
				// 得到所有ScriptSession
				Collection<ScriptSession> sessions = Browser
						.getTargetSessions();
				// 遍历每一个ScriptSession
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		};
		// 执行推送
		Browser.withAllSessions(run);
	}
	
	public static void sendToAdmin(String msg){
		send(msg,"admin");
	}
	public static void sendToShop(String msg){
		send(msg,"shop");
	}
	public static void sendToUser(String msg){
		send(msg,"user");
	}
	
	
	
	
	
	
	
	
}
