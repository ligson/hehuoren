package myFrameU.util.commonUtil.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myFrameU.util.sshUtil.hibernate.HibernateParter;

public class MyReflect {
	//获得id
	public static int getId(Object o) throws Exception{
		Class c = o.getClass();
		Method m = c.getDeclaredMethod("getId");
		int id=(Integer)m.invoke(o, null);
		return id;
	}
	//获取字段的类型
	public static String getFieldType(Class c,String field) throws Exception{
		Field f =c.getDeclaredField(field);
		String ftname=f.getType().getName();
		return ftname;
	}
	
	//获取一个类的所有属性
	//排除不持久化的属性
	private static List<String> noFieldPropertyList = new ArrayList<String>();
	static{
		noFieldPropertyList.add("extraDataMap");
		noFieldPropertyList.add("noSave_payTimeOverDate");
		noFieldPropertyList.add("noSave_noPay2ShopDate");
		noFieldPropertyList.add("noSave_shopMainPt");
		noFieldPropertyList.add("mainProductTypeList");
		
		
		
	}
	public static List<String> getAllPropertys(Class c) throws Exception{
		List<String> list = new ArrayList<String>();
		Field[] fs = c.getDeclaredFields();
		Field f = null;
		int len = fs.length;
		String name=null;
		for(int i=0;i<len;i++){
			f=fs[i];
			name=f.getName();
			if(!noFieldPropertyList.contains(name)){
				list.add(f.getName());
			}
		}
		return list;
	}
	
	//将propertyValue如果是string，变成‘’
	public static String getPropertyValue(Class c,String field,String propertyValue) throws Exception{
		String ftname=getFieldType(c,field);
		if(ftname.equals("java.lang.String")){
			propertyValue="'"+propertyValue+"'";
		}
		return propertyValue;
	}
	
	
	
	public static void main(String[] args){
		try {
			Class c = Class.forName("yishupaipai.shop.entity.ShopLevel");
			Field f =c.getDeclaredField("specialReturnPlan");
			String ftname=f.getType().getName();
			System.out.println(ftname);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//biz中调用
	//将List<Object[]> 转成List<Object> object是Special
	public static List<? extends Object> switch2Objects(Class c,List<Map> oslist) throws Exception{
		List<Object> list = new ArrayList<Object>();
		HashMap<String,HashMap<String,String>> map = HibernateParter.getColumnName_twoMap(c);
		HashMap<String,String> field_propertyName_map = map.get(HibernateParter.MapKey_field);
		if(null!=oslist){
			int size = oslist.size();
			HashMap m = null;
			for(int i=0;i<size;i++){
				Object o = c.newInstance();
				m=(HashMap) oslist.get(i);
				Set set = m.entrySet();         
				Iterator iter = set.iterator();   
				String fieldName=null;
				Object fieldValue=null;
				String classPropertyName=null;
				String cpn_set=null;
				String propertyType=null;
				while(iter.hasNext()){      
				     Map.Entry<String, Object> entry1=(Map.Entry<String, Object>)iter.next();    
				     fieldName=entry1.getKey();
				     fieldValue=entry1.getValue();
				     classPropertyName=field_propertyName_map.get(fieldName);
				     propertyType=getFieldType(c, classPropertyName);
				     System.out.println(propertyType+"============================fieldValue:"+fieldValue+"==fieldName:"+fieldName);
				     
				     
				     char first=classPropertyName.charAt(0);
				     if(first>='a' && first<='z'){
				    	 first = (char) (first-32);
				     }
				     classPropertyName=classPropertyName.substring(1,classPropertyName.length());
				     cpn_set="set"+first+classPropertyName;
				     Method method1=null;
				     if(propertyType.equals("boolean")){
				    	 method1=c.getDeclaredMethod(cpn_set, boolean.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Boolean) fieldValue);
				    	 }
				     }else if(propertyType.equals("int")){
				    	 method1=c.getDeclaredMethod(cpn_set, int.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Integer) fieldValue);
				    	 }
				     }else if(propertyType.equals("float")){
				    	 method1=c.getDeclaredMethod(cpn_set, float.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Float) fieldValue);
				    	 }
				     }else if(propertyType.equals("double")){
				    	 method1=c.getDeclaredMethod(cpn_set, double.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Double) fieldValue);
				    	 }
				     }else if(propertyType.equals("long")){
				    	 method1=c.getDeclaredMethod(cpn_set, long.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Long) fieldValue);
				    	 }
				     }else if(propertyType.equals("java.lang.String")){
				    	 method1=c.getDeclaredMethod(cpn_set, String.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, fieldValue+"");
				    	 }
				     }else if(propertyType.equals("java.lang.Integer")){
				    	 method1=c.getDeclaredMethod(cpn_set, Integer.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Integer) fieldValue);
				    	 }
				     }else if(propertyType.equals("java.lang.Float")){
				    	 method1=c.getDeclaredMethod(cpn_set, Float.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Float) fieldValue);
				    	 }
				     }else if(propertyType.equals("java.lang.Double")){
				    	 method1=c.getDeclaredMethod(cpn_set, Double.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Double) fieldValue);
				    	 }
				     }else if(propertyType.equals("java.lang.Long")){
				    	 method1=c.getDeclaredMethod(cpn_set, Long.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Long) fieldValue);
				    	 }
				     }else if(propertyType.equals("java.util.Date")){
				    	 method1=c.getDeclaredMethod(cpn_set, Date.class);
				    	 if(null!=fieldValue && !fieldValue.equals("")){
				    		 method1.invoke(o, (Date) fieldValue);
				    	 }
				     }else if(propertyType.startsWith("myFrame") || propertyType.startsWith("myFrameU") || propertyType.startsWith("yishupaipai")){
				    	 
				     }else{
				    	// method1=c.getDeclaredMethod(cpn_set, Object.class);
				     }
				}  
				list.add(o);
			}
		}else{
			return null;
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
