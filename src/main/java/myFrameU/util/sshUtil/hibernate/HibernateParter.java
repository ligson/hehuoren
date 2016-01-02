package myFrameU.util.sshUtil.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import myFrameU.account.entity.AccountItem;
import myFrameU.util.commonUtil.reflect.MyReflect;
import myFrameU.util.commonUtil.text.PinyinUtil;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.Table;

public class HibernateParter {

	private static Configuration hibernateConf;

	private static Configuration getHibernateConf() {
		if (hibernateConf == null) {
			// return new Configuration().configure();
			return new Configuration();
		}
		return hibernateConf;
	}

	private static PersistentClass getPersistentClass(Class clazz) {
		String clazzName = clazz.getName();
		if(null!=clazzName && !clazzName.equals("")){
			synchronized (HibernateParter.class) {
				PersistentClass pc = getHibernateConf().getClassMapping(
						clazzName);

				if (pc == null) {
					hibernateConf = getHibernateConf().addClass(clazz);
					pc = getHibernateConf().getClassMapping(clazzName);
				}
				return pc;
			}
		}
		return null;
		
	}

	/**
	 * 获取实体对应的表名
	 * 
	 * @param clazz
	 *            实体类
	 * @return 表名
	 */
	public static String getTableName(Class clazz) {
		return getPersistentClass(clazz).getTable().getName();
	}

	/**
	 * 获取实体对应表的主键字段名称
	 * 
	 * @param clazz
	 *            实体类
	 * @return 主键字段名称
	 */
	public static String getPkColumnName(Class clazz) {
		Table table = getPersistentClass(clazz).getTable();
		return table.getPrimaryKey().getColumn(0).getName();
	}

	/**
	 * 通过实体类和属性，获取实体类属性对应的表字段名称
	 * 
	 * @param clazz
	 *            实体类
	 * @param propertyName
	 *            属性名称
	 * @return 字段名称
	 */
	public static String getColumnName(Class clazz, String propertyName) {
		PersistentClass persistentClass = getPersistentClass(clazz);
		Property property = persistentClass.getProperty(propertyName);
		Iterator it = property.getColumnIterator();
		if (it.hasNext()) {
			Column column = (Column) it.next();
			return column.getName();
		}
		return null;
	}
	
	public static final String MapKey_propertyName="MapKey_propertyName";
	public static final String MapKey_field="MapKey_field";
	
	public static HashMap<String,HashMap<String,String>> getColumnName_twoMap(Class clazz) throws Exception {
		HashMap<String,HashMap<String,String>> map = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> propertyName_field_map = new HashMap<String,String>();
		HashMap<String,String> field_propertyName_map = new HashMap<String,String>();
		PersistentClass persistentClass = getPersistentClass(clazz);
		
		
		List<String> allPropertys = MyReflect.getAllPropertys(clazz);
		int size = allPropertys.size();
		String propertyName = null;
		Property property = null;
		for(int i=0;i<size;i++){
			propertyName = allPropertys.get(i);
			property = persistentClass.getProperty(propertyName);
			Iterator it = property.getColumnIterator();
			String columName=null;
			if (it.hasNext()) {
				Column column = (Column) it.next();
				columName=column.getName();
				propertyName_field_map.put(propertyName, columName);
				field_propertyName_map.put(columName, propertyName);
			}
		}
		
		map.put(MapKey_propertyName, propertyName_field_map);
		map.put(MapKey_field, field_propertyName_map);
		
		return map;
	}
	
	
	
}