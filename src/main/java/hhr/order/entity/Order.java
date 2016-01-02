package hhr.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 
 */
public class Order   implements Serializable{
	private int id;
	private String markedNum;
	private int tuijianRenId;
	private String tujianRenName;
	private int userId;
	private String userName;
	private int myAddressId;//我的地址,暂时没有用，因为是自提的，但是作为扩展。
	
	
	private int totalCount;
	private float totalPrice;//付款金额
	private String status;//关闭订单，等待付款-->成功付款--->已自提
	//paying代表要货到付款
	public enum STATUS{CLOSE,WAITPAY,PAYED,PAYING,PICKUPED}
	
	private Date createDate;
	private Date payDate;
	private Date pickDate;//自提日期
	

	
	
	//======================
	//如何计算各自的提成,global里有提成。
	private float toHehuorenPrice;//给他的推荐人（合伙人）多少钱
	private float toWebPrice;//给平台多少钱。
	
	
	//===============================
	private String remarks;//用户自己填写的

	
	
	
	private Set<OrderItem> oiSet;
	
	
	
	//收货人电话姓名
	private String shouhuoTelphone;
	private String shouhuoName;
	
	
	
	//================================================
	
	private String orderPayType;//两种，一种是自提付款，不走线上。二是走线上就是outerType
	public enum ORDERPAYTYPE{
		Unknown,outerType,Huodaofukuan
	}
	
	//从账户支出的，支付宝，微信
	private String outerType;
	public enum OUTERTYPE{
		ACCOUNT,ZHIFUBAO,WEIXIN
	}
	private String outerMarkedNum;
	private String outerMarkedNum1;
	

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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getPayDate() {
		return payDate;
	}


	public void setPayDate(Date payDate) {
		this.payDate = payDate;
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


	public String getMarkedNum() {
		return markedNum;
	}


	public void setMarkedNum(String markedNum) {
		this.markedNum = markedNum;
	}


	public String getTujianRenName() {
		return tujianRenName;
	}


	public void setTujianRenName(String tujianRenName) {
		this.tujianRenName = tujianRenName;
	}



	

	public Set<OrderItem> getOiSet() {
		return oiSet;
	}


	public void setOiSet(Set<OrderItem> oiSet) {
		this.oiSet = oiSet;
	}


	public Date getPickDate() {
		return pickDate;
	}


	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
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


	public String getOuterMarkedNum() {
		return outerMarkedNum;
	}


	public void setOuterMarkedNum(String outerMarkedNum) {
		this.outerMarkedNum = outerMarkedNum;
	}


	public String getOuterMarkedNum1() {
		return outerMarkedNum1;
	}


	public void setOuterMarkedNum1(String outerMarkedNum1) {
		this.outerMarkedNum1 = outerMarkedNum1;
	}


	public String getOuterType() {
		return outerType;
	}


	public void setOuterType(String outerType) {
		this.outerType = outerType;
	}


	public String getOrderPayType() {
		return orderPayType;
	}


	public void setOrderPayType(String orderPayType) {
		this.orderPayType = orderPayType;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
