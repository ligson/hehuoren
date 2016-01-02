package myFrameU.queryArgs.util;


import java.util.Map;

import myFrameU.queryArgs.entityForm.EntityFiledFormItem;
import myFrameU.queryArgs.entityForm.EntityForm;
import myFrameU.queryArgs.entityForm.EntityFormUtil;
import myFrameU.util.commonUtil.text.TextUtil;

public class SwitchFGQuery {
	/**
	 * 为了让前台更简洁，所以不能在前台使用真实的keyvalue组件的queryArgs，而用以下代替
	 * 		key-value-filedType-operators_............
	 * 			1、如果filedType和operators都为空，则为key-value
	 * 			2、如果filedType为空，operators不为空，则为key-value--operators
	 * 			3、如果filedType不为空，operators为空，则key-value-filedType
	 * 
	 * 
	 * 		现在将filedType和operators去掉。
	 */
	public static String switchFGQuery(String className,String queryArgs){
		EntityForm ef = EntityFormUtil.getEntityForm(className);
		if(null==ef){
			return queryArgs;
		}
		Map<String,EntityFiledFormItem> effiMap = ef.getEffiMap();
		StringBuffer sb = new StringBuffer("[");
		if(null!=queryArgs && !queryArgs.equals("")){
			String[] array=queryArgs.split("_");
			if(null!=array){
				int len = array.length;
				String queryItem=null;
				String[] qiArray=null;
				StringBuffer queryItemSB = null;
				EntityFiledFormItem effi=null;
				
				String filedKey = null;
				String filedName=null;
				String filedType=null;
				String operators=null;
				for(int i=0;i<len;i++){
					queryItem=array[i];
					if(null!=queryItem && !queryItem.equals("")){
						queryItemSB=new StringBuffer();
						qiArray=queryItem.split("-");
						int qiLen = qiArray.length;
						if(qiLen==2){
							filedKey=qiArray[0];
							effi=effiMap.get(filedKey);
							if(null!=effi){
								filedName=effi.getFiledName();
								filedType=effi.getFiledType();
								operators=effi.getOperators();
								queryItemSB.append("{'key':'").append(filedName).append("','value':'").append(qiArray[1]).append("'");
								if(!filedType.equals("varchar")){
									queryItemSB.append(",'filedType':'").append(filedType).append("'");
								}
								if(!operators.equals("=")){
									queryItemSB.append(",'operators':'").append(operators).append("'");
								}
								queryItemSB.append("}");
							}
						}
						if(i==(len-1)){
							sb.append(queryItemSB.toString());
						}else{
							sb.append(queryItemSB.toString()).append(",");
						}
					}
					/*
					
					int count = TextUtil.getSubNumber(queryItem, "-");
					if(count==1){
						//说明只有key-value
						qiArray=queryItem.split("-");
						if(null!=qiArray){
							queryItemSB.append("{'key':'").append(qiArray[0]).append("','value':'").append(qiArray[1]).append("'}");
						}
					}else if(count==2){
						//说明是key-value-filedType
						qiArray=queryItem.split("-");
						if(null!=qiArray){
							queryItemSB.append("{'key':'").append(qiArray[0]).append("','value':'").append(qiArray[1]).append("','filedType':'").append(qiArray[2]).append("'}");
						}
					}else if(count==3){
						//说明是key-value--operators
						qiArray=queryItem.split("-");
						if(null!=qiArray){
							queryItemSB.append("{'key':'").append(qiArray[0]).append("','value':'").append(qiArray[1]).append("','operators':'").append(qiArray[3]).append("'}");
						}
					}*/
				}
				sb.append("]");
				System.out.println("将前台传递过来的伪装的queryArgs转为真实的queryArgs："+sb.toString());
				return sb.toString();
			}
		}
		return queryArgs;
	}
	
	/**
	 * [{'field':'baozhengjin','ad':'desc'}]
	 * 	前台：baozhengjindesc_idasc
	 * 
	 */
	public static String switchFGOrder(String orderBy){
		if(null!=orderBy && !orderBy.equals("")){
			String[] array=orderBy.split("_");
			int len = array.length;
			String orderItem = null;
			StringBuffer sb = new StringBuffer("[");
			StringBuffer itemSB = null;
			String field = null;
			for(int i=0;i<len;i++){
				itemSB=new StringBuffer("");
				orderItem = array[i];
				if(null!=orderItem && !orderItem.equals("")){
					if(orderItem.endsWith("asc")){
						field=orderItem.replace("asc", "");
						itemSB.append("{'field':'").append(field).append("','ad':'asc'}");
					}else{
						field=orderItem.replace("desc", "");
						itemSB.append("{'field':'").append(field).append("','ad':'desc'}");
					}
				}
				if(i==(len-1)){
					sb.append(itemSB.toString());
				}else{
					sb.append(itemSB.toString()).append(",");
				}
			}
			sb.append("]");
			System.out.println("将前台传递过来的伪装的orderBy转为真实的orderBy："+sb.toString());
			return sb.toString();
		}
		return orderBy;
	}
	
}
