package myFrameU.user.entity;

import java.io.Serializable;

import myFrameU.address.entity.Address;

public class MyAddress  implements Serializable{
	private  int id;
	private int userId;
	private int addressId;
	private String addressTreeIds;
	private String name;
	private String jutiAddress;
	private String zipcode;
	private String telPhone;
	private String phone;
	private int isDefault;//0不是，1是默认的，后面的覆盖前面的。
	
	
	
	private String qq;
	private String email;
	private String msn;
	private String ww;
	
	private double coorX;
	private double coorY;
	
	
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJutiAddress() {
		return jutiAddress;
	}
	public void setJutiAddress(String jutiAddress) {
		this.jutiAddress = jutiAddress;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public String getAddressTreeIds() {
		return addressTreeIds;
	}
	public void setAddressTreeIds(String addressTreeIds) {
		this.addressTreeIds = addressTreeIds;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getWw() {
		return ww;
	}
	public void setWw(String ww) {
		this.ww = ww;
	}
	public double getCoorX() {
		return coorX;
	}
	public void setCoorX(double coorX) {
		this.coorX = coorX;
	}
	public double getCoorY() {
		return coorY;
	}
	public void setCoorY(double coorY) {
		this.coorY = coorY;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	
}
