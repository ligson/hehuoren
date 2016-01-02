package myFrameU.user.init;

import java.util.HashMap;
import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	private String loginErrorPage;//如果登录错误，则指向哪个页面
	
	//要反射运行哪个类的哪个方法
	private String afterClass;
	private HashMap<String,String> afterMethodMap;
	
	
	
	
	
	/**
	 * 收藏
	 * <Shop,shoucangCount>
	 */
	private HashMap<String,String> shoucangMap;
	
	
	
	
	
	
	
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public String getLoginErrorPage() {
		return loginErrorPage;
	}
	public void setLoginErrorPage(String loginErrorPage) {
		this.loginErrorPage = loginErrorPage;
	}
	public String getAfterClass() {
		return afterClass;
	}
	public void setAfterClass(String afterClass) {
		this.afterClass = afterClass;
	}
	public HashMap<String, String> getAfterMethodMap() {
		return afterMethodMap;
	}
	public void setAfterMethodMap(HashMap<String, String> afterMethodMap) {
		this.afterMethodMap = afterMethodMap;
	}
	public HashMap<String, String> getShoucangMap() {
		return shoucangMap;
	}
	public void setShoucangMap(HashMap<String, String> shoucangMap) {
		this.shoucangMap = shoucangMap;
	}
	
}
