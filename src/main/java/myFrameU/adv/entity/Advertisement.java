package myFrameU.adv.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 广告
 *
 */
public class Advertisement   implements Serializable{
	private int id;
	private Advertising advertising;//广告位
	private String advertisingMarkedNum;
	
	private String markeNum;//12.广告编号
	private Date relDate;//下单时间
	private Date beginDate;//开启时间
	private Date endDate;//关闭时间
	private String advType;
	
	
	private int indexNum;//如果广告位类型是焦点广告的话，则标志着第几张图片
	private int remainTime;//如果广告位类型是上边巨幅广告，则标志着停留时间
	private String picUrl;//50.广告图片地址
	private String picSmall;//如果是巨幅广告，则需要还有一个小图片
	private String picTitle;//100.广告图片标题
	private String picA;//100.广告图连接地址
	private String flashUrl;//50.如果广告类型是flash，则表示flash地址
	private float oldPrice;//从广告位继承下来的本来的价格
	private float price;//成交价格
	private float priceMeiyue;//每月多少的价格
	
	private String widthAndHeight;//100.广告的尺寸
	private int isWeb;//是否是官方做的广告
	private String status;//状态
	public enum STATUS{WAIT,ING,CLOSE};
	private int isJiaoqian;//是不是交钱的广告，为什么会有这个属性，原因有2：
	//1)网站一开始，是没有广告的，那么这些广告位就需要填充
	//2）在做优惠活动的时候，很可能给商家做一个免费的。
	
	
	
	
	
	
	private int shopId;
	private String shopName;
	private int addressId;
	private String addressTreeIds;
	
	
	
	
	
	//如果是栏目页面的话，需要指定这个广告图是那个pt下的
	private String sonByValue;
	
	
	
	
	public String getAddressTreeIds() {
		return addressTreeIds;
	}
	public void setAddressTreeIds(String addressTreeIds) {
		this.addressTreeIds = addressTreeIds;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Advertising getAdvertising() {
		return advertising;
	}
	public void setAdvertising(Advertising advertising) {
		this.advertising = advertising;
	}
	public String getMarkeNum() {
		return markeNum;
	}
	public void setMarkeNum(String markeNum) {
		this.markeNum = markeNum;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

	public String getAdvType() {
		return advType;
	}
	public void setAdvType(String advType) {
		this.advType = advType;
	}
	public int getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}
	public int getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getPicTitle() {
		return picTitle;
	}
	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}
	public String getPicA() {
		return picA;
	}
	public void setPicA(String picA) {
		this.picA = picA;
	}
	public String getFlashUrl() {
		return flashUrl;
	}
	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}
	public float getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getWidthAndHeight() {
		return widthAndHeight;
	}
	public void setWidthAndHeight(String widthAndHeight) {
		this.widthAndHeight = widthAndHeight;
	}



	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIsJiaoqian() {
		return isJiaoqian;
	}
	public void setIsJiaoqian(int isJiaoqian) {
		this.isJiaoqian = isJiaoqian;
	}
	public float getPriceMeiyue() {
		return priceMeiyue;
	}
	public void setPriceMeiyue(float priceMeiyue) {
		this.priceMeiyue = priceMeiyue;
	}
	public Date getRelDate() {
		return relDate;
	}
	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}
	public String getPicSmall() {
		return picSmall;
	}
	public void setPicSmall(String picSmall) {
		this.picSmall = picSmall;
	}
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getIsWeb() {
		return isWeb;
	}
	public void setIsWeb(int isWeb) {
		this.isWeb = isWeb;
	}
	public String getAdvertisingMarkedNum() {
		return advertisingMarkedNum;
	}
	public void setAdvertisingMarkedNum(String advertisingMarkedNum) {
		this.advertisingMarkedNum = advertisingMarkedNum;
	}
	public String getSonByValue() {
		return sonByValue;
	}
	public void setSonByValue(String sonByValue) {
		this.sonByValue = sonByValue;
	}

	
	
	
	
	
	
	
}
