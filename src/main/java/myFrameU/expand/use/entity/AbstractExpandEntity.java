package myFrameU.expand.use.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;

import myFrameU.util.commonUtil.json.JSONUtils;




public class AbstractExpandEntity   implements Serializable{
	private int id;
	
	private String propertyValues_Ids;//非Input类型
	private String propertyValues_Strs;//input类型
	
	//=======================================

	
	private HashMap<String,String> propertyValuesMap;//ExpandPropertyUseUtil.getPropertyValuesMap()
													//<pId,realPvalue>  <3,'本科'>
	
	
	public int getId() {
		return id;
	}
	public HashMap<String, String> getPropertyValuesMap() {
		return propertyValuesMap;
	}
	public void setPropertyValuesMap(HashMap<String, String> propertyValuesMap) {
		this.propertyValuesMap = propertyValuesMap;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="propertyValues_Ids")
	public String getPropertyValues_Ids() {
		return propertyValues_Ids;
	}
	public void setPropertyValues_Ids(String propertyValues_Ids) {
		this.propertyValues_Ids = propertyValues_Ids;
	}
	@Column(name="propertyValues_Strs")
	public String getPropertyValues_Strs() {
		return propertyValues_Strs;
	}
	public void setPropertyValues_Strs(String propertyValues_Strs) {
		this.propertyValues_Strs = propertyValues_Strs;
	}
	
	
}
