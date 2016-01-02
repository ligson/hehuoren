package myFrameU.sincerity.entity;

import java.util.Date;

public class SincerityArchivesItem {
	private int id;
	private String whoclassName;
	private String whopName;
	private int whoId;
	
	private int sincertityArchiversId;
	
	/**
	 * 1、买家付款后，卖家不发货
	 * 2、买家收货之后发现是假货
	 * 3、上传虚假信息
	 * 4、卖家与买家发生纠纷后，卖家态度恶劣。
	 * 5、卖家多次上传虚假认证申请
	 * 6、卖家遭买家用户举报投诉
	 */
	/*public enum SINCERITYTYPE{SHOP_NODeliverGoods,
		SHOP_FakeProduct,
		SHOP_FalseInfo,
		SHOP_BadAttitude,
		SHOP_FalseRenzheng,
		SHOP_BeComplaints
		};
	*/
	private int sincerityTypeId;
	private String addOrMis;
	public enum ADDORMIS{ADD,MIS};
	
	private float score;
	
	private String info;
	private String title;
	private Date relDate;
	
	private String extraData;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWhoclassName() {
		return whoclassName;
	}

	public void setWhoclassName(String whoclassName) {
		this.whoclassName = whoclassName;
	}

	public String getWhopName() {
		return whopName;
	}

	public void setWhopName(String whopName) {
		this.whopName = whopName;
	}

	public int getWhoId() {
		return whoId;
	}

	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}

	public int getSincertityArchiversId() {
		return sincertityArchiversId;
	}

	public void setSincertityArchiversId(int sincertityArchiversId) {
		this.sincertityArchiversId = sincertityArchiversId;
	}

	public int getSincerityTypeId() {
		return sincerityTypeId;
	}

	public void setSincerityTypeId(int sincerityTypeId) {
		this.sincerityTypeId = sincerityTypeId;
	}

	public String getAddOrMis() {
		return addOrMis;
	}

	public void setAddOrMis(String addOrMis) {
		this.addOrMis = addOrMis;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRelDate() {
		return relDate;
	}

	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	
}
