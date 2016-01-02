package myFrameU.account.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Transient;

import myFrameU.util.commonUtil.json.JSONUtils;

public class AccountItem   implements Serializable{
	private int id;
	private String markedNum;// 编号
	private Account account;
	private String itemType;//

	public enum ITEMTYPE {
		RECHARGE, WITHDRAWALS, INCOME, SPENDING, FROZEN
	};// 充值、提现、收入、支出、冻结

	/**
	 * 等待、结束、关闭 充值：wait---[-->close]-->finish
	 * 提现：finish.到apply中去提交提现申请，ok之后直接插入一个finish的item
	 * 收入：[wait]-->[-->close]--->finish 支出：[wait]-->[-->close]--->finish。
	 * 冻结：finish--[--->close]。
	 */
	/**
	 * 一：买家竞拍产品 1、买家参加拍卖，【拍卖产品A1,竞拍纪录是B1,100元】 item1=new Item();
	 * item1.itemType=dongjie item1.status=finish item1.price=10
	 * item1.productId=A1 item1.paimaijilu=B1
	 * 2、买家第二次出价买这个产品【竞拍产品A1，竞拍纪录是B2,200元】 item2=new Item();
	 * item2.itemType=dongjie item2.status=finish item1.status=close
	 * 【检索规则：竞拍纪录B1，用户Id1】 3-1、买家最终没有买到这个产品 在拍卖结束的时候，退换，就是关闭冻结item
	 * item2.status=close 【检索规则：竞拍纪录B2，用户id1】 3-2、产品流拍，退还所有用户保证金
	 * 【检索规则：竞拍产品A1，所有的有效竞拍纪录（筛选出本期拍卖的竞拍纪录）】
	 * 3-3、买家最终买到了这个产品【竞拍产品A1，竞拍纪录B2,200元】【产品拍卖价格是1900元】
	 * 
	 * 
	 * 买到这个产品了之后，操作情况有： 1）什么都不做，一直拖到时间到了。
	 * 2）在时间内，去付款，发现账户里的钱不够拍卖的产品拍卖价格（包括了拍卖这个产品所冻结的price） 3）在时间内，去付款，账户里的钱够了。
	 * 
	 * 第一种情况： 关闭这个冻结，将被冻结的200元直接新生成一个支出item支付出去 item2.status=close item3=new
	 * Item(); item3.price=item2.price; item3.itemType=zhichu
	 * item3.status=finish item4=new Item(); item4.price=item2.price;
	 * item4.itemType=shouru item4.status=finish 【检索规则】
	 * 每天0点进行筛选竞拍纪录，筛选出中标的竞拍纪录来，判断每个竞拍纪录的状态，找出那些超过15日还没有付款的竞拍纪录来。
	 * 每个竞拍纪录对应一个itemId，从而找出这些iitemIds来
	 * 
	 * 第二种情况： 先判断账户里的钱够不够 如果不够，则不能关闭冻结，一直拖着。 【检索规则：本产品、本期拍卖、本次竞拍纪录】 第三种情况：
	 * 先判断账户里的钱够不够 如果够了，则关闭冻结，将被冻结的200元+1700（=1900）直接支付出去,分成两部分支付，一部分给商家一部分是平台
	 * item2.status=close item3=new Item(); item3.price=item2.price+700;
	 * item3.itemType=zhichu item3.status=finish item4=new Item();
	 * item4.price=(item2.price+1700)-平台提成; item4.itemType=shouru
	 * item4.status=finish
	 * 
	 * item5=new Item(); item5.price=平台提成 item5.itemType=shouru
	 * item5.status=finish
	 * 
	 * 二、买家|卖家充值 1、提交充值 item1=new Item(); item1.itemType=chongzhi
	 * item1.status=wait item1.price=2000 2、在真正充值付款之前，买家关闭了这个充值申请
	 * item1.status=close 3、去提交了真正的付款 item1.status=finish 三、买家|卖家申请提现
	 * 先去apply去申请提现，审批通过,审批通过的同时必须提现管理员，你先得给这个账户的支付宝打钱 item1=new Item();
	 * item1.itemType=tixian item1.status=finish 四、卖家提交店铺入驻申请
	 * 先判断账户里的钱够不够，如果不够，则提示想要入驻就必须先缴纳入住费2000元 如果够，则管理员审批通过 item1=new Item();
	 * item1.itemType=zhifu item1.status=finish 五、卖家提交举办展会|卖家提出要参加展会
	 * 先判断账户里的钱够不够，不够去充值 够了，则管理员审批通过 item1=new Item(); item1.itemType=dongjie
	 * item1.status=finish 六、卖家在展会结束之后 店铺级别+销售情况 1）店铺是A级的，展会结束之后，退还所有保证金
	 * item1.status=close 2）店铺是B级的 2-1）销售额度大于2000的，则退还所有保证金。 item1.status=close
	 * 2-2）销售额度小于2000的，则退还500元保证金。 item1.status=close item2=new Item();
	 * item2.itemType=zhichu item2.status=finish
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private String status;//

	public enum STATUS {
		WAIT, FINISH, CLOSE
	};

	
	
	private String priceType;
	public enum PRICETYPE{
		DEFAULT,XS,XC
	}
	/*private String rechargeType;
	public enum RECHARGETYPE{
		WX,CFT,ZFB
	}
	*/
	

