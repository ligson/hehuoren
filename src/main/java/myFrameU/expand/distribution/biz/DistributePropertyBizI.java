package myFrameU.expand.distribution.biz;

import java.util.HashMap;

import myFrameU.biz.AbstractBizI;
import myFrameU.expand.libraryProperty.entity.SystemLibraryProperty;

public interface DistributePropertyBizI extends AbstractBizI {
	public void addDistributePropertyBatch(
			HashMap<String, SystemLibraryProperty> libraryPropertyMap,
			String className, String dRange, String[] must,
			String[] propertyValueIds, String[] propertyIds) throws Exception;

	// 追加一个空的property
	public void addDistributeProperty(
			HashMap<String, SystemLibraryProperty> libraryPropertyMap,
			String className, String dRange, int propertyId) throws Exception;

	// 取消一个分配
	public void delDistributeProperty(int propertyId, String className,
			String dRange) throws Exception;

	// 为一个pd追加一个值|取消一个值
	public void modifyDistributePropertyValue(
			HashMap<String, SystemLibraryProperty> libraryPropertyMap,
			String className, String dRange, int propertyValueId) throws Exception;

}
