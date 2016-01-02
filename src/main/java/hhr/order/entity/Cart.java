package hhr.order.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 */
public class Cart   implements Serializable{
	private int id;
	private int tuijianRenId;
	private String tujianRenName;
	private int userId;
	private String userName;
	private int myAddressId;//我的地址,暂时没有用，因为是自提的，但是作为扩展。
	
	
	private int totalCount;
	private float totalPrice;//付款金额
	
	//======================
	private float toHehuorenPrice;//给他的推荐人（合伙人）多少钱
	private float toWebPrice;//给平台多少钱。
	
	
	//===============================
	private String remarks;//用户自己填写的

	
	
	private Set<CartItem> cartItemSet;
	

	
	
	private String productIds;//[][][]
	private String productPriceIds;//[][][]
	
	
	
	
	//收货人电话姓名
	private String shouhuoTelphone;
	private String shouhuoName;
	
	
	
	
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTuijianRenId() {
		return tuijianRenId;
	}


	public void setTuijianRenId(int tuijianRenId) {
		this.tuijianRenId = tuijianRenId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getMyAddressId() {
		return myAddressId;
	}


	public void setMyAddressId(int myAddressId) {
		this.myAddressId = myAddressId;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public float getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}


	

	public float getToHehuorenPrice() {
		return toHehuorenPrice;
	}


	public void setToHehuorenPrice(float toHehuorenPrice) {
		this.toHehuorenPrice = toHehuorenPrice;
	}


	public float getToWebPrice() {
		return toWebPrice;
	}


	public void setToWebPrice(float toWebPrice) {
		this.toWebPrice = toWebPrice;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}




	public String getTujianRenName() {
		return tujianRenName;
	}


	public void setTujianRenName(String tujianRenName) {
		this.tujianRenName = tujianRenName;
	}


	public Set<CartItem> getCartItemSet() {
		return cartItemSet;
	}


	public void setCartItemSet(Set<CartItem> cartItemSet) {
		this.cartItemSet = cartItemSet;
	}


	public String getProductIds() {
		return productIds;
	}


	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}


	public String getShouhuoTelphone() {
		return shouhuoTelphone;
	}


	public void setShouhuoTelphone(String shouhuoTelphone) {
		this.shouhuoTelphone = shouhuoTelphone;
	}


	public String getShouhuoName() {
		return shouhuoName;
	}


	public void setShouhuoName(String shouhuoName) {
		this.shouhuoName = shouhuoName;
	}


	public String getProductPriceIds() {
		return productPriceIds;
	}


	public void setProductPriceIds(String productPriceIds) {
		this.productPriceIds = productPriceIds;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
