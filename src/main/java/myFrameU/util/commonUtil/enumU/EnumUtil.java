package myFrameU.util.commonUtil.enumU;

import java.lang.reflect.Method;

public class EnumUtil {
	public static boolean equalsE(Object enumValue,String str){
		try{
			Class c = enumValue.getClass();
			Method m = c.getDeclaredMethod("valueOf",String.class);
			if(enumValue.equals(m.invoke(enumValue, str))){
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}
}
