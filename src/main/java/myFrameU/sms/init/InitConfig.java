package myFrameU.sms.init;

import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	private String serviceURL;
	private String userId;
	private String account;
	private String password;
	private String suffix;
	
	private String status;
	
	
	private boolean saveRecord;//是否记录发送记录
	private String sdkMtType;
	private String telPhones;
	private String smsOthers;
	private String smsYanzhengma;
	
	private String myCreateContentUtil;
	
	
	
	
	
	
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public String getServiceURL() {
		return serviceURL;
	}
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public boolean getSaveRecord() {
		return saveRecord;
	}
	public void setSaveRecord(boolean saveRecord) {
		this.saveRecord = saveRecord;
	}
	public String getMyCreateContentUtil() {
		return myCreateContentUtil;
	}
	public void setMyCreateContentUtil(String myCreateContentUtil) {
		this.myCreateContentUtil = myCreateContentUtil;
	}
	public String getSdkMtType() {
		return sdkMtType;
	}
	public void setSdkMtType(String sdkMtType) {
		this.sdkMtType = sdkMtType;
	}
	public String getTelPhones() {
		return telPhones;
	}
	public void setTelPhones(String telPhones) {
		this.telPhones = telPhones;
	}
	public String getSmsOthers() {
		return smsOthers;
	}
	public void setSmsOthers(String smsOthers) {
		this.smsOthers = smsOthers;
	}
	public String getSmsYanzhengma() {
		return smsYanzhengma;
	}
	public void setSmsYanzhengma(String smsYanzhengma) {
		this.smsYanzhengma = smsYanzhengma;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	
	
}
