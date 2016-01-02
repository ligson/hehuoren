package myFrameU.integration.biz;

import myFrameU.biz.AbstractBizI;
import myFrameU.integration.entity.Integration;

public interface IntegrationBizI extends AbstractBizI {
	/**
	 * 添加积分。发生在创建店铺的时候，初始化这个店铺每种积分类型的账户。
	 * o:代表是要创建谁的积分。比如Shop(shop.id=1)对象
	 */
	public Integration addIntegration(Object who) throws Exception;
	
	
	/**
	 * 添加积分Item
	 */
	public void addIntegrationItem(Object who,int addOrMinus,float fraction,String itemEvent) throws Exception;
	
	
	
	
	
	
	
	
	
	
	
}
