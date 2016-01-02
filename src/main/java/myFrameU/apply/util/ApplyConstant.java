package myFrameU.apply.util;

import java.util.Map;

import myFrameU.exception.exception.MyApplyException;
import myFrameU.util.commonUtil.enumU.EnumUtil;


public class ApplyConstant {
	public enum APPLYTYPEKEY{
		AccountTXApply,UserLevelHHR
	}
	
	public static String getExtraData(String applyTypeKey,Map<String, String> argsMap) throws Exception{
		String extra=null;
		if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.AccountTXApply, applyTypeKey)){
			//提现
			String xcPrice = argsMap.get("xcPrice");
			String xsPrice = argsMap.get("xsPrice");
			if(null==xcPrice || xcPrice.equals("")){
				throw new MyApplyException("xcPrice必须传递");
			}
			if(null==xsPrice || xsPrice.equals("")){
				throw new MyApplyException("xsPrice必须传递");
			}
			
			
			
			
			String accountId = argsMap.get("accountId");
			String accountItemId = argsMap.get("accountItemId");
			String roleId = argsMap.get("roleId");
			if(null==accountId || accountId.equals("") || accountId.equals("0")){
				throw new MyApplyException("accountId必须传递");
			}
			if(null==accountItemId || accountItemId.equals("")  || accountItemId.equals("0")){
				throw new MyApplyException("accountItemId必须传递");
			}
			if(null==roleId || roleId.equals("")  || roleId.equals("0")){
				throw new MyApplyException("roleId必须传递");
			}
			extra="{'accountId':'"+accountId+"','accountItemId':'"+accountItemId+"','roleId':'"+roleId+"','xcPrice':'"+xcPrice+"','xsPrice':'"+xsPrice+"'}";
		}else if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
		}
		return extra;
	}
	
}
