package myFrameU.expand.libraryProperty.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import myFrameU.util.commonUtil.enumU.EnumUtil;

/**
 * 
 * 系统property库
 *
 */
public class SystemLibraryProperty   implements Serializable{

	private Set<SystemLibraryPropertyValue> sysLibraryPropertyValueSet;

	private int id;
	private String propertyName;// 属性名称
	private String propertyKey;// 属性key

	private String dataType;// 数据类型，int，float，string，boolean，date，long，double等。具体参考util

	public enum DATATYPE {
		INT, LONG, FLOAT, DOUBLE, STRING, DATE, BOOLEAN
	}

	private String dataFrom;// 数据来源，手动，程序

	public enum DATAFROM {
		MANUAL, WEB
	}

	private String dataFromWebUrl;// 如果数据来源是程序web，那么需要指定url

	private String addType;// add object的时候，该属性所呈现的类型。自己【Input(输入) select(下拉)
							// radio(单选) checkbox(多选)】

	public enum ADDTYPE {
		INPUT, SELECT, RADIO, CHECKBOX
	}

	private String showType;// 展示类型。枚举、下拉、单选、多选

	public enum SHOWTYPE {
		ENUM, SELECT, RADIO, CHECKBOX
	}

	private String valueDefault;// 默认值
	private String valueAlternative;// 备选值

