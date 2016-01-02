package myFrameU.pay.alipay.com.alipay.util;
/**
 * 
responseTxt=true
 isSign=true
 返回回来的参数：
 	body=请赐教 - 全国优秀的培训资源都在这里&
 	buyer_email=13614036650&
 	buyer_id=2088702508039462&
 	discount=0.00&
 	gmt_create=2013-11-14 21:10:00&
 	gmt_logistics_modify=2013-11-14 21:10:00&
 	gmt_payment=2013-11-14 21:10:36&
 	is_total_fee_adjust=N&
 	logistics_fee=0.00&
 	logistics_payment=SELLER_PAY&
 	logistics_type=EXPRESS&
 	notify_id=7895f0d9d433a4eb82399693d4cedd584k&
 	notify_time=2013-11-14 21:10:36&
 	notify_type=trade_status_sync&
 	out_trade_no=1384434366964&
 	payment_type=1&
 	price=0.01&
 	quantity=1&
 	receive_address=辽宁省 沈阳市-大东区-大悦城&
 	receive_mobile=13614036650&
 	receive_name=xuxianlin&
 	receive_phone=024-24363199&
 	receive_zip=100000&
 	seller_email=qingcijiao@126.com&
 	seller_id=2088111299065736&
 	sign=aa45cecfd78ddc2775a652c0f20e9045&
 	sign_type=MD5&
 	subject=请赐教培训官网订单&
 	total_fee=0.01&
 	trade_no=2013111430363846&
 	trade_status=WAIT_SELLER_SEND_GOODS&
 	use_coupon=N
 *
 */
public class ZFBCallbackEntity {
	private String buyer_email;
	private String buyer_id;
	private String gmt_payment;
	private String total_fee;
	private String quantity;
	private String trade_status;//支付宝的这个单子的状态
	private String out_trade_no;//我们自己的编号
	private String trade_no;//支付宝自己的编号
	public String getBuyer_email() {
		return buyer_email;
	}
	public void setBuyer_email(String buyerEmail) {
		buyer_email = buyerEmail;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyerId) {
		buyer_id = buyerId;
	}
	public String getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(String gmtPayment) {
		gmt_payment = gmtPayment;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String totalFee) {
		total_fee = totalFee;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String tradeStatus) {
		trade_status = tradeStatus;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String tradeNo) {
		trade_no = tradeNo;
	}
	
	
}
