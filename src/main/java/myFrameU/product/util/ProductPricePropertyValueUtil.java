package myFrameU.product.util;

import java.util.ArrayList;
import java.util.List;

import myFrameU.biz.AbstractBizI;
import myFrameU.product.entity.ProductPrice;
import myFrameU.product.entity.ProductPricePropertyValue;

public class ProductPricePropertyValueUtil {
	
	
	/**
	 * 查询一个product的所有productPricePropertyValue
	 */
	public static List<ProductPricePropertyValue> getPPPVList(int productId,AbstractBizI aBiz) throws Exception{
		List<ProductPricePropertyValue> pppvList = (List<ProductPricePropertyValue>)
				aBiz.findObjectList(ProductPricePropertyValue.class, 
						new Object[]{productId}, 
						"from ProductPricePropertyValue as pppv where pppv.productId=?",
						null, 
						false, 
						0, 
						0);
		
		/**
		 * 完善noSave_keyValues
		 */
		if(null!=pppvList){
			int size = pppvList.size();
			String keyValues = null;
			String[] keyValuesArray = null;
			ProductPricePropertyValue pppv = null;
			List<String> noSave_keyValues = null;
			for(int i=0;i<size;i++){
				pppv=pppvList.get(i);
				noSave_keyValues=new ArrayList<String>();
				keyValues = pppv.getKeyValues();
				if(null!=keyValues && !keyValues.equals("")){
					keyValuesArray=keyValues.split(",");
					if(null!=keyValuesArray){
						int len = keyValuesArray.length;
						for(int j=0;j<len;j++){
							noSave_keyValues.add(keyValuesArray[j]);
						}
						pppv.setNoSave_keyValues(noSave_keyValues);
					}
				}
			}
		}
		return pppvList;
	}
	
	
	
	
	
	/**
	 * 重新查询一个product所有的productPrice，重新更新product对应的三个propertyValue对象的值
	 */
	public static void againUpdatePPPVs(int productId,AbstractBizI aBiz) throws Exception{
		List<ProductPrice> ppList = (List<ProductPrice>)aBiz.findObjectList(
				ProductPrice.class, new Object[]{productId}, "from ProductPrice as pp where pp.productId=?", null, false, 0, 0);
		if(null!=ppList){
			List<String> xilieNamesList = new ArrayList<String>();
			List<String> baozhuangNamesList = new ArrayList<String>();
			List<String> shiyongNamesList = new ArrayList<String>();
			
			
			int size_ppList = ppList.size();
			ProductPrice pp = null;
			String pp_xilieName=null;
			String pp_baozhuangName=null;
			String pp_shiyongName = null;
			for(int i=0;i<size_ppList;i++){
				pp=ppList.get(i);
				pp_xilieName = pp.getXilieName();
				pp_baozhuangName = pp.getBaozhuangName();
				pp_shiyongName = pp.getShiyongName();
				if(!xilieNamesList.contains(pp_xilieName)){
					xilieNamesList.add(pp_xilieName);
				}
				
				if(!baozhuangNamesList.contains(pp_baozhuangName)){
					baozhuangNamesList.add(pp_baozhuangName);
				}
				
				if(!shiyongNamesList.contains(pp_shiyongName)){
					shiyongNamesList.add(pp_shiyongName);
				}
				
			}
			
			String xilieNameValues = list2String(xilieNamesList);
			String baozhuangNameValues = list2String(baozhuangNamesList);
			String shiyongNameValues = list2String(shiyongNamesList);
			
			List<ProductPricePropertyValue> pppvList = (List<ProductPricePropertyValue>)
					aBiz.findObjectList(ProductPricePropertyValue.class, 
							new Object[]{productId}, 
							"from ProductPricePropertyValue as pppv where pppv.productId=?",
							null, 
							false, 
							0, 
							0);
			if(null!=pppvList){
				String pppvKeyPy=null;
				int size = pppvList.size();
				ProductPricePropertyValue pppv = null;
				for(int i=0;i<size;i++){
					pppv=pppvList.get(i);
					pppvKeyPy=pppv.getKeyPy();
					if(null!=pppvKeyPy && !pppvKeyPy.equals("")){
						if(pppvKeyPy.equals("xilieName")){
							pppv.setKeyValues(xilieNameValues);
							aBiz.modifyObject(pppv);
						}else if(pppvKeyPy.equals("baozhuangName")){
							pppv.setKeyValues(baozhuangNameValues);
							aBiz.modifyObject(pppv);
						}else if(pppvKeyPy.equals("shiyongName")){
							pppv.setKeyValues(shiyongNameValues);
							aBiz.modifyObject(pppv);
						}
					}
				}
			}
			
		}
		
	}
	
	
	private static String list2String(List<String> list){
		if(null!=list){
			int size = list.size();
			String s = null;
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<size;i++){
				if(i==(size-1)){
					sb.append(list.get(i));
				}else{
					sb.append(list.get(i)).append(",");
				}
			}
			return sb.toString();
		}
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
