package myFrameU.shop.entity;

import java.io.Serializable;

/**
 * 
 * 店铺认证
 *
 */
public class Certification implements Serializable {
	private int id;
	private String whoclassName;
	private int whoId;
	private String whoName;
	// 营业执照、组织机构代码证、税务登记证
	private String yingyezhizhaoUrl;// 营业执照图片
	private String yingyezhizhaoHaoma;// 营业执照号码
	private String zuzhijigoudaimazheng;// 组织机构代码证
	private String shuiwudengjizheng;// 税务登记证

	// 拍卖证
	private String paimaixukezheng;// 拍卖许可证
	private String wenwupmxkzheng;// 文物拍卖许可证

	// 身份证
	private String shenfenzhengzUrl;
	private String shenfenzhengfUrl;
	private String shenfenzhenghaoma;// 身份证号码
	private String trueName;//真实姓名
	
	
	//分组，分成2租，1组是资质证件审核，一组是实名认证
	private String zizhiStatus;
	private String shimingStatus;
	//如果审核不通过，则回滚到wait,所有没有APPLYERROR
	public enum STATUS{WAIT,WAITAPPLY,APPLYOK};
	
	private String yuanyinzizhi;
	private String yuanyinshiming;
	
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
	public int getWhoId() {
		return whoId;
	}
	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}
	public String getWhoName() {
		return whoName;
	}
	public void setWhoName(String whoName) {
		this.whoName = whoName;
	}
	public String getYingyezhizhaoUrl() {
		return yingyezhizhaoUrl;
	}
	public void setYingyezhizhaoUrl(String yingyezhizhaoUrl) {
		this.yingyezhizhaoUrl = yingyezhizhaoUrl;
	}
	public String getYingyezhizhaoHaoma() {
		return yingyezhizhaoHaoma;
	}
	public void setYingyezhizhaoHaoma(String yingyezhizhaoHaoma) {
		this.yingyezhizhaoHaoma = yingyezhizhaoHaoma;
	}
	public String getZuzhijigoudaimazheng() {
		return zuzhijigoudaimazheng;
	}
	public void setZuzhijigoudaimazheng(String zuzhijigoudaimazheng) {
		this.zuzhijigoudaimazheng = zuzhijigoudaimazheng;
	}
	public String getShuiwudengjizheng() {
		return shuiwudengjizheng;
	}
	public void setShuiwudengjizheng(String shuiwudengjizheng) {
		this.shuiwudengjizheng = shuiwudengjizheng;
	}
	public String getPaimaixukezheng() {
		return paimaixukezheng;
	}
	public void setPaimaixukezheng(String paimaixukezheng) {
		this.paimaixukezheng = paimaixukezheng;
	}
	public String getWenwupmxkzheng() {
		return wenwupmxkzheng;
	}
	public void setWenwupmxkzheng(String wenwupmxkzheng) {
		this.wenwupmxkzheng = wenwupmxkzheng;
	}
	public String getShenfenzhengzUrl() {
		return shenfenzhengzUrl;
	}
	public void setShenfenzhengzUrl(String shenfenzhengzUrl) {
		this.shenfenzhengzUrl = shenfenzhengzUrl;
	}
	public String getShenfenzhengfUrl() {
		return shenfenzhengfUrl;
	}
	public void setShenfenzhengfUrl(String shenfenzhengfUrl) {
		this.shenfenzhengfUrl = shenfenzhengfUrl;
	}
	public String getShenfenzhenghaoma() {
		return shenfenzhenghaoma;
	}
	public void setShenfenzhenghaoma(String shenfenzhenghaoma) {
		this.shenfenzhenghaoma = shenfenzhenghaoma;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getZizhiStatus() {
		return zizhiStatus;
	}
	public void setZizhiStatus(String zizhiStatus) {
		this.zizhiStatus = zizhiStatus;
	}
	public String getShimingStatus() {
		return shimingStatus;
	}
	public void setShimingStatus(String shimingStatus) {
		this.shimingStatus = shimingStatus;
	}
	public String getYuanyinzizhi() {
		return yuanyinzizhi;
	}
	public void setYuanyinzizhi(String yuanyinzizhi) {
		this.yuanyinzizhi = yuanyinzizhi;
	}
	public String getYuanyinshiming() {
		return yuanyinshiming;
	}
	public void setYuanyinshiming(String yuanyinshiming) {
		this.yuanyinshiming = yuanyinshiming;
	}
	

}
