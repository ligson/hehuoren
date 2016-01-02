package myFrameU.account.biz;

import java.util.List;
import java.util.Map;

import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.pay.alipay.com.alipay.util.ZFBCallbackEntity;
public interface AccountBizI extends AbstractBizI {
	
	/**
	 * 充值
	 * @param account
	 * @param price
	 * @return
	 * @throws Exception
	 */
	public AccountItem rechage(Account account,float price,String status,String outerType) throws Exception;
	//支付宝回调
	public AccountItem alipayCallback(ZFBCallbackEntity zfbcbe) throws Exception;
	
	//============================以上的两个方法是集成支付宝，只有在充值的时候才会和支付宝打交道==========================================================
	
	
	//=============================以下方法都是本系统自己的，不会和外界支付宝联系==============================================================================
	//冻结
	public AccountItem forzen(Account account,float price,String extraData,String info) throws Exception;
	
	
	//提现
	public AccountItem account_withDrawals(AccountItem forzenAI_TX) throws Exception;
	public AccountItem withDrawals(String whoclassName,int whoId,Account account,float price) throws Exception;
	
	
	//支出和收入
	public AccountItem income(Account account,float price,String extraData,String status,String info) throws Exception;
	public AccountItem spending(Account account,float price,String extraData,String status,String info) throws Exception;
	//关闭ai
	public AccountItem closeAccountItem(AccountItem item) throws Exception;
	
	//产生交易的时候，就是收入支出的时候，是需要将支出的AI和收入的AI进行匹配
	public void matchAI(AccountItem spendingAI,AccountItem shopIncomeAI,AccountItem adminIncomeAI ) throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	//=================================
	public List<AccountItem> findAIs(String whoClassName,int whoId,String status,String itemType,String extra) throws Exception;
	
	public Account account_findAccount(String whoClassName,int whoId) throws Exception;
	
	
	//====================================================================
	
	//将冻结的部分拆分成支出
	//将forzenAI这条被冻结的ai的price，用支出的形式给shop的account
	public Map<String,AccountItem> forzen2Spending(AccountItem forzenAI,Account shopIncomeA,String spendingInfo,String incomeInfo) throws Exception;
	//交易
	//public Map<String,AccountItem> trade(float price,Account spendingAccount,Account shopIncomeAccount,Map<String,String> aiInfoMap) throws Exception;
	
	public Map<String,AccountItem> trade(float price, float tjrTCPrice,
			Account spendingAccount, Account tjrIncomeAccount,
			Account adminIncomeAccount,Map<String,String> aiInfoMap,Map<String,String> extraMap) throws Exception;
	
	
	//判断莫一个账户里的可用资金是否足够，第二个参数代表是否要计算进去某一个冻结的资金。第三个参数代表要支出的钱
	public boolean account_enoughAvailablePriceInAccount(Account account,float oneForzen,float spendingPrice) throws Exception;
	
	
	
	//==================================================
	public Account account_computingAccountPrice(Account account) throws Exception;
	public Account account_computingAccountPrice_test(Account account) throws Exception;
	
	//
	public AccountItem findAccountItemById(int accountItemId) throws Exception;
	
	
	//===================================================================
	//增加销售|宣传佣金
	public AccountItem income_xsxc(Account account,float price,String extraData,String status,String info,String priceType) throws Exception;
	
	public Map<String,AccountItem> trade_xsxc(float price, float tjrTCPrice,
			Account spendingAccount, Account tjrIncomeAccount,
			Account adminIncomeAccount,Map<String,String> aiInfoMap,Map<String,String> extraMap) throws Exception;
	public AccountItem spending_xsxc(Account account,float price,String extraData,String status,String info) throws Exception;	
}
