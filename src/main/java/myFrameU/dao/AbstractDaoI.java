package myFrameU.dao;

import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;


public interface AbstractDaoI {
	
	
	public HibernateTemplate getHt();
	
	/**
	 * hibernate查询单个对象。包括集合级联对象
	 * queryObjectById("get",News.class,1,new String[]{"getNewsImageSet"});
	 * @param getOrLoad  选择get或者load方法
	 * @param c	要查询对象的类  
	 * @param id 根据ID查询
	 * @param invokeMethods  要级联查询的集合对象
	 * @return
	 * @throws Exception
	 */
	public Object queryObjectById(String getOrLoad,Class c,int id,String[] invokeMethods) throws Exception;

	/**
	 * hibernate查询单个对象
	 * @param hql
	 * @return
	 * @throws Exception
	 * 	
	 */
	public Object queryObjectById(String hql,Object[] args) throws Exception;
	
	
	
	
	/**
	 * hibernate查询List集合数据
	 * 
	 * @param c  List里装有的实体类News.class
	 * @param args  where 参数列表
	 * @param hql
	 * @param invokeMethods  需要级联查询出来的Set/List集合对象，无法投影查询
	 * @param pager		是否要分页
	 * @param startRow
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * 
	 * 注意：使用投影查询之后，invokeMethods便无效。
	 * 这个方法如果使用投影查询的时候，就无法通过（真正使用set/list集合级联对象）来查询级联数据。这个方法你要么使用投影查询而不级联查询setlist集合数据。要么非投影查询但是需要级联查询setlist集合数据
	 * 如：from News as n where ....
	 */
	public List<? extends Object> queryObjectList(Class c,Object[] args,String hql,String[] invokeMethods,boolean pager,int startRow,int pageSize) throws Exception;
	
	
	
	
	
	
	
	
	
	/**
	 * hibernate插入对象
	 * @param o
	 * @throws Exception
	 */
	public void insertObject(Object o) throws Exception;
	
	
	/**
	 * hibernate删除对象
	 * @param o
	 * @throws Exception
	 */
	public void deleteObject(Object o) throws Exception;
	
	
	/**
	 * hibernate更新对象
	 * @param o
	 * @throws Exception
	 */
	public void updateObject(Object o) throws Exception;
	
	
	
	/**
	 * hibernate的hql进行执行。主要是delete和update
	 * @param hql
	 * @param args
	 * @throws Exception
	 */
	public void h_execute(String hql,Object[] args) throws Exception;
	
	
	
	
	
	
	/**
	 * jdbc来执行操作
	 * 比如：执行删除、修改。update news set title='?' where id=?
	 * @param sql
	 * @param args 参数
	 * @throws Exception
	 */
	public void j_execute(String sql,Object[] args) throws Exception;
	
	
	public void j_execute_batch(Map<String,Object[]> map) throws Exception;
	
	
	
	/**
	 * jdbc来查询操作，与jdbcTemplate不同之处在于，目前没有做自动封装，而是到biz或者action中去自己手动封装
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 * eg：
	 * 1）List<String> list = (List<String>)newsBiz.j_queryObject("select title from news where id=?",new Object[]{1},true);
	 * 2）List<Object[]> list = (List<Object[]>)newsBiz.j_queryObject("select n.id,n.title,n.info,n.newsTypeStr,n.imgFm,ni.smallImg from news n,newsImage ni where n.id=? and ni.news_id=n.id", new Object[]{3},false);
			if(null!=list){
				Object[] os = null;
				for(int j=0;j<list.size();j++ ){
					os = list.get(j);
					if(null!=os){
						for(int i=0;i<os.length;i++ ){
							System.out.println(os[i]+"===");
						}
					}
				}
				
		}
	 */
	public List<? extends Object> j_queryObjectList(String sql,Object[] args) throws Exception;
	
	/**
	 * 
	 * @param sql
	 * @param args
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public Object j_queryObject(String sql,Object[] args) throws Exception;
	
	
	
	public List<? extends Object> j_queryObjectList(Class c,final String sql,final Object[] args,boolean pager,final int startRow,final int pageSize) throws Exception;
	
}
