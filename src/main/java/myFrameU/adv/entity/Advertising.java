package myFrameU.adv.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
 * 广告位
 */
public class Advertising   implements Serializable{
	private int id;
	private String markedNum;//12.广告位编码编号
	private String info;//100.广告位简介
	private String priceInfo;//100.价格简介
	private int width;
	private int height;
	private String widthAndheight;//100.广告位的广告宽高要求简介
	private String advType;//广告位类型。1代表普图片广告，2代表焦点广告，3代表flash广告，4代表上部巨幅广告，5代表侧栏悬浮广告
	public enum ADVTYPE{IMAGE,FOUCS,FLASH,BIG,LEFTRIGHT}
	private String status;//状态
	public enum STATUS{WAIT,ING,CLOSE}
	private int picNumber;//如果是焦点广告的话，那么这里需要写出多少张来
	
	
	private Set<Advertisement> advertisementSet = new HashSet<Advertisement>();

	/*private int weizhiDh;//位置代号
	private String weizhiStr;//位置字符串
*/	
	private String advertingPageNameKey;
	
	private int addressId;
	//private String addressStr;
	private String addressTreeIds;
	
	
	//==============================
	private String image;//说明图片
	private String price;//价格要求。如果是焦点广告
	private int jifen;//所需要积分
	private int saleNum;//卖出去多少份
	private int liulanNum;//浏览数日均
	
	//========================
	//涉及到城市,所以不是一对一
/*	private int isCur;//现在当前有生效的广告吗？1是有，0没有了
	private Date curBeginTime;//如果有当前生效的广告的话，开始时间
	private Date curEndTime;
	private int curAdervId;*/
	
	
	private String haveSon;
	
	/*
	private String haveSon;
	private String sonBy;
	private String sonByValue;
	
	*/

	private int width100;//0是默认的，0代表不是width100%的，1代表100%的
	
	
	public int getId() {
		return id;
	}

	public String getAdvertingPageNameKey() {
		return advertingPageNameKey;
	}

	public void setAdvertingPageNameKey(String advertingPageNameKey) {
		this.advertingPageNameKey = advertingPageNameKey;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarkedNum() {
		return markedNum;
	}

	public void setMarkedNum(String markedNum) {
		this.markedNum = markedNum;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPriceInfo() {
		return priceInfo;
	}

	public void setPriceInfo(String priceInfo) {
		this.priceInfo = priceInfo;
	}

	public String getWidthAndheight() {
		return widthAndheight;
	}

	public void setWidthAndheight(String widthAndheight) {
		this.widthAndheight = widthAndheight;
	}

	

	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPicNumber() {
		return picNumber;
	}

	public void setPicNumber(int picNumber) {
		this.picNumber = picNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Set<Advertisement> getAdvertisementSet() {
		return advertisementSet;
	}

	public void setAdvertisementSet(Set<Advertisement> advertisementSet) {
		this.advertisementSet = advertisementSet;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getJifen() {
		return jifen;
	}

	public void setJifen(int jifen) {
		this.jifen = jifen;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}

	public int getLiulanNum() {
		return liulanNum;
	}

	public void setLiulanNum(int liulanNum) {
		this.liulanNum = liulanNum;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getHaveSon() {
		return haveSon;
	}

	public void setHaveSon(String haveSon) {
		this.haveSon = haveSon;
	}

	public int getWidth100() {
		return width100;
	}

	public void setWidth100(int width100) {
		this.width100 = width100;
	}

	
	
	
	
	
	
	
}
