package myFrameU.expand.libraryProperty.biz;

import myFrameU.biz.AbstractBizI;
import myFrameU.expand.libraryProperty.entity.SystemLibraryProperty;

public interface SystemLibraryPropertyBizI extends AbstractBizI {
	public String addSystemLibraryProperty(SystemLibraryProperty sysLibraryProperty) throws Exception;
	
	public String modifySLProperty(SystemLibraryProperty oldP) throws Exception;
	
	
	public String deleteSystemLibraryProperty(SystemLibraryProperty sysLibraryProperty) throws Exception;
	
	//为p追加多个pv
	public String addSLPropertyValues(SystemLibraryProperty systemLibraryProperty,String values) throws Exception;
	
	//修改
	public String modifySLPropertyValues(int id,String newPValue,boolean defaultValue,String modifyValueOrDefault) throws Exception;
	
}
