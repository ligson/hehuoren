package myFrameU.admin.entity;

import java.io.Serializable;



public class Admin   implements Serializable{
	private int id;
	private String name;//12
	private String password;//16
	
	private int superOrCom;//1 is super  , 0 is Com
	private int status;//1为正常，-1为停用了。


	private int addressId;//地区,专指的是cityId
	private String addressTreeIds;//地区treeId
	private String cityName;
	
	private int isCanAccount;//是否可以收款付款.默认的是0，代表没有权限。1代表有权限

	
	private String telPhone;
	private String wxId;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSuperOrCom() {
		return superOrCom;
	}

	public void setSuperOrCom(int superOrCom) {
		this.superOrCom = superOrCom;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddressTreeIds() {
		return addressTreeIds;
	}

	public void setAddressTreeIds(String addressTreeIds) {
		this.addressTreeIds = addressTreeIds;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getIsCanAccount() {
		return isCanAccount;
	}

	public void setIsCanAccount(int isCanAccount) {
		this.isCanAccount = isCanAccount;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	
	
	
	
}
