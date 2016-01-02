package myFrameU.account.util;

import myFrame.cache.CacheKey;
import myFrameU.account.entity.Account;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.global.entity.Global;

public class AccountUtilHHR {

	/**
	 * 计算可用的宣传金额
	 */
	public static float getAvailable_xcPrice(UICacheManager uICacheManager,float xcPrice,float xsPrice){
		float xcPrice_keyong=0f;
		float xcPrice_forzen=0f;
		
		if(xsPrice>0){
			Global g20=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 20);
			if(null!=g20){
				String g20Value = g20.getMyValue();
				if(null!=g20Value && !g20Value.equals("")){
					float g20ValueFloat = new Float(g20Value).floatValue();
					if(g20ValueFloat>0){
						float xsPrice_baifenbi=(xsPrice*g20ValueFloat)/100;
						/**
						 * 我的宣传佣金是100元
						 * 
						 * 1、我的销售佣金是60元，我的可用宣传佣金是6元，我的总的可用佣金是66元
						 * 2、我的销售佣金是600元，我的可用宣传佣金是60元，我的总的可用佣金是660元
						 * 3、我的小手佣金是6000元，我的可用宣传佣金是600元（错误，不能大于100元，因为你总的宣传佣金就100元），我的宣传佣金是100元，则我的总的可用佣金是6100元
						 */
						if(xsPrice_baifenbi<=xcPrice){
							//第一，第二种情况
							xcPrice_keyong=xsPrice_baifenbi;
							xcPrice_forzen=xcPrice-xcPrice_keyong;
						}else{
							//第三种情况
							xcPrice_keyong=xcPrice;
							xcPrice_forzen=0;
						}
					}else{
						//==0
						// 全部的宣传佣金都是冻结的
						xcPrice_forzen=xcPrice;
					}
				}
			}
		}else{
			xcPrice_forzen=xcPrice;
		}
		return xcPrice_keyong;
	}
	
	/**
	 * 可用的宣传佣金：
	 * 		最多不会超过销售佣金的10%
	 */
	public static float getAvailable_xcPrice(UICacheManager uICacheManager,Account tjrAccount){

		float xcPrice=tjrAccount.getXcPrice();
		//根据推荐人He的销售佣金来计算
		float xcPrice_keyong=0f;
		float xcPrice_forzen=0f;
		float xsPrice = tjrAccount.getXsPrice();
		if(xsPrice>0){
			Global g20=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 20);
			if(null!=g20){
				String g20Value = g20.getMyValue();
				if(null!=g20Value && !g20Value.equals("")){
					float g20ValueFloat = new Float(g20Value).floatValue();
					if(g20ValueFloat>0){
						float xsPrice_baifenbi=(xsPrice*g20ValueFloat)/100;
						/**
						 * 我的宣传佣金是100元
						 * 
						 * 1、我的销售佣金是60元，我的可用宣传佣金是6元，我的总的可用佣金是66元
						 * 2、我的销售佣金是600元，我的可用宣传佣金是60元，我的总的可用佣金是660元
						 * 3、我的小手佣金是6000元，我的可用宣传佣金是600元（错误，不能大于100元，因为你总的宣传佣金就100元），我的宣传佣金是100元，则我的总的可用佣金是6100元
						 */
						if(xsPrice_baifenbi<=xcPrice){
							//第一，第二种情况
							xcPrice_keyong=xsPrice_baifenbi;
							xcPrice_forzen=xcPrice-xcPrice_keyong;
						}else{
							//第三种情况
							xcPrice_keyong=xcPrice;
							xcPrice_forzen=0;
						}
					}else{
						//==0
						// 全部的宣传佣金都是冻结的
						xcPrice_forzen=xcPrice;
					}
				}
			}
		}else{
			//=0
			//全部的宣传佣金都是冻结的
			xcPrice_forzen=xcPrice;
		}
		//将宣传资金拆分成两部分
		return xcPrice_keyong;
	}
}
