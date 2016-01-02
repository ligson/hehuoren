package myFrameU.weixin.init;

import java.util.HashMap;


public class InitConfig{
	private Class initMavenClass;
	
	private String token;
	private String appId;
	private String secret;
	
	private String accessTokenUrl;
	private String getOpenIdUrl;
	private String subscribeTxt;
	private String menuTxtUrl;
	
	
	
	
	private String payMchId;
	private String paySpbillCreateIp;
	private String payNotifyUrl;
	
	
	private String appKey;//微信支付密钥
	
	
	//每个项目自己的templateId都不同，所以要对应起来
	private HashMap<String,String> templateMsgId_myAndWx;
	
	
	
	
	
	
	private String adminWxid_2wmUrl;
	
	
	
	
	
	
	
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
	public String getSubscribeTxt() {
		return subscribeTxt;
	}
	public void setSubscribeTxt(String subscribeTxt) {
		this.subscribeTxt = subscribeTxt;
	}
	public String getMenuTxtUrl() {
		return menuTxtUrl;
	}
	public void setMenuTxtUrl(String menuTxtUrl) {
		this.menuTxtUrl = menuTxtUrl;
	}
	public String getGetOpenIdUrl() {
		return getOpenIdUrl;
	}
	public void setGetOpenIdUrl(String getOpenIdUrl) {
		this.getOpenIdUrl = getOpenIdUrl;
	}
	public String getPayMchId() {
		return payMchId;
	}
	public void setPayMchId(String payMchId) {
		this.payMchId = payMchId;
	}
	public String getPaySpbillCreateIp() {
		return paySpbillCreateIp;
	}
	public void setPaySpbillCreateIp(String paySpbillCreateIp) {
		this.paySpbillCreateIp = paySpbillCreateIp;
	}
	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}
	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public HashMap<String, String> getTemplateMsgId_myAndWx() {
		return templateMsgId_myAndWx;
	}
	public void setTemplateMsgId_myAndWx(
			HashMap<String, String> templateMsgId_myAndWx) {
		this.templateMsgId_myAndWx = templateMsgId_myAndWx;
	}
	public String getAdminWxid_2wmUrl() {
		return adminWxid_2wmUrl;
	}
	public void setAdminWxid_2wmUrl(String adminWxid_2wmUrl) {
		this.adminWxid_2wmUrl = adminWxid_2wmUrl;
	}
	
	
	
	
	
}
