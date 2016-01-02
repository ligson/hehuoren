package myFrameU.user.entity;

import java.io.Serializable;
import java.util.Date;

import myFrameU.address.entity.Address;

public class User  implements Serializable{
	private int id;
	
	/**
	 * 1、1代表的是普通客户。
	 * 2、2代表的是成为试用期的推荐人
	 * 3、3代表成为真正的推荐人
	 */
	private int userLevelId;
	
	
	private int tuijianRenId;//我的上级推荐人是谁，默认为0
	private String tuijianRenNicheng;
	/**
	 * 如果试用期过了
	 * 		试用期期间，试用期的佣金（宣传佣金+销售佣金）不可申请提现。
	 * 		1、通过，好办
	 * 		2、不通过
	 * 			1、清空粉丝数量
	 * 			2、清空那些粉丝的推荐人ID
	 * 			3、清空我的宣传佣金和销售佣金。
	 * 			4、对于我的粉丝们已经通过我这个试用期推荐人下的订单，照常通过合伙人价格购买，不产生影响。	
	 * 			
	 *
	 *	对于销售佣金和宣传佣金如何体现在财务系统中？
	 *	1、销售佣金直接便是可用资金
	 *	2、宣传佣金，宣传佣金中的销售佣金的10%为可用资金，90%为冻结资金（即不可使用（提现+使用）的资金）
	 *	3、每次我的粉丝进行购买，则重新计算我账户的可用资金和冻结资金
	 */
	private int fensiCount;//粉丝数量
	private Date beProbationDate;//成为试用期的日期
	private Date beDate;//成为正式合伙人的日期
	
	
	
	private String my2wm;//我的二维码
	
	
	
	private String ifNosubscribe_subscribeEvent;
	
	
	
	private String wxId;
	private String bianhao;//编号
	private String name;//20
	private String nicheng;//昵称---姓名
	private String password;//20
	
	
	private String telPhone;//20.电话
	private String phone;//固话
	private String email;//50.邮箱
	private String touxiang;//头像
	private String qq;
	private String msn;
	
	private String zhifubao;//支付宝账号
	
	
	
	private int sex;//1代表男，2代表女
	private int age;//年龄
	private String addressTreeIds;
	private int addressId;
	
	
	
	private Date zhuceDate;//注册时间
	private Date chushengDate;//出生日期
	private String zhuceIp;
	private String selfInfo;//自我介绍,为真实姓名
	private String status;//状态，用来管理员冻结这个账户
	public enum STATUS{OK,FORZEN}
	
	
	
	
	
	private float totalScore;//总分,总分与等级有这制度关系
	private int grade;//等级。日后可能会有用.等级用来显示钻石还是皇冠的.
	
	
	
	private String userWw;
	
	private String certiStatus;//认证状态,等待认证
	//wait代表的是certification是wait、waitally
	public enum CERTISTATUS{WAIT,OK}
	
	
	public Date getChushengDate() {
		return chushengDate;
	}
	public void setChushengDate(Date chushengDate) {
		this.chushengDate = chushengDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBianhao() {
		return bianhao;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNicheng() {
		return nicheng;
	}
	public void setNicheng(String nicheng) {
		this.nicheng = nicheng;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTouxiang() {
		return touxiang;
	}
	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public Date getZhuceDate() {
		return zhuceDate;
	}
	public void setZhuceDate(Date zhuceDate) {
		this.zhuceDate = zhuceDate;
	}
	public String getZhuceIp() {
		return zhuceIp;
	}
	public void setZhuceIp(String zhuceIp) {
		this.zhuceIp = zhuceIp;
	}
	public String getSelfInfo() {
		return selfInfo;
	}
	public void setSelfInfo(String selfInfo) {
		this.selfInfo = selfInfo;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getZhifubao() {
		return zhifubao;
	}
	public void setZhifubao(String zhifubao) {
		this.zhifubao = zhifubao;
	}
	public float getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(float totalScore) {
		this.totalScore = totalScore;
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getUserWw() {
		return userWw;
	}
	public void setUserWw(String userWw) {
		this.userWw = userWw;
	}
	public String getAddressTreeIds() {
		return addressTreeIds;
	}
	public void setAddressTreeIds(String addressTreeIds) {
		this.addressTreeIds = addressTreeIds;
	}
	public int getUserLevelId() {
		return userLevelId;
	}
	public void setUserLevelId(int userLevelId) {
		this.userLevelId = userLevelId;
	}
	public String getCertiStatus() {
		return certiStatus;
	}
	public void setCertiStatus(String certiStatus) {
		this.certiStatus = certiStatus;
	}
	public int getTuijianRenId() {
		return tuijianRenId;
	}
	public void setTuijianRenId(int tuijianRenId) {
		this.tuijianRenId = tuijianRenId;
	}
	public int getFensiCount() {
		return fensiCount;
	}
	public void setFensiCount(int fensiCount) {
		this.fensiCount = fensiCount;
	}
	public Date getBeProbationDate() {
		return beProbationDate;
	}
	public void setBeProbationDate(Date beProbationDate) {
		this.beProbationDate = beProbationDate;
	}
	public Date getBeDate() {
		return beDate;
	}
	public void setBeDate(Date beDate) {
		this.beDate = beDate;
	}
	public String getMy2wm() {
		return my2wm;
	}
	public void setMy2wm(String my2wm) {
		this.my2wm = my2wm;
	}
	public String getWxId() {
		return wxId;
	}
	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public String getTuijianRenNicheng() {
		return tuijianRenNicheng;
	}
	public void setTuijianRenNicheng(String tuijianRenNicheng) {
		this.tuijianRenNicheng = tuijianRenNicheng;
	}
	public String getIfNosubscribe_subscribeEvent() {
		return ifNosubscribe_subscribeEvent;
	}
	public void setIfNosubscribe_subscribeEvent(String ifNosubscribe_subscribeEvent) {
		this.ifNosubscribe_subscribeEvent = ifNosubscribe_subscribeEvent;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