	private String outerType;
	public enum OUTERTYPE{
		ADMIN,WEIXIN,ZHIFUBAO
	}
	//值得是微信的pre__id
	private String outerMarkedNum;
	//指的是微信的订单成功的订单号,那就这样，将2这个字段设定为所有外界的订单号，1那个是微信特有的一个中间值
	private String outerMarkedNum2;
	
	
	
	
	
	private float price;// 这次的费用。
	private String info;// 描述

	private Date relDate;
	private boolean callbackHaveddeal;// 回调的时候，这个ai是不是已经处理过了。

	/**
	 * 这个属性很重要 因为你不知道系统中哪些类需要和accountItem级联，比如这个项目中是竞拍明细ID和产品ID
	 * 那么这里的extraData就用一个json保存{'竞拍明细ID':'5','productId':'4'},这样在用一个map来取。
	 */
	private String extraData;// 额外的数据
	private HashMap<String, String> extraDataMap;

	public HashMap<String, String> getExtraDataMap() {
		return extraDataMap;
	}

	public enum EXTRADATA {
		auctionPeriodId, specialId, roleId
	};

	/**
	 * 这两个是从account继承下来
	 */
	private String whoclassName;
	private int whoId;// 可以是shopId也可以是userId

	/**
	 * 当时收入和支出的时候，这两个是一对儿的。以前的做法是markedNum相同，但是type不同。
	 * 现在markerdNum不同。所以需要一个匹配符字段 这个字段的生成方式很简单： 1）支出是一方，输入是一方，一对一匹配
	 * 【支出markedNum】【收入markedNum】 2）支出是一方，收入是两方，一方是shop，一方是admin
	 * 【支出markedNum】【shop收入markedNum】【admin收入markedNum】
	 */
	private String amatch;// 一个匹配符

	// =====================================================================


	// ==========================================================================

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarkedNum() {
		return markedNum;
	}

	public void setMarkedNum(String markedNum) {
		this.markedNum = markedNum;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public void setExtraDataMap(HashMap<String, String> extraDataMap) {
		this.extraDataMap = extraDataMap;
	}

	public Date getRelDate() {
		return relDate;
	}

	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}

	public boolean getCallbackHaveddeal() {
		return callbackHaveddeal;
	}

	public void setCallbackHaveddeal(boolean callbackHaveddeal) {
		this.callbackHaveddeal = callbackHaveddeal;
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

	public String getAmatch() {
		return amatch;
	}

	public void setAmatch(String amatch) {
		this.amatch = amatch;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getOuterType() {
		return outerType;
	}

	public void setOuterType(String outerType) {
		this.outerType = outerType;
	}

	public String getOuterMarkedNum() {
		return outerMarkedNum;
	}

	public void setOuterMarkedNum(String outerMarkedNum) {
		this.outerMarkedNum = outerMarkedNum;
	}

	public String getOuterMarkedNum2() {
		return outerMarkedNum2;
	}

	public void setOuterMarkedNum2(String outerMarkedNum2) {
		this.outerMarkedNum2 = outerMarkedNum2;
	}

	

}
