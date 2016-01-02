package myFrameU.expand.use.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.expand.distribution.entity.PropertyDistribute;
import myFrameU.expand.libraryProperty.entity.SystemLibraryPropertyValue;
import myFrameU.expand.use.entity.AbstractExpandEntity;
import myFrameU.expand.use.entity.JSONPropertyValues;
import myFrameU.spring.mvc.CommonField;
import myFrameU.util.commonUtil.json.JSONUtils;

public class ExpandPropertyUseUtil {

	
	//将两个json分别
	
	
	
	//合并两个json
	public static String mergePvs(String propertyValues_Ids,String propertyValue_Strs){
		String lastPVs="";
		if(null==propertyValues_Ids || propertyValues_Ids.equals("")){
			if(null!=propertyValue_Strs && !propertyValue_Strs.equals("")){
				lastPVs=propertyValue_Strs;
			}
		}else if(null!=propertyValues_Ids && !propertyValues_Ids.equals("")){
			if(null==propertyValue_Strs || propertyValue_Strs.equals("")){
				lastPVs=propertyValues_Ids;
			}else{
				//lastPVs=propertyValues_Ids+propertyValue_Strs;
				String propertyValues_Ids_NOSuf=propertyValues_Ids.substring(0,propertyValues_Ids.length()-1);//[{},{}
				String propertyValue_Strs_NOPref=propertyValue_Strs.substring(1,propertyValue_Strs.length());//{}{}]
				lastPVs=propertyValues_Ids_NOSuf+","+propertyValue_Strs_NOPref;
			}
		}
		return lastPVs;
	}
	
	
	
	
	
	// 将两个json字符串合并，并统一构建<propertyId,>
	public static HashMap<String, String> getPropertyValuesMap(
			UICacheManager uICacheManager, AbstractExpandEntity ee) {
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, SystemLibraryPropertyValue> libraryPropertyValueMap = (HashMap<String, SystemLibraryPropertyValue>) uICacheManager
				.getObjectCached("web", "libraryPropertyValueMap");
		if (null != libraryPropertyValueMap) {
			String propertyValues_Ids = ee.getPropertyValues_Ids();
			List<JSONPropertyValues> list1 = null;
			if (null != propertyValues_Ids && !propertyValues_Ids.equals("")) {
				list1 = (List<JSONPropertyValues>) JSONUtils.toBeanList(
						ee.getPropertyValues_Ids(), JSONPropertyValues.class);
			}
			List<JSONPropertyValues> list2 = null;
			String propertyValues_Strs = ee.getPropertyValues_Strs();
			if (null != propertyValues_Strs && !propertyValues_Strs.equals("")) {
				list2 = (List<JSONPropertyValues>) JSONUtils.toBeanList(
						ee.getPropertyValues_Strs(), JSONPropertyValues.class);
			}
			List<JSONPropertyValues> allList = new ArrayList<JSONPropertyValues>();
			if (null != list1 && null != list2) {
				allList.addAll(list1);
				allList.addAll(list2);
			} else if (null != list1 && null == list2) {
				allList.addAll(list1);
			} else if (null == list1 && null != list2) {
				allList.addAll(list2);
			} else if (null == list1 && null == list2) {
				allList = null;
			}
			
			
			/*if(null!=allList){
				int ksize = allList.size();
				JSONPropertyValues jpv = null;
				for(int k=0;k<ksize;k++){
					jpv=allList.get(k);
					System.out.println(jpv.getpId()+"="+jpv.getPvId()+"="+jpv.getPvalue()+"#+++++++++++++++++++++++++++++++");
				}
			}
			*/
			
			
			if (null != allList) {
				int size = allList.size();
				JSONPropertyValues jpv = null;
				String pvalue = null;
				String pvId=null;
				String jpvToString = null;
				for (int i = 0; i < size; i++) {
					jpv = allList.get(i);
					pvalue = jpv.getPvalue();
					pvId=jpv.getPvId();
				//	if(!pvId.equals("")){
						jpvToString = jpv.toString();
						if (!jpvToString.contains("pvId")
								&& jpvToString.contains("pvalue")) {
							// input类型
							map.put("pId_" + jpv.getpId(), pvalue);
						} else if (jpvToString.contains("pvId")
								&& !jpvToString.contains("pvalue")) {
							// 非input
							// id中含有,说明是含有多个值的checkbox
							if (pvId.contains(",")) {
								String[] pvalue_array = pvId.split(",");
								int pvalue_array_len = pvalue_array.length;
								int pvalue2Id = 0;
								SystemLibraryPropertyValue slpv = null;
								for (int j = 0; j < pvalue_array_len; j++) {
									String s = pvalue_array[j];
									if(!s.equals("")){
										pvalue2Id = new Integer(pvalue_array[j]).intValue();
										slpv = libraryPropertyValueMap.get("pvId_"+pvalue2Id);
										if (null != slpv) {
											String oldValue=map.get("pId_"+jpv.getpId());
											if(null!=oldValue && !oldValue.equals("")){
												String newValue=oldValue+","+slpv.getPvalue();
												map.put("pId_" + jpv.getpId(),newValue);
											}else{
												map.put("pId_" + jpv.getpId(),slpv.getPvalue());
											}
										}
									}
								}
							} else {
								// 说明是select，radio，或者是只有一个值的checkbox
								if(!pvId.equals("")){
									int pvalue2Id = new Integer(pvId).intValue();
									SystemLibraryPropertyValue slpv = libraryPropertyValueMap
											.get("pvId_" + pvalue2Id);
									if (null != slpv) {
										map.put("pId_" + jpv.getpId(), slpv.getPvalue());
									}
								}
								
							}
						}
					//}
				}
			}
		}

		/*for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue() + "=======================########$$$$$$$$$$$%%%%%%%%%%^^^^^^^^^^^^^^==========================");
		}*/

		return map;
	}

