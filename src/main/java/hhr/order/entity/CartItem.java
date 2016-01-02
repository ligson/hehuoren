package hhr.order.entity;

import java.io.Serializable;

public class CartItem implements Serializable{
	private int id;
	private Cart cart;
	private int productId;
	private int productPriceId;
	
	private int ocount;
	private float price;//单价
	private float tprice;//总价
	
	private float toHehuorenPrice;//给他的推荐人（合伙人）多少钱
	private float toWebPrice;//给平台多少钱。
	
	
	
	
	
	
	
	
	private int pickupAddressId;//自提点儿ID

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getOcount() {
		return ocount;
	}

	public void setOcount(int ocount) {
		this.ocount = ocount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTprice() {
		return tprice;
	}

	public void setTprice(float tprice) {
		this.tprice = tprice;
	}

	public int getPickupAddressId() {
		return pickupAddressId;
	}

	public void setPickupAddressId(int pickupAddressId) {
		this.pickupAddressId = pickupAddressId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
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

	public int getProductPriceId() {
		return productPriceId;
	}

	public void setProductPriceId(int productPriceId) {
		this.productPriceId = productPriceId;
	}
	
	
	
}
