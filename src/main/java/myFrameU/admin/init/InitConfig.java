package myFrameU.admin.init;

import java.util.HashMap;

public class InitConfig{
	private Class initMavenClass;
	private String loginErrorPage;//如果登录错误，则指向哪个页面
	
	//如果登录成功，则要反射运行哪个类的哪个方法
	private String afterClass;
	private HashMap<String,String> afterMethodMap;
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

	
	
}
