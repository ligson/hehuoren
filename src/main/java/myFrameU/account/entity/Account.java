package myFrameU.account.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Account   implements Serializable{
	private int id;
	private String whoclassName;
	private int whoId;//可以是shopId也可以是userId
	private String whoName;//shopName userName   adminName
	//账号信息
	private String zhifubao;//支付宝账号
	private String yinhangka;//银行卡账号
	private String yinhangkaType;//哪个行的
	private int isQueren;//商家是否确认了
	private Set<AccountItem> accountItemSet = new HashSet<AccountItem>();
	private String withdrawalsPwd;//提现密码
	
	/**
	 * 这三者有关系：totalPrice=frozenPrice+availablePrice
	 */
	private float totalPrice;//总的钱
	private float frozenPrice;//冻结的钱
	private float availablePrice;//可以使用的钱
	
	
	/**
	 * 可用资金包括：
	 * 		充值余额、销售佣金、宣传佣金的百分之十
	 * 冻结资金包括：
	 * 		待审核的提现申请资金、宣传佣金的百分之九十
	 */
	
	private float xcPrice;
	private float xsPrice;
	
	
	
	private int addressId;
	private String addressTreeIds;
	
	//=====================================
	public static final String adminAccountLock_pre="aaLock";
	public static final String shopAccountLock_pre="saLock";
	public static final String userAccountLock_pre="uaLock";
	
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
	public String getZhifubao() {
		return zhifubao;
	}
	public void setZhifubao(String zhifubao) {
		this.zhifubao = zhifubao;
	}
	public String getYinhangka() {
		return yinhangka;
	}
	public void setYinhangka(String yinhangka) {
		this.yinhangka = yinhangka;
	}
	public String getYinhangkaType() {
		return yinhangkaType;
	}
	public void setYinhangkaType(String yinhangkaType) {
		this.yinhangkaType = yinhangkaType;
	}
	public int getIsQueren() {
		return isQueren;
	}
	public void setIsQueren(int isQueren) {
		this.isQueren = isQueren;
	}
	
	public Set<AccountItem> getAccountItemSet() {
		return accountItemSet;
	}
	public void setAccountItemSet(Set<AccountItem> accountItemSet) {
		this.accountItemSet = accountItemSet;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public float getFrozenPrice() {
		return frozenPrice;
	}
	public void setFrozenPrice(float frozenPrice) {
		this.frozenPrice = frozenPrice;
	}
	public float getAvailablePrice() {
		return availablePrice;
	}
	public void setAvailablePrice(float availablePrice) {
		this.availablePrice = availablePrice;
	}
	public String getWithdrawalsPwd() {
		return withdrawalsPwd;
	}
	public void setWithdrawalsPwd(String withdrawalsPwd) {
		this.withdrawalsPwd = withdrawalsPwd;
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
	public float getXcPrice() {
		return xcPrice;
	}
	public void setXcPrice(float xcPrice) {
		this.xcPrice = xcPrice;
	}
	public float getXsPrice() {
		return xsPrice;
	}
	public void setXsPrice(float xsPrice) {
		this.xsPrice = xsPrice;
	}
	
	
	
	
}
