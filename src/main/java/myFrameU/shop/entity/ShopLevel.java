package myFrameU.shop.entity;

import java.io.Serializable;


public class ShopLevel   implements Serializable{
	private int id;
	private String name;//类目名字
	private float presenceInPrice;//入驻费
	
	private float specialPrice;//自己举办专场费用
	private String specialReturnPlan;//自己举办专场退还方案
	public enum RETURNPLAN{ONE,TWO}
	
	private String webSpecialReturnPlan;//平台举办的专场，你要参加的话，缴纳的保证金数量是每个专场不同的，但是返还机制还是看shop 等级
	//public enum WEBRETURNPLAN{ONE,TWO}
	
	
	private int shopMainBigProductTypeCount;//shop选择主营类目大类的最大数量
	
	
	/*private float two_totalSpecialPrice;//需要达到的总的销售额
	private float two_price;//固定收取的费用
	*/
	

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
	public float getPresenceInPrice() {
		return presenceInPrice;
	}
	public void setPresenceInPrice(float presenceInPrice) {
		this.presenceInPrice = presenceInPrice;
	}
	public float getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(float specialPrice) {
		this.specialPrice = specialPrice;
	}
	public String getSpecialReturnPlan() {
		return specialReturnPlan;
	}
	public void setSpecialReturnPlan(String specialReturnPlan) {
		this.specialReturnPlan = specialReturnPlan;
	}
	public String getWebSpecialReturnPlan() {
		return webSpecialReturnPlan;
	}
	public void setWebSpecialReturnPlan(String webSpecialReturnPlan) {
		this.webSpecialReturnPlan = webSpecialReturnPlan;
	}
	/*public float getTwo_totalSpecialPrice() {
		return two_totalSpecialPrice;
	}
	public void setTwo_totalSpecialPrice(float two_totalSpecialPrice) {
		this.two_totalSpecialPrice = two_totalSpecialPrice;
	}
	public float getTwo_price() {
		return two_price;
	}
	public void setTwo_price(float two_price) {
		this.two_price = two_price;
	}
	*/
	public int getShopMainBigProductTypeCount() {
		return shopMainBigProductTypeCount;
	}
	public void setShopMainBigProductTypeCount(int shopMainBigProductTypeCount) {
		this.shopMainBigProductTypeCount = shopMainBigProductTypeCount;
	}
	
}
