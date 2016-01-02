package myFrameU.dao;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import myFrameU.account.entity.AccountItem;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;



public class AbstractDaoImpl  implements AbstractDaoI{
	
	
	private HibernateTemplate ht;
	
	public HibernateTemplate getHt() {
		return ht;
	}

	public void setHt(HibernateTemplate ht) {
		this.ht = ht;
	}


	@Override
	public Object queryObjectById(final String getOrLoad, final Class c,final int id,final String[] invokeMethods) throws Exception {
		List<? extends Object> list= null;
		list= (List<? extends Object>)ht.executeFind(new  HibernateCallback() {
	           public Object doInHibernate(Session session)throws HibernateException, SQLException {
	        	   Object o = null;
	        	   if(null!=getOrLoad){
	       			if(getOrLoad.equals("get")){
	       				o= session.get(c, id);
	       				//System.out.println("dao queryObjectById  get  o="+o);
	       			}else if(getOrLoad.equals("load")){
	       				o= session.load(c, id);
	       			}else{
	       				//容错性，如果输入错误，输入的不是get也不是load，默认的为get加载
	       				o= session.get(c, id);
	       			}
	       			}
	        	List list = new ArrayList(1);
	       		list.add(o);
	       		invokeMehods_(c,invokeMethods,list,session);
	       		return list;
	           }
		});
		if(null!=list){
			if(list.size()>0){
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public Object queryObjectById(String hql,Object[] args) throws Exception {
		List list = ht.find(hql,args);
		if(null!=list){
			if(list.size()>0){
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public List<? extends Object> queryObjectList(final Class c,final Object[] oArray, final String hql, final String[] invokeMethods, final boolean pager, final int startRow, final int pageSize) throws Exception {
		List<? extends Object> list= null;
		if(pager){
			//分页
			list= (List<? extends Object>)ht.executeFind(new  HibernateCallback() {
		           public Object doInHibernate(Session session)throws HibernateException, SQLException {
		            Query query = session.createQuery(hql).setFirstResult(startRow).setMaxResults(pageSize);
		            List<? extends Object> list = null;
		            if(null!=oArray){
		            	int size = oArray.length;
			            for(int i=0;i<size;i++ ){
			            	query.setParameter(i, oArray[i]);
			            }
		            }    
			        list = query.list();
			        invokeMehods_(c,invokeMethods,list,session);
		            return list;
		           }
			});
		}else{
			//不分页
			//如果不分页的话,按理说可以直接使用ht.find(hql,oArray);但是由于ht会直接将我们关掉session，这让我们无法真正使用这些级联属性，所以我们必须
			//得还原到hibernate环境中去
			list= (List<? extends Object>)ht.executeFind(new  HibernateCallback() {
		           public Object doInHibernate(Session session)throws HibernateException, SQLException {
		            Query query = session.createQuery(hql);
		            List<? extends Object> list =  null;
		            if(null!=oArray){
		            	 int size = oArray.length;
				            for(int i=0;i<size;i++ ){
				            	query.setParameter(i, oArray[i]);
				            }
				    }
				    list = query.list();
				    invokeMehods_(c,invokeMethods,list,session);
		            return list;
		           }
			});
		}
		//进行加载级联属性
		return list;
	}
	
	
	
	
	
	
	
	
	private static final String Brace="{";
	private static final String RightBrace="}";
	private static final String Vertical="|";
	private static final String Vertical_="\\|";
	private static final String LeftBracket="[";
	private static final String RightBracket="]";
	private static final String Colon=":";
	private static final String S="s";
	
	
	
	
	
	public void invokeMehods_(final Class c, final String[] invokeMethods,List<? extends Object> list,Session session){
		try{
			if(null!=invokeMethods && null!=list){
				
				Set innerSet=null;
				
				int listSize=list.size();
				int size=invokeMethods.length;
				Class[] cs ={};
				Object[] os ={};
				Method m = null;
				Set set =null;
				Class<?> rt=null;
				Object result=null;
				String methodName_parentJb=null;
				String[] methodName_parentJb_array=null;
				String methodName=null;
				
				//getCcsProSet[where this.id>3:name]
				String Set_Filter_hql=null;//集合  筛选  hql
				int Set_Filter_hql_Brackets_Index=0;//集合筛选hql中第一个左中括号的index
				Class[] cs_set_set ={Set.class};
				Method m_set_set = null;
				String methodName_set=null;
				
				
				//----------------------inner内的级联查询----------------------
				String innerCase_getMethod=null;//内的另外级联的集合或者非 集合
				int method_Braces_index=0;//大括号的位置
				Class innerCase_class=null;
				List<String> innerCase_iinvokeMethods_list=new ArrayList<String>();
				Map<String,Class> innerCase_invokeMethod_classMap=new HashMap<String,Class>();
				Map<String,List<? extends Object>> innerCase_invokeMethod_entityList=new HashMap<String,List<? extends Object>>();
				List innerCase_invokeMethod_list=null;
				
				
				
				
				
				int parentJb=0;
				
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						for(int i=0;i<size;i++ ){
							innerCase_getMethod=null;
							int currentObject=0;
							for(Object o:list){
								if(null!=o){
									currentObject++;
							methodName_parentJb=invokeMethods[i];
							if(methodName_parentJb.contains(Vertical)){
								//说明是getCanshuSet|2的情况，说明product里没有canshuSet，而是在product的爷爷那里有。
								methodName_parentJb_array=methodName_parentJb.split(Vertical_);
								if(null!=methodName_parentJb_array && methodName_parentJb_array.length==2){
									methodName=methodName_parentJb_array[0];
									
									parentJb=new Integer(methodName_parentJb_array[1]).intValue();
								}
							}else{
								methodName=methodName_parentJb;
								parentJb=0;//自己本身
							}
							
							
							if(null!=methodName){
								//----------------------inner内的级联查询----------------------
								if(methodName.contains(Brace) && methodName.contains(RightBrace)){
									method_Braces_index=methodName.indexOf(Brace);
									innerCase_getMethod=methodName.substring(method_Braces_index+1,methodName.length()-1);
//System.out.println(innerCase_getMethod+"***************************************************************88888");
									methodName=methodName.replace(Brace+innerCase_getMethod+RightBrace, "");
									//System.out.println("innerCase_getMethod="+innerCase_getMethod);//getCcpvSet[where this.status=1]
									if(!innerCase_iinvokeMethods_list.contains(innerCase_getMethod)){
										innerCase_iinvokeMethods_list.add(innerCase_getMethod);
									}
								}
								
								if(methodName.contains(LeftBracket) && methodName.contains(RightBracket)){
									Set_Filter_hql_Brackets_Index=methodName.indexOf(LeftBracket);
									Set_Filter_hql=methodName.substring(Set_Filter_hql_Brackets_Index+1,methodName.length()-1);
									methodName=methodName.substring(0,Set_Filter_hql_Brackets_Index);
									methodName_set=S+methodName.substring(1);
//System.out.println("Set_Filter_hql="+Set_Filter_hql+";====methodName="+methodName+"==========="+methodName_set);
								}
							}
							
							if(null!=methodName_set && !methodName_set.equals("")){
								if(parentJb==0){
									m = c.getDeclaredMethod(methodName,cs);//
									m_set_set=c.getDeclaredMethod(methodName_set, cs_set_set);
								}else{
									if(parentJb==1){
										m = c.getSuperclass().getDeclaredMethod(methodName,cs);//
										m_set_set=c.getSuperclass().getDeclaredMethod(methodName_set, cs_set_set);
									}else if(parentJb==2){
										m = c.getSuperclass().getSuperclass().getDeclaredMethod(methodName,cs);//
										m_set_set=c.getSuperclass().getSuperclass().getDeclaredMethod(methodName_set, cs_set_set);
									}else if(parentJb==3){
										m = c.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod(methodName,cs);//
										m_set_set=c.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod(methodName_set, cs_set_set);
									}
								}
							}else{
								if(parentJb==0){
									m = c.getDeclaredMethod(methodName,cs);//
								}else{
									if(parentJb==1){
										m = c.getSuperclass().getDeclaredMethod(methodName,cs);//
									}else if(parentJb==2){
										m = c.getSuperclass().getSuperclass().getDeclaredMethod(methodName,cs);//
									}else if(parentJb==3){
										m = c.getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod(methodName,cs);//
									}
								}
							}
							
							//System.out.println("dao中的invokeMehods_中的m="+m);
							//System.out.println("dao中的invokeMehods_中的o="+o);
							result =  m.invoke(o, os);
							if(result instanceof Set){
								//set = (Set) m.invoke(o, os);
								set = (Set)result;
								if(null!=set){
									if(null!=Set_Filter_hql && !Set_Filter_hql.equals("") && null!=m_set_set){
										//对集合进行筛选
									    List list_set=session.createFilter(set, Set_Filter_hql).list();
									    Set set1 = new HashSet();
									    set1.addAll(list_set);
									    //Object[] os_set_set ={set1};
									    m_set_set.invoke(o, set1);
									    list_set.clear();
									    list_set=null;
									    set1=null;
									}else{
										System.out.print(set.size());	//查询这个集合左右的
									}
								}
							}else{
								System.out.print(result);
							}
							
							
							
							
							
							//----------------------inner内的级联查询----------------------
							if(null!=innerCase_getMethod && !innerCase_getMethod.equals("") && !innerCase_getMethod.equals("null")){
								innerCase_invokeMethod_list = innerCase_invokeMethod_entityList.get(innerCase_getMethod);
								if(null==innerCase_invokeMethod_list){
									innerCase_invokeMethod_list=new ArrayList();
								}
								if(result instanceof Set){
									innerCase_invokeMethod_list.addAll(set);
								}else{
									innerCase_invokeMethod_list.add(result);
								}
								innerCase_invokeMethod_entityList.put(innerCase_getMethod, innerCase_invokeMethod_list);
							}
							
							
							if(null!=innerCase_getMethod && !innerCase_getMethod.equals("")){
								innerCase_class=innerCase_invokeMethod_classMap.get(innerCase_getMethod);
								if(null==innerCase_class){
									innerCase_class = getMethodGenericReturnType(m,0);
									innerCase_invokeMethod_classMap.put(innerCase_getMethod, innerCase_class);
								//	System.out.println(innerCase_class+"=========!!!!!!!!!!+==============!!!!!!!!!!!!!+===========!!!!!!!!!!!");
								}
							}
							
							
							
							
							
							
								}
							}	
							
							
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
				
				
				
				//----------------------inner内的级联查询----------------------
				if(null!=innerCase_iinvokeMethods_list){
					if(innerCase_iinvokeMethods_list.size()>0){
						//说明有内在的级联
						String[] innerCase_invokeMethods=new String[1];
						int size_innerCase=innerCase_iinvokeMethods_list.size();
						Class innerClass=null;
						List innerList = null;
						String everyInnerMethod=null;
//System.out.println("size_innerCase="+size_innerCase);
						for(int i=0;i<size_innerCase;i++ ){
							everyInnerMethod=innerCase_iinvokeMethods_list.get(i);

							innerCase_invokeMethods[0]=everyInnerMethod;
							innerClass=innerCase_invokeMethod_classMap.get(everyInnerMethod);
							innerList=innerCase_invokeMethod_entityList.get(everyInnerMethod);
							if(null!=innerClass && null!=innerList){
								//System.out.println("innerClass="+innerClass+";innerCase_invokeMethods="+innerCase_invokeMethods+";innerList="+innerList);
								invokeMehods_(innerClass,innerCase_invokeMethods,innerList,session);
							}
							
						}
					}
				}
				
				
				
				
				
				
				
				
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	public static Class getMethodGenericReturnType(Method method, int index) {
		Type returnType = method.getGenericReturnType();
		if (returnType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			if (index >= typeArguments.length || index < 0) {
				throw new RuntimeException("你输入的索引"
						+ (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class) typeArguments[index];
		}
		return Object.class;
	}
	
	
	
	@Override
	public void insertObject(Object o) throws Exception {
		ht.save(o);
	}

	@Override
	public void deleteObject(Object o) throws Exception {
		//ht.clear();
		ht.delete(o);
	}

	@Override
	public void updateObject(Object o) throws Exception {
		ht.update(o);
	}


	@Override
	public void j_execute(final String sql,final Object[] args) throws Exception {
		ht.execute(new  HibernateCallback() {
	           public Object doInHibernate(Session session)throws HibernateException, SQLException {
	            Query query = session.createSQLQuery(sql);
	            if(null!=args){
	            	 int size = args.length;
	 	            for(int i=0;i<size;i++ ){
	 	            	query.setParameter(i, args[i]);
	 	            }
	            }
	            return query.executeUpdate();
	          }
		});
	}

	@Override
	public List<? extends Object> j_queryObjectList(final String sql,final Object[] args) throws Exception {
		return ht.executeFind(new  HibernateCallback() {
	           public Object doInHibernate(Session session)throws HibernateException, SQLException {
		            Query query = session.createSQLQuery(sql);
		            if(null!=args){
		            	  int size = args.length;
				            for(int i=0;i<size;i++ ){
				            	query.setParameter(i, args[i]);
				           }
		            }
		            return query.list();
		          }
			});
		
	}
	@Override
	public List<? extends Object> j_queryObjectList(final Class c,final String sql,final Object[] args,boolean pager,final int startRow,final int pageSize) throws Exception {
		List<? extends Object> list=null;
		if(pager){
			list = ht.executeFind(new  HibernateCallback() {
		           public Object doInHibernate(Session session)throws HibernateException, SQLException {
		        	   Query query = session.createSQLQuery(sql).addEntity(c); 
		        	   query.setFirstResult(startRow).setMaxResults(pageSize);
			            if(null!=args){
			            	  int size = args.length;
					            for(int i=0;i<size;i++ ){
					            	query.setParameter(i, args[i]);
					           }
			            }
			            return query.list();
			          }
				});
		}else{
			list = ht.executeFind(new  HibernateCallback() {
		           public Object doInHibernate(Session session)throws HibernateException, SQLException {
		        	   Query query = session.createSQLQuery(sql).addEntity(c); 
			            if(null!=args){
			            	  int size = args.length;
					            for(int i=0;i<size;i++ ){
					            	query.setParameter(i, args[i]);
					           }
			            }
			            return query.list();
			          }
				});
		}
		return list;
	}
	

	@Override
	public Object j_queryObject(final String sql, final Object[] args) throws Exception {
		return ht.execute(new  HibernateCallback() {
	           public Object doInHibernate(Session session)throws HibernateException, SQLException {
		            Query query = session.createSQLQuery(sql);
		            if(null!=args){
		            	  int size = args.length;
				            for(int i=0;i<size;i++ ){
				            	query.setParameter(i, args[i]);
				           }
		            }
		            return query.uniqueResult();
		          }
			});
	}

	@Override
	public void h_execute(final String hql,final Object[] args) throws Exception {
		ht.execute(new  HibernateCallback() {
	           public Object doInHibernate(Session session)throws HibernateException, SQLException {
	            Query query = session.createQuery(hql);
	            if(null!=args){
	            	 int size = args.length;
	 	            for(int i=0;i<size;i++ ){
	 	            	query.setParameter(i, args[i]);
	 	            }
	            }
	            return query.executeUpdate();
	          }
		});		
	}

	@Override
	public void j_execute_batch(final Map<String, Object[]> map) throws Exception {
		ht.execute(new  HibernateCallback() {
	           public Object doInHibernate(Session session)throws HibernateException, SQLException {
	        	if(null!=map){
	        		String sql=null;
	        		Object[] args=null;
	        		Query query=null;
	        		for(Entry<String, Object[]> entry: map.entrySet()) {
	        			System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
	        			sql=entry.getKey();
	        			args=entry.getValue();
	        			query = session.createSQLQuery(sql);
	    	            if(null!=args){
	    	            	 int size = args.length;
	    	 	            for(int i=0;i<size;i++ ){
	    	 	            	query.setParameter(i, args[i]);
	    	 	            }
	    	            }
	    	            query.executeUpdate();
	        		}
	        	}
	            
	            return 0;
	          }
		});
	}



	
}
