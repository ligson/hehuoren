package myFrameU.integration.util;

import java.util.HashMap;
import java.util.Map;

import myFrameU.integration.biz.IntegrationBizI;
import myFrameU.integration.init.InitConfig;
import myFrameU.integration.init.ItemRule;

/**
 * 
 * 积分规则
 *
 */
public class IntegrationItemRuleUtil {
	/**
	 * 调用biz，addIntegrationItem(Object who, int addOrMinus, float fraction, String itemEvent)
	 * 所以这个方法主要是生成分数和说明
	 * 
	 * @param who  谁的积分
	 * @param actionType 规则名称
	 * @throws Exception
	 */
	public static void dealItem(Object who,String actionType,IntegrationBizI integrationBizI) throws Exception{
		String whoName=who.getClass().getName();
		InitConfig initConfig=myFrameU.integration.init.InitMavenImpl.ic;
		Map<String,HashMap<String,ItemRule>> itemRulesMap=initConfig.getItemRulesMap();
		if(null!=itemRulesMap){
			HashMap<String,ItemRule> rulesMap=itemRulesMap.get(whoName);
			if(null!=rulesMap){
				ItemRule rule=rulesMap.get(actionType);
				if(null!=rule){
					float fraction=rule.getFraction();
					int addOrMinus=0;
					if(fraction>0){
						addOrMinus=1;
					}else{
						addOrMinus=2;
					}
					String itemEvent=rule.getItemEvent();
					integrationBizI.addIntegrationItem(who, addOrMinus, fraction, itemEvent);
				}
			}
		}
	}
}




















