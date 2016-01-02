package myFrameU.admin.vo;

public class MergerShopUser {
	private int id;
	private String name;
	private String telPhone;
	private String logo;
	
	private String userOrShop;
	public enum USERORSHOP{USER,SHOP}
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
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getUserOrShop() {
		return userOrShop;
	}
	public void setUserOrShop(String userOrShop) {
		this.userOrShop = userOrShop;
	}
	
}
