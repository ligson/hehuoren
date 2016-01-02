package myFrameU.expand.use.entity;

import java.io.Serializable;

import myFrameU.util.commonUtil.collection.MyCollectionUtils;

/**
 * 
 * 从abstractExpandEntity中的propertyValues_Ids和propertyValues_Strs转化出来
 *		propertyValues_Ids：{'pId':'3','pvId':'23'}
 *		propertyValues_Strs:{'pId':'3','pvalue':'这是我自己输入的值'}
 *
 *
 */
public class JSONPropertyValues   implements Serializable{
	private String pId;//property.id
	private String pvId;//propertyValue.id
	private String pvalue;//propertyValue.pvalue
	
	
	
	
	
	@Override
	public String toString() {
		if(null!=pvId){
			if(pvId.startsWith(",")){
				pvId=pvId.substring(1,pvId.length());
			}
			if(pvId.endsWith(",")){
				pvId=pvId.substring(0,pvId.length()-1);
			}
		}
		StringBuffer sb = new StringBuffer();
		if(null==pvId || pvId.equals("") || pvId.equals("null")){
			//说明是str的
			sb.append("{'pId':'").append(pId).append("','").append("pvalue':'").append(pvalue).append("'").append("}");
		}else{
			if(pvId.contains(",")){
				pvId=MyCollectionUtils.sortStringArray(pvId);
			}
			sb.append("{'pId':'").append(pId).append("','").append("pvId':'").append(pvId).append("'").append("}");
		}
		/*if(null==pvId || pvId.equals("") || pvId.equals("null")){
			//说明是str的
			sb.append("(pId=").append(pId).append(",").append("pvalue=").append(pvalue).append(")");
		}else{
			sb.append("(pId=").append(pId).append(",").append("pvId=").append(pvId).append(")");
		}*/
		return sb.toString();
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getPvId() {
		return pvId;
	}
	public void setPvId(String pvId) {
		this.pvId = pvId;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	
	
	
}
