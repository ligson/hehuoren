package hhr.order.entity;

public class PickUpAddress {
	private int id;
	private String tpName;//type
	private String name;
	private String addressStr;
	private String telPhone;
	
	private int addressId;
	private String addressTreeIds;
	
	
	private String markedNum;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddressStr() {
		return addressStr;
	}
	public void setAddressStr(String addressStr) {
		this.addressStr = addressStr;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getTpName() {
		return tpName;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}
	public String getMarkedNum() {
		return markedNum;
	}
	public void setMarkedNum(String markedNum) {
		this.markedNum = markedNum;
	}
	
	
	
	
}
