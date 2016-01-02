package myFrameU.product.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myFrame.cache.CacheKey;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.expand.distribution.entity.PropertyDistribute;
import myFrameU.expand.use.util.ExpandPropertyUseUtil;
import myFrameU.product.entity.Product;
import myFrameU.product.entity.ProductType;

public class ProductTypeUtil {
	//处理allName，当name修改了之后，要级联修改自己的allName
	//allName之间都是用-隔开的。玉石-好玉石-特级玉石
	/**
	 * @param oldAllName  原来老的allName
	 * @param oldName		原来老的name
	 * @param newName		现在新的name
	 * @return
	 */
	public static String getNewAllName(String oldAllName,String oldName,String newName){
		StringBuffer sb = new StringBuffer("");
		String[] oldAllNameArray=oldAllName.split("-");
		int len=oldAllNameArray.length;
		String everyName=null;
		for(int i=0;i<len;i++){
			everyName=oldAllNameArray[i];
			if(everyName.equals(oldName)){
				sb.append(newName).append("-");
			}else{
				sb.append(everyName).append("-");
			}
		}
		String s = sb.toString();
		if(null!=s && !s.equals("")){
			if(s.endsWith("-")){
				s=s.substring(0,s.length()-1);
			}
		}
		return s;
	}
	
	
	
	
	//获取pt的扩展属性
	public static HashMap<String,List<PropertyDistribute>> getPropertyDistributesList(UICacheManager uICacheManager,ProductType pt){
		HashMap<String,List<PropertyDistribute>> map = new HashMap<String,List<PropertyDistribute>>();
		if(null!=pt){
			int productTypeId=pt.getId();
			int isLeaf=pt.getIsLeaf();
			int isROOT=pt.getIsROOT();
			if(isROOT==0){
				//跟目录
				int rootId=pt.getRootTypeId();
				String dRange="{'key':'productTypeId','value':'"+rootId+"'}";
				List<PropertyDistribute> list_root = ExpandPropertyUseUtil.getPropertyDistributes(Product.class.getName(), dRange, uICacheManager);
				map.put("propertyDistributeList_root", list_root);
			}else{
				//
				if(isLeaf==1){
					int rootId=pt.getRootTypeId();
					String dRange="{'key':'productTypeId','value':'"+rootId+"'}";
					List<PropertyDistribute> list_root = ExpandPropertyUseUtil.getPropertyDistributes(Product.class.getName(), dRange, uICacheManager);
					map.put("propertyDistributeList_root", list_root);
					
					String dRangeS="{'key':'productTypeId','value':'"+productTypeId+"'}";
					List<PropertyDistribute> list_self = ExpandPropertyUseUtil.getPropertyDistributes(Product.class.getName(), dRangeS, uICacheManager);
					map.put("propertyDistributeList_self", list_self);
				}
			}
		}
		return map;
		
	}
	
	
	
	//获取这些p的对应的pt
	public static Map<String,ProductType> getPtMap(String pIds,AbstractBizI aBiz,UICacheManager uICacheManager) throws Exception{
		HashMap<String,ProductType> ptMap = CacheKey.CKProductType.ALLMAP.getMap(uICacheManager);
		 Map<String,ProductType> map=new HashMap<String,ProductType>();
		 String pId_str = "productId_";
		 if(null!=pIds){
			 String pId_ptIds=(String)aBiz.j_queryObject("select group_concat(convert(p.id,char),'-',convert(p.productTypeId,char)) from product_product as p where p.id in("+pIds+")", null);
			 if(null!=pId_ptIds){
				 String[] array=pId_ptIds.split(",");
				 if(null!=array){
					 int len=array.length;
					 String everyPIdPTId=null;
					 for(int i=0;i<len;i++){
						 everyPIdPTId=array[i];
						 String[] pptArray=everyPIdPTId.split("-");
						 if(null!=pptArray){
							 map.put(pId_str+pptArray[0], ptMap.get("productTypeId_"+pptArray[1]));
						 }
					 }
				 }
			 }
		 }
		return map;
	}
	
	
	
	
	
	//将传入的shop的mainProductTypeIds[][][][]解析成List<ProductType>
	public static List<ProductType> getPTList_from_mainProductTypeIds(UICacheManager uICacheManager,String mainProductTypeIds){
		List<ProductType> list = null;
		HashMap<String,ProductType> map = CacheKey.CKProductType.ALLMAP.getMap(uICacheManager);
		if(null!=mainProductTypeIds && !mainProductTypeIds.equals("")){
			list = new ArrayList<ProductType>();
			mainProductTypeIds=mainProductTypeIds.substring(1,mainProductTypeIds.length()-1);
			if(!mainProductTypeIds.contains("][")){
				ProductType pt = map.get("productTypeId_"+mainProductTypeIds);
				if(null!=pt){
					list.add(pt);
				}
			}else{
				String[] mptidsArray=mainProductTypeIds.split("]\\[");
				if(null!=mptidsArray){
					int len = mptidsArray.length;
					String ptIdStr = null;
					ProductType pt = null;
					for(int i=0;i<len;i++){
						ptIdStr=mptidsArray[i];
						pt = map.get("productTypeId_"+ptIdStr);
						if(null!=pt){
							list.add(pt);
						}
					}
				}
			}
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
}