	/*private boolean search;// 是否参与search
	private boolean must;// 是否必须填写
	private boolean queryArg;// 是否参与组合复杂查询
	private boolean list;// 是否显示在list查询中
*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public String getDataFromWebUrl() {
		return dataFromWebUrl;
	}

	public void setDataFromWebUrl(String dataFromWebUrl) {
		this.dataFromWebUrl = dataFromWebUrl;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getValueDefault() {
		return valueDefault;
	}

	public void setValueDefault(String valueDefault) {
		this.valueDefault = valueDefault;
	}

	public String getValueAlternative() {
		return valueAlternative;
	}

	public void setValueAlternative(String valueAlternative) {
		this.valueAlternative = valueAlternative;
	}

	public Set<SystemLibraryPropertyValue> getSysLibraryPropertyValueSet() {
		return sysLibraryPropertyValueSet;
	}

	public void setSysLibraryPropertyValueSet(
			Set<SystemLibraryPropertyValue> sysLibraryPropertyValueSet) {
		this.sysLibraryPropertyValueSet = sysLibraryPropertyValueSet;
	}
	//======================================================================================================================
	public HashMap<String,SystemLibraryPropertyValue> getValues(){
		HashMap<String,SystemLibraryPropertyValue> map = new HashMap<String,SystemLibraryPropertyValue>();
		Iterator it = sysLibraryPropertyValueSet.iterator();
		SystemLibraryPropertyValue v = null;
		while(it.hasNext()){
			v=(SystemLibraryPropertyValue)it.next();
			map.put("pvId_"+v.getId(), v);
		}
		return map;
	}
	
	// =====================================================================================================================
	
	// 输出html，在为shop添加使用这个属性的时候
	private String htmlAdd;
	public void setHtmlAdd(String htmlAdd) {
		this.htmlAdd = htmlAdd;
	}

	/**
	 * <c:if test='${p.addType=="INPUT"}'>
								<input type="text" value="${p.valueDefault}" name="${p.propertyKey}"/>
							</c:if>
							<c:if test='${p.addType=="SELECT"}'>
								<select name="${p.propertyKey}">
									<c:if test='${empty p.valueDefault || p.valueDefault==""}'>
										<option value="">请选择${p.propertyName}</option>
									</c:if>
									<c:forEach var="pv" items="${p.sysLibraryPropertyValueSet}">
										<c:if test='${pv.defaultValue==true}'>
											<option selected="selected" value="${pv.pvalue}">${pv.pvalue}</option>
										</c:if>
										<c:if test='${pv.defaultValue==false}'>
											<option value="${pv.pvalue}">${pv.pvalue}</option>
										</c:if>
									</c:forEach>
								</select>
							</c:if>
							<c:if test='${p.addType=="RADIO"}'>
								<c:forEach var="pv" items="${p.sysLibraryPropertyValueSet}">
									<c:if test='${pv.defaultValue==true}'>
										<label><input checked="checked" type="radio" name="${p.propertyKey}" value="${pv.pvalue}"/>${pv.pvalue}</label>
									</c:if>
									<c:if test='${pv.defaultValue==false}'>
										<label><input type="radio" name="${p.propertyKey}" value="${pv.pvalue}"/>${pv.pvalue}</label>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test='${p.addType=="CHECKBOX"}'>
								<c:forEach var="pv" items="${p.sysLibraryPropertyValueSet}">
									<c:if test='${pv.defaultValue==true}'>
										<label><input checked="checked" type="checkbox" name="${p.propertyKey}" value="${pv.pvalue}"/>${pv.pvalue}</label>
									</c:if>
									<c:if test='${pv.defaultValue==false}'>
										<label><input type="checkbox" name="${p.propertyKey}" value="${pv.pvalue}"/>${pv.pvalue}</label>
									</c:if>
								</c:forEach>
							</c:if>
	 * @return
	 */
	public String getHtmlAdd() {
		StringBuffer sb = new StringBuffer("");
		if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.INPUT, addType)){
			sb.append("<input propertyId='").append(this.getId()).append("' ").append(" type='text' value='").append(this.valueDefault).append("' name='").append(this.propertyKey).append("'/>");
		}else if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.SELECT, addType)){
			sb.append("<select name='").append(this.propertyKey).append("'>");
			if(null==this.valueDefault || this.valueDefault.equals("")){
				sb.append("<option value='' >请选择").append(this.propertyName).append("</option>");
			}
			Iterator it = this.sysLibraryPropertyValueSet.iterator();
			SystemLibraryPropertyValue pv=null;
			while(it.hasNext()){
				pv=(SystemLibraryPropertyValue) it.next();
				if(pv.getDefaultValue()){
					sb.append("<option propertyId='").append(this.getId()).append("'").append("select='selected' value='").append(pv.getPvalue()).append("' >").append(pv.getPvalue()).append("</option>");
				}else{
					sb.append("<option propertyValueId='").append(pv.getId()).append("' ").append("value='").append(pv.getPvalue()).append("' >").append(pv.getPvalue()).append("</option>");
				}
			}
		}else if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.RADIO, addType)){
			Iterator it = this.sysLibraryPropertyValueSet.iterator();
			SystemLibraryPropertyValue pv=null;
			while(it.hasNext()){
				pv=(SystemLibraryPropertyValue) it.next();
				if(pv.getDefaultValue()){
					sb.append("<label><input propertyValueId='").append(pv.getId()).append("' ").append(" propertyId='").append(this.getId()).append("' checked='checked' type='radio' name='").append(this.getPropertyKey()).append("' value='").append(pv.getPvalue()).append("' />").append(pv.getPvalue()).append("</label>");
				}else{
					sb.append("<label><input propertyValueId='").append(pv.getId()).append("' ").append(" propertyId='").append(this.getId()).append("' type='radio' name='").append(this.getPropertyKey()).append("' value='").append(pv.getPvalue()).append("' />").append(pv.getPvalue()).append("</label>");
				}
			}
		}else if(EnumUtil.equalsE(SystemLibraryProperty.ADDTYPE.CHECKBOX, addType)){
			Iterator it = this.sysLibraryPropertyValueSet.iterator();
			SystemLibraryPropertyValue pv=null;
			while(it.hasNext()){
				pv=(SystemLibraryPropertyValue) it.next();
				if(pv.getDefaultValue()){
					sb.append("<label><input propertyValueId='").append(pv.getId()).append("' ").append(" propertyId='").append(this.getId()).append("' checked='checked' type='checkbox' name='").append(this.getPropertyKey()).append("' value='").append(pv.getPvalue()).append("' />").append(pv.getPvalue()).append("</label>");
				}else{
					sb.append("<label><input  propertyValueId='").append(pv.getId()).append("' ").append("propertyId='").append(this.getId()).append("'  type='checkbox' name='").append(this.getPropertyKey()).append("' value='").append(pv.getPvalue()).append("' />").append(pv.getPvalue()).append("</label>");
				}
			}
		}
		return sb.toString();
	}

}
