package myFrameU.spring.mvc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import myFrameU.util.commonUtil.json.JSONUtils;
import myFrameU.util.commonUtil.json.JsonValidator;

public class SDynaActionForm implements Serializable{
	private HashMap<String,Object> formMap=new HashMap<String,Object>();
	
	public HashMap<String, Object> getFormMap() {
		return formMap;
	}
	public void setFormMap(HashMap<String, Object> formMap) {
		this.formMap = formMap;
	}
	private boolean isNull(Object value){
		if(null==value){
			return true;
		}
		return false;
	}
	public Integer getInteger(String key){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String valueStr=value.toString();
			if(!valueStr.equals("")){
				Integer i=new Integer(valueStr);
				return i;
			}
		}
		return null;
	}
	
	public String getString(String key){
		Object value=formMap.get(key);
		if(!isNull(value)){
			return (String) value;
		}
		return null;
	}
	
	
	public String[] getStrings(String key){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String v = value.toString();
			if(v.endsWith(",")){
				v=v.substring(0,v.length()-1);
			}
			String[] array=null;
			if(v.contains(",")){
				array=v.split(",");
			}else{
				array=new String[1];
				array[0]=v;
			}
			return array;
		}
		return null;
	}
	
	
	
	
	
	
	
	public Boolean getBoolean(String key){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String valueStr=value.toString();
			if(!valueStr.equals("")){
				Boolean b = new Boolean(valueStr);
				return b;
			}
		}
		return true;
	}
	public Boolean getBoolean(String key,boolean defaultBoolean){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String valueStr=value.toString();
			if(!valueStr.equals("")){
				Boolean b = new Boolean(valueStr);
				return b;
			}else{
				return defaultBoolean;
			}
		}
		return defaultBoolean;
	}
	public Double getDouble(String key){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String valueStr=value.toString();
			if(!valueStr.equals("")){
				Double b = new Double(valueStr);
				return b;
			}else{
			}
		}
		return null;
	}
	public Float getFloat(String key){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String valueStr=value.toString();
			if(!valueStr.equals("")){
				Float b = new Float(valueStr);
				return b;
			}else{
			}
		}
		return null;
	}
	public Object getObject(String key){
		Object value=formMap.get(key);
		if(!isNull(value)){
			return (Object) value;
		}
		return null;
	}
	
	
	public <T> T getObjetFromJSONStr(String key,Class c){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String jsonStr=(String) value;
			if(JsonValidator.validate(jsonStr)){
				return (T) JSONUtils.toBean(jsonStr, c);
			}
		}
		return null;
	}
	
	public  <T> List<T> getListFromJSONStr(String key,Class c){
		Object value=formMap.get(key);
		if(!isNull(value)){
			String jsonStr=(String) value;
			if(JsonValidator.validate(jsonStr)){
				return JSONUtils.toBeanList(jsonStr, c);
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
}
