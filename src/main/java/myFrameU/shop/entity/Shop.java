package myFrameU.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import myFrame.cache.CacheKey;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.product.entity.ProductType;

/*
 * 
 */

public class Shop  implements Serializable {
	public Shop(){
	}
	public Shop(int id,String name,String logo){
		this.id=id;
		this.name=name;
		this.logo=logo;
	}
	private int shopLevelId;//店铺级别
	
	private int id;//*
	private String name;//50.店铺名 *
	private String smallName;//店铺短名
	private String logo;//
	private String phone;//30个字符,将电话和手机都写在这都可以.
	private String info;//100.店铺简介，详情是shopInfoContent
	
	private String specificAddress;//50.具体地址，比如大悦城C42505*
	private Date ruzhuDate;//入住时间*
	
	
	private int shoucangCount;//收藏数目*
	
	/**
	 * 这里需要注意一下，因为本项目中，auctionPeriod由ing-end的时候，end还没结束，还得看付款不付款
	 * 这里的成交次数和成交金额都是真实的。即如果中标了不付款，虽然将保证金付款给商家了，但是也依然不能算是中标成交金额。
	 */
	private int clinchCount;//成交次数*
	private float clinchPrice;//成交金额
	private int specialCount;//专场数
	
	//自己的产品数量
	private int productCount;
	private int auctionPeriodCount;//拍品数，累计数，关闭的也算。
	
	
	//自己所运营的主要类目
	//已【】【】
	private String mainProductTypeIds;
	
	//
	private List<ProductType> mainProductTypeList;
	
	
	public List<ProductType> getMainProductTypeList() {
		return mainProductTypeList;
	}
	public void setMainProductTypeList(UICacheManager uICacheManager) {
		String mainProductTypeIds_s=mainProductTypeIds;
		HashMap<String,ProductType> ptMap = CacheKey.CKProductType.ALLMAP.getMap(uICacheManager);
		
		if(null!=mainProductTypeIds_s && !mainProductTypeIds_s.equals("")){
			
			mainProductTypeList = getMainProductTypeList();
			if(null==mainProductTypeList){
				mainProductTypeList=new ArrayList<ProductType>();
				if(mainProductTypeIds_s.contains("][")){
					//说明有多个5][7][8][9][10
					mainProductTypeIds_s=mainProductTypeIds_s.substring(1,mainProductTypeIds_s.length()-1);
					String[] array = mainProductTypeIds_s.split("]\\[");
					int len = array.length;
					String ptIdStr = null;
					int ptId = 0;
					ProductType pt = null;
					for(int i=0;i<len;i++){
						ptIdStr=array[i];
						//System.out.println(ptIdStr+"===============================================");
						ptId = new Integer(ptIdStr).intValue();
						pt = ptMap.get(CacheKey.CKProductType.ALLMAP.getMapKey()+ptId);
						if(null!=pt){
							mainProductTypeList.add(pt);
						}
					}
					
					
				}else{
					//说明只有一个
					mainProductTypeIds_s=mainProductTypeIds_s.substring(1,mainProductTypeIds_s.length()-1);
					int ptId = new Integer(mainProductTypeIds_s).intValue();
					ProductType pt = ptMap.get(CacheKey.CKProductType.ALLMAP.getMapKey()+ptId);
					if(null!=pt){
						mainProductTypeList.add(pt);
					}
				}
			}else{
				
			}
			
		}
		this.mainProductTypeList = mainProductTypeList;
	}
	//积分等级、积分
	private int grade;
	private float totalScore;
	
	//shopUser
	private int shopUserId;
	
	
	//地区
	private int addressId;
	private String addressTreeIds;
	
	
	private String certiStatus;//认证状态,等待认证
	//wait代表的是certification是wait、waitally
	public enum CERTISTATUS{WAIT,OK}
	
	private String status;
	public enum STATUS{WAIT,OK,FROZEN};//等待开通，开通了，冻结了
	
	//==========================================
	// 不能超过5个每个大类
	private String ptVip;
	//在首页的产品分类列表右边有展示推荐的shop
	public enum PTVIP{YES,NO};
	
	
	public int getShopLevelId() {
		return shopLevelId;
	}
	public void setShopLevelId(int shopLevelId) {
		this.shopLevelId = shopLevelId;
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
	public String getSmallName() {
		return smallName;
	}
	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getSpecificAddress() {
		return specificAddress;
	}
	public void setSpecificAddress(String specificAddress) {
		this.specificAddress = specificAddress;
	}
	public Date getRuzhuDate() {
		return ruzhuDate;
	}
	public void setRuzhuDate(Date ruzhuDate) {
		this.ruzhuDate = ruzhuDate;
	}
	public int getShoucangCount() {
		return shoucangCount;
	}
	public void setShoucangCount(int shoucangCount) {
		this.shoucangCount = shoucangCount;
	}
	public int getClinchCount() {
		return clinchCount;
	}
	public void setClinchCount(int clinchCount) {
		this.clinchCount = clinchCount;
	}

	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public String getMainProductTypeIds() {
		return mainProductTypeIds;
	}
	public void setMainProductTypeIds(String mainProductTypeIds) {
		this.mainProductTypeIds = mainProductTypeIds;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public float getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}
	public int getShopUserId() {
		return shopUserId;
	}
	public void setShopUserId(int shopUserId) {
		this.shopUserId = shopUserId;
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
	public String getCertiStatus() {
		return certiStatus;
	}
	public void setCertiStatus(String certiStatus) {
		this.certiStatus = certiStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public float getClinchPrice() {
		return clinchPrice;
	}
	public void setClinchPrice(float clinchPrice) {
		this.clinchPrice = clinchPrice;
	}
	public int getSpecialCount() {
		return specialCount;
	}
	public void setSpecialCount(int specialCount) {
		this.specialCount = specialCount;
	}
	public String getPtVip() {
		return ptVip;
	}
	public void setPtVip(String ptVip) {
		this.ptVip = ptVip;
	}
	public int getAuctionPeriodCount() {
		return auctionPeriodCount;
	}
	public void setAuctionPeriodCount(int auctionPeriodCount) {
		this.auctionPeriodCount = auctionPeriodCount;
	}

	
	
	
}
