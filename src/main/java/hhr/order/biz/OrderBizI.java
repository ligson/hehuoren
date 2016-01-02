package hhr.order.biz;

import hhr.order.entity.Order;
import myFrameU.account.biz.AccountBizI;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.user.entity.User;

public interface OrderBizI extends AbstractBizI {
	public void addOrder(Order o) throws Exception;
	
	//从账户支付
	public User payOrder(User user,Order o,AccountBizI aBiz, UICacheManager uICacheManager) throws Exception;
	
	//微信\支付宝直接支付,从第三方
	public User payOrder_3(Order o,AccountBizI aBiz, UICacheManager uICacheManager) throws Exception;
	
	public User zitifukuan_picked(Order o,AccountBizI aBiz, UICacheManager uICacheManager) throws Exception;
	
	
	public void closeOrder(Order o) throws Exception;
	
	
}
