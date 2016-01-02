package myFrameU.weixin.pay.entity;
/**
 * 
 * jsapi支付时候需要给js一些数据
 *
 */
public class WxPayJSAPINeedEntity {
	/**
	 * 输出js支付所需要的参数字符串
	 * "appId" : "wx247c7e42bcde403d", //公众号名称，由商户传入     
		"timeStamp" : " 1395712654", //时间戳，自1970年以来的秒数     
		"nonceStr" : "e61463f8efa94090b1f366cccfbbb444", //随机串     
		"package" : "prepay_id=u802345jgfjsdfgsdg888",
		"signType" : "MD5", //微信签名方式:     
		"paySign" : "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名 
	 */
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String wpackage;//本来应该是package，但是是关键字，就前面加一个w
	private String signType;
	private String paySign;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getWpackage() {
		return wpackage;
	}
	public void setWpackage(String wpackage) {
		this.wpackage = wpackage;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	
	
	
}