	// 将统一的拆分成两个json字符串，存储在数据库中不同的字段中。
	// [{'pId':'8','pvalue':'默认产品1熟悉ing6'},{'pId':'7','pvId':'34'},{'pId':'4','pvId':'22'},{'pId':'6','pvId':'30'}]
	public static HashMap<String, String> getPropertyValues_Ids_Strs(
			String result) {
		if(null==result){
			return null;
		}
		result=result.replaceAll(",,", ",");
		HashMap<String, String> map = new HashMap<String, String>();
		StringBuffer ids_sb = new StringBuffer("[");
		StringBuffer strs_sb = new StringBuffer("[");
		List<JSONPropertyValues> list = JSONUtils.toBeanList(result,
				JSONPropertyValues.class);
		int size = list.size();
		JSONPropertyValues jpv = null;
		String pvalue = null;
		String pId = null;
		String pvId = null;
		String jpvJson = null;
		for (int i = 0; i < size; i++) {
			jpv = list.get(i);
			if(null!=jpv){
				pId = jpv.getpId();
				pvId = jpv.getPvId();
				pvalue = jpv.getPvalue();
				jpvJson = jpv.toString();
				if (jpvJson.contains("pvalue") && !jpvJson.contains("pvId")) {
					strs_sb.append(jpvJson).append(",");
				} else if (!jpvJson.contains("pvalue") && jpvJson.contains("pvId")) {
					ids_sb.append(jpvJson).append(",");
				}
			}
		}

		String ids_sbStr = ids_sb.toString();
		String strs_sbStr = strs_sb.toString();
		if (null != ids_sbStr && !ids_sbStr.equals("")
				&& ids_sbStr.endsWith(",")) {
			ids_sbStr = ids_sbStr.substring(0, ids_sbStr.length() - 1);
		}
		if (null != strs_sbStr && !strs_sbStr.equals("")
				&& strs_sbStr.endsWith(",")) {
			strs_sbStr = strs_sbStr.substring(0, strs_sbStr.length() - 1);
		}

		ids_sbStr = ids_sbStr + "]";
		strs_sbStr = strs_sbStr + "]";
		map.put("propertyValues_Ids", ids_sbStr);
		map.put("propertyValues_Strs", strs_sbStr);

		System.out.println("##########$$$$$$$$$$$$$$$$$==propertyValues_Ids="
				+ ids_sbStr);
		System.out.println("##########$$$$$$$$$$$$$$$$$==propertyValues_Strs="
				+ strs_sbStr);

		return map;
	}

	public static List<PropertyDistribute> getPropertyDistributes(
			String className, String dRange, UICacheManager uICacheManager) {
		if (null != className && !className.equals("")) {
			HashMap<String, HashMap<String, List<PropertyDistribute>>> pdmapALL = (HashMap<String, HashMap<String, List<PropertyDistribute>>>) uICacheManager
					.getObjectCached("web", "distributePropertyMap");
			if (null != pdmapALL) {
				HashMap<String, List<PropertyDistribute>> pdmap = pdmapALL
						.get(className);
				if(null!=pdmap){
					if (null == dRange || dRange.equals("")) {
						// req.setAttribute("propertyDistributeMap", pdmap);
					} else {
						List<PropertyDistribute> list = pdmap.get(dRange);
						return list;
					}
				}
			}
		}
		return null;
	}
	
	
	
	
	//add的时候，有两种方式，一种是将tempSaveResults的input先用js来串起来，给服务器一个字符串。一种是直接form表单穿数组
	public static String getPropertyValuesFromArray(HttpServletRequest req){
		String[] propertyValues = req.getParameterValues(CommonField.propertyValuesArray);
		if(null!=propertyValues && !propertyValues.equals("undefined")){
			int len = propertyValues.length;
			String propertyValue=null;
			StringBuffer sb = new StringBuffer("[");
			for(int i=0;i<len;i++){
				propertyValue=propertyValues[i];
				if(i==(len-1)){
					sb.append(propertyValue);
				}else{
					sb.append(propertyValue).append(",");
				}
			}
			sb.append("]");
			return sb.toString();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
