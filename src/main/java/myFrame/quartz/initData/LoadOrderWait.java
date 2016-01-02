package myFrame.quartz.initData;



import hhr.order.entity.Order;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;

public class LoadOrderWait extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		/**
		 * 找出所有以下订单：
		 * 1、已经下单，但是并没有选择是在线第三方支付还是自提付款的，即状态为WAIPAY	
		 * 2、已经下单，选择的是自提付款，但是仍未到自提点提货付款的，即状态未：PAYING
		 * 
		 * 注意，对于已经下单，且选择了微信支付，但是并没有去自提的即状态未PAYED的订单，不能自动关闭	
		 */
		
		List<Order> orderList = (List<Order>)aBiz.findObjectList(
				Order.class, 
				new Object[]{Order.STATUS.WAITPAY.toString(),Order.STATUS.PAYING.toString()}, 
				"from Order as o where o.status=? or o.status=?",
				null,
				false, 0, 0);
		
		uICacheManager.putObjectCached("web", "orderWait", (Serializable) orderList);
		
	}

	
	
	
	
	
	
	
}
