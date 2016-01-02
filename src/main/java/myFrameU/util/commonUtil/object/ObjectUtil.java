package myFrameU.util.commonUtil.object;

public class ObjectUtil {
	public static boolean same(Object old,String str){
		if(null==old && null==str){
			//双方都是null，
			return true;
		}else if(null!=old && null==str){
			//一个null，一个不为null
			return false;
		}else if(null==old && null!=str){
			//一个null，一个不为null
			return false;
		}else if(null!=old && null!=str){
			//都不为空，则将object转换为string
			String oldStr=old.toString();
			if(oldStr.equals(str)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}
