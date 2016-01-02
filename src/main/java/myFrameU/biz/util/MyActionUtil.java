package myFrameU.biz.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import myFrameU.biz.AbstractBizI;
import myFrameU.dao.AbstractDaoI;
import myFrameU.pager.pager.Pager;
import myFrameU.pager.pager.PagerHelper;
import net.sf.ehcache.Cache;


public  class MyActionUtil {
	private  Cache cache;
	private String hqlOrSql;
	public String getHqlOrSql() {
		return hqlOrSql;
	}
	public void setHqlOrSql(String hqlOrSql) {
		this.hqlOrSql = hqlOrSql;
	}
	public MyActionUtil(){}
	public MyActionUtil(HttpServletRequest req,String hqlOrSql){
		//ApplicationContext ac = GetApplicationContext.getApplicationContext(req);
		//cache=(Cache)ac.getBean("pagerCache");
		if(hqlOrSql==null || hqlOrSql.equals("")){
			this.hqlOrSql="sql";
		}else{
			this.hqlOrSql="hql";
		}
	}
	
	
	public static Pager getPager_old(HttpServletRequest req,AbstractBizI aBiz,String queryCountSql,Object[] args){
		BigInteger countLong = null;
		Pager pager=null;
		try {
			//countLong = aBiz.findListCount(args, queryCountHql);
			countLong = (BigInteger) aBiz.j_queryObject(queryCountSql, args);
			int count = 0;
			if(null!=countLong){
				count=countLong.intValue();
			}
			pager=PagerHelper.getPager(req,count);
			req.setAttribute("pager", pager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pager;
	}
	
	/**
	 * 获取分页对象
	 * @param req
	 * @param aBiz
	 * @param queryCountHqlSql
	 * @param args
	 * @return
	 */
	public  Pager getPager(HttpServletRequest req,AbstractBizI aBiz,String queryCountSql,Object[] args){
		BigInteger countLong = null;
		Pager pager=null;
		try {
			//countLong = aBiz.findListCount(args, queryCountHql);
			/*Element element = cache.get(queryCountSql);
			if(null!=element){
				pager=(Pager)element.getObjectValue();
				pager=PagerHelper.getPager(req,pager.getTotalRows());
				req.setAttribute("pager", pager);
			}else{
				countLong = (BigInteger) aBiz.j_queryObject(queryCountSql, args);
				int count = 0;
				if(null!=countLong){
					count=countLong.intValue();
				}
				pager=PagerHelper.getPager(req,count);
				element = new Element(queryCountSql, (Serializable)pager);
				cache.put(element);
				req.setAttribute("pager", pager);
			}*/
			if(hqlOrSql.equals("sql")){
				countLong = (BigInteger) aBiz.j_queryObject(queryCountSql, args);
			}else{
				countLong = (BigInteger) aBiz.findObjectById(queryCountSql, args);
			}
			
			int count = 0;
			if(null!=countLong){
				count=countLong.intValue();
			}
			pager=PagerHelper.getPager(req,count);
			req.setAttribute("pager", pager);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pager;
	}
	public  Pager getPager(HttpServletRequest req,AbstractDaoI aDao,String queryCountSql,Object[] args){
		BigInteger countLong = null;
		Pager pager=null;
		try {
			//countLong = aBiz.findListCount(args, queryCountHql);
			/*Element element = cache.get(queryCountSql);
			if(null!=element){
				pager=(Pager)element.getObjectValue();
				pager=PagerHelper.getPager(req,pager.getTotalRows());
				req.setAttribute("pager", pager);
			}else{
				countLong = (BigInteger) aDao.j_queryObject(queryCountSql, args);
				int count = 0;
				if(null!=countLong){
					count=countLong.intValue();
				}
				pager=PagerHelper.getPager(req,count);
				req.setAttribute("pager", pager);
				element = new Element(queryCountSql, (Serializable)pager);
				cache.put(element);
			}*/
			if(hqlOrSql.equals("sql")){
				countLong = (BigInteger) aDao.j_queryObject(queryCountSql, args);
			}else{
				countLong = (BigInteger) aDao.queryObjectById(queryCountSql, args);
			}
			int count = 0;
			if(null!=countLong){
				count=countLong.intValue();
			}
			pager=PagerHelper.getPager(req,count);
			req.setAttribute("pager", pager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pager;
	}
	public  Pager getPager_static(Map<String,Object> map,AbstractBizI aBiz,String queryCountSql,Object[] args){
		BigInteger countLong = null;
		Pager pager=null;
		try {
			//countLong = aBiz.findListCount(args, queryCountHql);
			/*Element element = cache.get(queryCountSql);
			if(null!=element){
				pager=(Pager)element.getObjectValue();
				pager=PagerHelper.getPager("number", "1", pager.getTotalRows());
				map.put("pager", pager);
			}else{
				countLong = (BigInteger) aBiz.j_queryObject(queryCountSql, args);
				int count = 0;
				if(null!=countLong){
					count=countLong.intValue();
				}
				pager=PagerHelper.getPager("number", "1", count);
				map.put("pager", pager);
				element = new Element(queryCountSql, (Serializable)pager);
				cache.put(element);
			}*/
			if(hqlOrSql.equals("sql")){
				countLong = (BigInteger) aBiz.j_queryObject(queryCountSql, args);
			}else{
				countLong = (BigInteger) aBiz.findObjectById(queryCountSql, args);
			}
			int count = 0;
			if(null!=countLong){
				count=countLong.intValue();
			}
			pager=PagerHelper.getPager("number", "1", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pager;
	}
	
	
	
	
	public  Pager getPager(HttpServletRequest req,int count){
		BigInteger countLong = null;
		Pager pager=null;
		try {
			pager=PagerHelper.getPager(req,count);
			req.setAttribute("pager", pager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pager;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 注意:
	 * 1)只能封装简单对象.不能有级联对象
	 * 2)os和fields属性一定要相同.
	 * 
	 * @param os.数据数组
	 * @param fields.要调用的set方法
	 * @param c 要封住成的对象
	 * @return
	 */
	public static Object array2Object_no_jl(Object[] os,String[] fields,Class c) throws Exception{
		if(null!=os){
			Object o = c.newInstance();
			Field f = null;
			int size=os.length;
			Class[] look_cs=new Class[1];
			Class c1=null;
			Object[] args=new Object[1];
			Method m=null;
			String f_type=null;
			for(int i=0;i<size;i++ ){
				 f = c.getDeclaredField(fields[i]);
				 f_type=f.getType().getName();
				 if(f_type.equals("int")){
					 c1=int.class;
				 }else if(f_type.equals("float")){
					 c1=float.class;
				 }else if(f_type.equals("java.lang.Integer") || f_type.equals("java.lang.String")){
					 c1=Class.forName(f_type);
				 }else{
					
				 }
				 
				 look_cs[0]=c1;
				 args[0]=os[i];
				 m = c.getDeclaredMethod("set"+firstUp(fields[i]),look_cs);//找到方法
				 m.setAccessible(true);
				 m.invoke(o, args);
			}
			return o;
		}else{
			return null;
		}
		
	}
	public static void main(String args[]){
		
	}
	public static String firstUp(String str){
		//将字符串的第一个字母改成大写
		if(null!=str){
			Pattern p = Pattern.compile("([a-z])(.*)");
			Matcher m = p.matcher(str);
			String first = null;
			while (m.find()) {
				first = m.group(1);
				if (null != first) {
					first = first.toUpperCase();
				}
				str = first + str.substring(1);
			}
		}
		return str;
	}
	
	/**
	 * 打印输出list,遍历,作为测试用的.必须使用泛型
	 */
	public static void printlnList(List<? extends Object> list,List<String> jilianMethods) throws Exception{
		//
System.out.println("list集合里大小为:"+list.size());
		Class c = null;
		String inner_name=null;
		String inner_simapleName=null;
		Method[] m = null;
		Object[] os = {};
		Class<?> returnClass=null;
		String returnTypeName=null;
		for(Object o:list){
			System.out.println("============="+o+"=================");
			c=o.getClass();
			//entity.news.NewsType
			inner_name=c.getName();
			//NewsType
			inner_simapleName=c.getSimpleName();
			if(inner_simapleName.equals("Integer") || inner_simapleName.equals("Float") || inner_simapleName.equals("String") || inner_simapleName.equals("Date")){
				System.out.println(o.toString());
			}else{
				//如果是自定义的
				m= c.getDeclaredMethods();
				for(int i=0;i<m.length;i++){
					if(m[i].getName().startsWith("get")){
						returnClass=m[i].getReturnType();
						returnTypeName=returnClass.getSimpleName();
						if(returnTypeName.equals("int") || returnTypeName.equals("Integer") 
								|| returnTypeName.equals("float") || returnTypeName.equals("Float")
								|| returnTypeName.equals("String") || returnTypeName.equals("Date")){
							System.out.println(m[i].getName()+"="+m[i].invoke(o, os));
						}else if(returnTypeName.equals("Set")){
							//如果是Set集合
							if(null!=jilianMethods){
								if(jilianMethods.contains(m[i].getName())){
									//如果需要输出这个级联Set集合对象的数据
									List list1 = (List)m[i].invoke(o, os);
									System.out.println(m[i].getName()+".size()="+list1.size());
								}
							}
						}else if(returnTypeName.equals("List")){
							//如果是List集合
						}else if(returnTypeName.equals("Map")){
							//如果是Map
						}else{
							//如果是自定义的
						}
						//System.out.println(m[i].getReturnType().getSimpleName());
						
					}
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
	}
}
