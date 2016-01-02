package myFrameU.product.entity;

import java.io.Serializable;

public class ProductPrice implements Serializable{
	public static final String lockKC_="lockKC_";
	public static String getLockKC(int id){
		return lockKC_+id;
	}
	
	private int id;
	private int productId;
	private String productName;
	private String productImg;
	
	
	
	private String xilieName;//系列名字,净含量
	private String baozhuangName;//包装名字，规格
	private String shiyongName;//使用
	
	private float price1;//网店价格
	private float price2;//合伙人价格
	private float toTjrTc;//给推荐人的提成百分比。5--5%

	private float price1Price2;

	
	private int yesproductPrice;//是否映射到product的价格.0是默认的，不是，1代表是。
	
	
	
	private int productCount;//
	private int saleCount;//销售额
	
	//从product上继承西来
	private String pickupAddressIds;//[][][]
	
	
	
	private String keyName1;
	private String keyName2;
	private String keyName3;
	private String keyName4;
	private String keyName5;

	
	//===================
	private String noSave_select;
	
	
	
	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getXilieName() {
		return xilieName;
	}

	public void setXilieName(String xilieName) {
		this.xilieName = xilieName;
	}

	public String getBaozhuangName() {
		return baozhuangName;
	}

	public void setBaozhuangName(String baozhuangName) {
		this.baozhuangName = baozhuangName;
	}

	public float getPrice1() {
		return price1;
	}

	public void setPrice1(float price1) {
		this.price1 = price1;
	}

	public float getPrice2() {
		return price2;
	}

	public void setPrice2(float price2) {
		this.price2 = price2;
	}

	public float getToTjrTc() {
		return toTjrTc;
	}

	public void setToTjrTc(float toTjrTc) {
		this.toTjrTc = toTjrTc;
	}

	public float getPrice1Price2() {
		return price1Price2;
	}

	public void setPrice1Price2(float price1Price2) {
		this.price1Price2 = price1Price2;
	}

	public int getYesproductPrice() {
		return yesproductPrice;
	}

	public void setYesproductPrice(int yesproductPrice) {
		this.yesproductPrice = yesproductPrice;
	}

	public String getPickupAddressIds() {
		return pickupAddressIds;
	}

	public void setPickupAddressIds(String pickupAddressIds) {
		this.pickupAddressIds = pickupAddressIds;
	}

	public String getShiyongName() {
		return shiyongName;
	}

	public void setShiyongName(String shiyongName) {
		this.shiyongName = shiyongName;
	}

	public String getKeyName1() {
		return keyName1;
	}

	public void setKeyName1(String keyName1) {
		this.keyName1 = keyName1;
	}

	public String getKeyName2() {
		return keyName2;
	}

	public void setKeyName2(String keyName2) {
		this.keyName2 = keyName2;
	}

	public String getKeyName3() {
		return keyName3;
	}

	public void setKeyName3(String keyName3) {
		this.keyName3 = keyName3;
	}

	public String getKeyName4() {
		return keyName4;
	}

	public void setKeyName4(String keyName4) {
		this.keyName4 = keyName4;
	}

	public String getKeyName5() {
		return keyName5;
	}

	public void setKeyName5(String keyName5) {
		this.keyName5 = keyName5;
	}

	public String getNoSave_select() {
		return noSave_select;
	}

	public void setNoSave_select(String noSave_select) {
		this.noSave_select = noSave_select;
	}
	
	
	
	
	
	
}
