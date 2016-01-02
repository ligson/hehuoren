package myFrameU.news.entity;

import java.util.Date;
import java.util.Set;

public class News {
	private int id;
	private String title;
	private String title1;
	private String image;
	private Date relDate;
	private int viewsCount;//浏览量
	
	private String roleType;
	public enum ROLETYPE{
		WEB,SHOP,USER
	}
	private int roleId;//如果是web的，就是0
	
	private int newsTypeId;
	private int newsTypeROOTId;
	private String newsTypeTreeIds;
	
	
	
	
	private String info;//50个
	private int newsContentId;

	
	private int addressId;
	private String addressTreeIds;
	
	
	private String status;
	public enum STATUS{
		WAITAPPLY,APPLYEERROR,OK,CLOSE
	}
	
	//是否是可以删除重新写一片，默认的都是yes，但是又的情况，如footer的各种帮助文档，只能修改
	private String canDel;
	public enum CANDEL{
		YES,NO
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle1() {
		return title1;
	}

	public void setTitle1(String title1) {
		this.title1 = title1;
	}

	public Date getRelDate() {
		return relDate;
	}

	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}

	public int getViewsCount() {
		return viewsCount;
	}

	public void setViewsCount(int viewsCount) {
		this.viewsCount = viewsCount;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


	public int getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(int newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

	public int getNewsContentId() {
		return newsContentId;
	}

	public void setNewsContentId(int newsContentId) {
		this.newsContentId = newsContentId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNewsTypeROOTId() {
		return newsTypeROOTId;
	}

	public void setNewsTypeROOTId(int newsTypeROOTId) {
		this.newsTypeROOTId = newsTypeROOTId;
	}

	public String getNewsTypeTreeIds() {
		return newsTypeTreeIds;
	}

	public void setNewsTypeTreeIds(String newsTypeTreeIds) {
		this.newsTypeTreeIds = newsTypeTreeIds;
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

	public String getCanDel() {
		return canDel;
	}

	public void setCanDel(String canDel) {
		this.canDel = canDel;
	}
	
	
}
