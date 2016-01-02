package myFrameU.sms.sdkN.entity;
//余额
public class YEEntity {
	/**
	 * <?xml version="1.0" encoding="utf-8" ?>
		 * 	<returnsms>
			 	<returnstatus>Sucess</returnstatus>
			 	<message></message>
			 	<payinfo>预付费</payinfo>
			 	<overage>25</overage>
			 	<sendTotal>320</sendTotal>
			</returnsms>
	 */
	private String returnstatus;
	private String message;
	private String payinfo;
	private String overage;
	private String sendTotal;
	public String getReturnstatus() {
		return returnstatus;
	}
	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPayinfo() {
		return payinfo;
	}
	public void setPayinfo(String payinfo) {
		this.payinfo = payinfo;
	}
	public String getOverage() {
		return overage;
	}
	public void setOverage(String overage) {
		this.overage = overage;
	}
	public String getSendTotal() {
		return sendTotal;
	}
	public void setSendTotal(String sendTotal) {
		this.sendTotal = sendTotal;
	}
	
	
}
