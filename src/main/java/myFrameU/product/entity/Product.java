package myFrameU.product.entity;

import java.io.Serializable;
import java.util.Date;

import myFrameU.expand.use.entity.AbstractExpandEntity;

public class Product extends AbstractExpandEntity implements Serializable{
	public static final String lockKC_="lockKC_";
	public static String getLockKC(int id){
		return lockKC_+id;
	}
	private int id;
	private String name;
	
	//产品图
	private String lunboImage1;
	private String lunboImage2;
	private String lunboImage3;
	private String lunboImage4;
	private String lunboImage5;
	private String mainImage;
	
	//产品分类
	private int productTypeId;
	private String productTypeTreeIds;
	
	
	//产品详情
	private int productContentId;
	
	
	private int addressId;
	private String addressTreeIds;
	
	private int productCount;//
	
	private String status;

	public enum STATUS{CLOSE,OK};//已冻结，
	
	private String propertyValues_Ids;
	private String propertyValues_Strs;
	
	private Date relDate;//发布日期
	
	private int saleCount;//销售额

	//-----------------
	private float price1;//网店价格
	private float price2;//合伙人价格
	private float toTjrTc;//给推荐人的提成百分比。5--5%

	private float price1Price2;
	
	
	private String pickupAddressIds;//[][][]
	
	
	
	
	public String getPickupAddressIds() {
		return pickupAddressIds;
	}

	public void setPickupAddressIds(String pickupAddressIds) {
		this.pickupAddressIds = pickupAddressIds;
	}

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

	public String getLunboImage1() {
		return lunboImage1;
	}

	public void setLunboImage1(String lunboImage1) {
		this.lunboImage1 = lunboImage1;
	}

	public String getLunboImage2() {
		return lunboImage2;
	}

	public void setLunboImage2(String lunboImage2) {
		this.lunboImage2 = lunboImage2;
	}

	public String getLunboImage3() {
		return lunboImage3;
	}

	public void setLunboImage3(String lunboImage3) {
		this.lunboImage3 = lunboImage3;
	}

	public String getLunboImage4() {
		return lunboImage4;
	}

	public void setLunboImage4(String lunboImage4) {
		this.lunboImage4 = lunboImage4;
	}

	public String getLunboImage5() {
		return lunboImage5;
	}

	public void setLunboImage5(String lunboImage5) {
		this.lunboImage5 = lunboImage5;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeTreeIds() {
		return productTypeTreeIds;
	}

	public void setProductTypeTreeIds(String productTypeTreeIds) {
		this.productTypeTreeIds = productTypeTreeIds;
	}

	public int getProductContentId() {
		return productContentId;
	}

	public void setProductContentId(int productContentId) {
		this.productContentId = productContentId;
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

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRelDate() {
		return relDate;
	}

	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public float getToTjrTc() {
		return toTjrTc;
	}

	public void setToTjrTc(float toTjrTc) {
		this.toTjrTc = toTjrTc;
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

	public String getPropertyValues_Ids() {
		return propertyValues_Ids;
	}

	public void setPropertyValues_Ids(String propertyValues_Ids) {
		this.propertyValues_Ids = propertyValues_Ids;
	}

	public String getPropertyValues_Strs() {
		return propertyValues_Strs;
	}

	public void setPropertyValues_Strs(String propertyValues_Strs) {
		this.propertyValues_Strs = propertyValues_Strs;
	}

	public float getPrice1Price2() {
		return price1Price2;
	}

	public void setPrice1Price2(float price1Price2) {
		this.price1Price2 = price1Price2;
	}
	
	
	
	
	
	
}
