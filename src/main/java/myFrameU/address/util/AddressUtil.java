package myFrameU.address.util;

import java.lang.reflect.Method;
import java.util.HashMap;

import myFrameU.address.entity.Address;
import myFrameU.ehcache.util.UICacheManager;

public class AddressUtil {
	
	/**
	 * 前提是两者双方都是addressId和addressTreeIds
	 * @param to 要被赋值address对象
	 * @param from 从from对象里拿到address
	 * @return 返回赋值成功的to
	 */
	public static Object setAddress(Object to,Object from) throws Exception{
		Class fromClass=from.getClass();
		Class toClass=to.getClass();
		
		Method from_m1 = fromClass.getDeclaredMethod("getAddressId");
		int addressId=(Integer)from_m1.invoke(from, null);
		
		Method from_m2 = fromClass.getDeclaredMethod("getAddressTreeIds");
		String addressTreeIds=(String)from_m2.invoke(from, null);
		
		Method to_m1 = toClass.getDeclaredMethod("setAddressId",Integer.class);
		to_m1.invoke(to, addressId);
		
		Method to_m2 = toClass.getDeclaredMethod("setAddressTreeIds",Integer.class);
		to_m2.invoke(to, addressTreeIds);
		
		
		return to;
	}
	
	//传入addressId，将entity的addressId和addressTreeIds填充
	public static Object fillAddress(UICacheManager uICacheManager,Object o,int addressId) throws Exception{
		HashMap<String,Address> addMap = (HashMap<String,Address>)uICacheManager.getObjectCached("web", "addressAll_map");
		if(null!=addMap){
			Address add=addMap.get("addId_"+addressId);
			if(null!=add){
				Class c = o.getClass();
				Method m1=c.getDeclaredMethod("setAddressId", int.class);
				m1.invoke(o, addressId);
				Method m2=c.getDeclaredMethod("setAddressTreeIds", String.class);
				m2.invoke(o, add.getTreeId());
			}
		}
		return o;
	}
	
	
	
	
	
	
	
	
	
}
