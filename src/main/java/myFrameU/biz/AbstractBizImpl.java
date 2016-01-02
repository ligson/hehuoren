package myFrameU.biz;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import myFrameU.biz.util.ModifyProperty;
import myFrameU.biz.util.MyActionUtil;
import myFrameU.biz.util.PropertyRule;
import myFrameU.dao.AbstractDaoI;
import myFrameU.pager.pager.Pager;
import myFrameU.queryArgs.util.QueryArgsUtil;
import myFrameU.util.commonUtil.object.ObjectUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class AbstractBizImpl implements AbstractBizI {
	@Autowired
	public AbstractDaoI aDao;

	public AbstractDaoI getaDao() {
		return aDao;
	}

	public void setaDao(AbstractDaoI aDao) {
		this.aDao = aDao;
	}

	@Override
	public void addObject(Object o) throws Exception {
		aDao.insertObject(o);
	}

	@Override
	public Object findObjectById(String getOrLoad, Class c, int id,
			String[] invokeMethods) throws Exception {
		return aDao.queryObjectById(getOrLoad, c, id, invokeMethods);
	}

	@Override
	public Object findObjectById(String hql, Object[] args) throws Exception {
		return aDao.queryObjectById(hql, args);
	}

	@Override
	public List<? extends Object> findObjectList(Class c, Object[] args,
			String hql, String[] invokeMethods, boolean pager, int startRow,
			int pageSize) throws Exception {
		return aDao.queryObjectList(c, args, hql, invokeMethods, pager,
				startRow, pageSize);
	}

	@Override
	public void j_execute(String sql, Object[] args) throws Exception {
		aDao.j_execute(sql, args);
	}

	@Override
	public List<? extends Object> j_queryObjectList(String sql, Object[] args)
			throws Exception {
		return aDao.j_queryObjectList(sql, args);
	}

	@Override
	public void modifyObject(Object o) throws Exception {
		aDao.updateObject(o);
	}

	@Override
	public void removeObject(Object o) throws Exception {
		aDao.deleteObject(o);
	}

	@Override
	public Object j_queryObject(String sql, Object[] args) throws Exception {
		return aDao.j_queryObject(sql, args);
	}

	@Override
	public HibernateTemplate getHt() {
		// TODO Auto-generated method stub
		return aDao.getHt();
	}

	@Override
	public void h_execute(String hql, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		aDao.h_execute(hql, args);
	}

	@Override
	public void j_execute_batch(Map<String, Object[]> map) throws Exception {
		// TODO Auto-generated method stub
		aDao.j_execute_batch(map);
	}

	@Override
	public List<? extends Object> findEntitysByArgs_aBizSelf(Class c,
			String tableName, String queryArgs, String searchStr,
			String needCsiTitle, String needCsiList, String orderBy,
			HttpServletRequest req, boolean isPage, int returnListSize,
			String titleCsiList, String listCsiList, String entityList)
			throws Exception {
		req.setAttribute("returnListSize", returnListSize);
		String entityName = c.getName();
		List<Object> carList_list = null;
		try {
			if (null != queryArgs) {
				queryArgs = java.net.URLDecoder.decode(queryArgs, "UTF-8");
			}

			req.setAttribute("queryArgs", queryArgs);
			req.setAttribute("orderBy", orderBy);
			
			
			Map<String, String> whereMap = QueryArgsUtil.getWhere(queryArgs,req);

			String wherehql = null;
			String wheresql = null;
			String join=null;
			if (null != whereMap) {
				wherehql = whereMap.get("hql");
				wheresql = whereMap.get("sql");
				join = whereMap.get("join");
			}

			String orderByql = QueryArgsUtil.getOrder(orderBy);
			
			if(null==join){
				StringBuffer hql = new StringBuffer();
				StringBuffer sql = new StringBuffer();
				if (null == wherehql && null == orderByql) {
					// 当where和order都不存在
					hql.append("from ").append(entityName).append(" as c");
					sql.append("select count(c.id) from ").append(tableName)
							.append(" as c");
				} else if (null != wherehql && null != orderByql) {
					// 当where和order都存在
					hql.append("from ").append(entityName).append(" as c ")
							.append(wherehql)
							.append(" order by ").append(orderByql);
					sql.append("select count(c.id) from  ").append(tableName)
							.append(" as c ").append(wheresql)
							.append(" order by ").append(orderByql);
				} else if (null != wherehql && null == orderByql) {
					// 当where存在，order不存在
					hql.append("from ").append(entityName).append(" as c ")
							.append(wherehql);
					sql.append("select count(c.id) from  ").append(tableName)
							.append(" as c").append(wheresql);
				} else if (null == wherehql && null != orderByql) {
					// 当where不存在，order存在
					hql.append("from ").append(entityName).append(" as c ")
							.append(" order by ").append(orderByql);
					sql.append("select count(c.id) from  ").append(tableName)
							.append(" as c").append(" order by ").append(orderByql);
				}

				if (!isPage) {
					carList_list = (List<Object>) aDao.queryObjectList(c, null,
							hql.toString(), null, true, 0, returnListSize);
				} else {
					Pager pager = new MyActionUtil(req, "").getPager(req, aDao,
							sql.toString(), null);
					carList_list = (List<Object>) aDao.queryObjectList(c, null,
							hql.toString(), null, true, pager.getStartRow(),
							pager.getPageSize());
					
				}
				req.setAttribute(entityList, carList_list);
			}else{
				//多表链接查询，则只能hql改成sql，然后自己反射封装，因为大部分中没有级联关系。
				StringBuffer hql = new StringBuffer();
				StringBuffer sql = new StringBuffer();
				if (null == wherehql && null == orderByql) {
					// 当where和order都不存在
					hql.append("select * from ").append(tableName).append(" as c");
					sql.append("select count(c.id) from ").append(tableName).append(" as c");
				} else if (null != wherehql && null != orderByql) {
					// 当where和order都存在
					hql.append("select * from ").append(tableName).append(" as c ").append(wherehql).append(" order by ").append(orderByql);
					sql.append("select count(c.id) from  ").append(tableName).append(" as c ").append(wheresql).append(" order by ").append(orderByql);
				} else if (null != wherehql && null == orderByql) {
					// 当where存在，order不存在
					hql.append("select * from ").append(tableName).append(" as c ")
							.append(wherehql);
					sql.append("select count(c.id) from  ").append(tableName)
							.append(" as c").append(wheresql);
				} else if (null == wherehql && null != orderByql) {
					// 当where不存在，order存在
					hql.append("select * from ").append(tableName).append(" as c ")
							.append(" order by ").append(orderByql);
					sql.append("select count(c.id) from  ").append(tableName)
							.append(" as c").append(" order by ").append(orderByql);
				}

				if (!isPage) {
					carList_list = (List<Object>) aDao.j_queryObjectList(c, hql.toString(), null, true, 0, returnListSize);
				} else {
					Pager pager = new MyActionUtil(req, "").getPager(req, aDao,
							sql.toString(), null);
					carList_list = (List<Object>) aDao.j_queryObjectList(c, hql.toString(), null, true, pager.getStartRow(), pager.getPageSize());
				}
				req.setAttribute(entityList, carList_list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return carList_list;
	}

	@Override
	public List<? extends Object> findEntitysByArgs(Class c, String tableName,
			String queryArgs, String orderBy, String searchStr, boolean isPage,
			int returnListSize, String entityList, HttpServletRequest req)
			throws Exception {
		return findEntitysByArgs_aBizSelf(c, tableName, queryArgs, searchStr,
				"", "", orderBy, req, isPage, returnListSize, "", "",
				entityList);
	}

//=============================================================
	@Override
	public Object modifySimple(Object o, List<ModifyProperty> modifyPropertyList)
			throws Exception {
		if(null!=o && null!=modifyPropertyList){
			int size = modifyPropertyList.size();
			if(size>0){
				Class c = o.getClass();
				String className=c.getName();
				String tableName=EntityTableUtil.tableName(className);
				
				 Method getIdMethod = c.getDeclaredMethod("getId"); 
				 Integer id = (Integer) getIdMethod.invoke(o); 
				if(null==id){
					return null;
				}
				
				
				StringBuffer sb = new StringBuffer();
				sb.append("update ").append(tableName).append(" set ");
				
				
				ModifyProperty mp = null;
				String key=null;
				String Bkey=null;
				String value=null;
				Object OvalueObject=null;
				String Ovalue=null;
				Method getMethod=null;
				for(int i=0;i<size;i++){
					mp=modifyPropertyList.get(i);
					key=mp.getKey();
					value=mp.getValue();
					
					boolean havePowerModify=false;//默认是这个属性没有资格修改
					
					
					//第一）反射执行key的get方法，来确定和之前的值不同
					Bkey =key.replaceFirst(key.substring(0, 1), key.substring(0, 1).toUpperCase());
					getMethod = c.getDeclaredMethod("get"+Bkey); 
					OvalueObject = getMethod.invoke(o); 
					boolean isSame=ObjectUtil.same(OvalueObject, value);
					if(!isSame){
						//不同才有权力修改
						//第二）验证规则，看看最后又没有资格修改
						List<String> rulesMethodList=mp.getRuleMethodList();
						if(null!=rulesMethodList){
							int ssize=rulesMethodList.size();
							String ruleMethod=null;
							int trueCount=0;
							for(int j=0;j<ssize;j++){
								ruleMethod=rulesMethodList.get(j);
								boolean theRuleResult = PropertyRule.rule(ruleMethod, value);
								if(theRuleResult){
									trueCount++;
								}
							}
							if(trueCount==ssize){
								//全部都通过了规则验证了
								havePowerModify=true;
							}
						}else{
							//不需要任何非业务验证，直接修改。
							havePowerModify=true;
						}
					}else{
						
					}
					
					
					if(havePowerModify){
						//key=newValue,
						sb.append(key).append("=").append(value).append(",");
					}
					
				}
				
				String sbs=sb.toString();
				if(sbs.endsWith(",")){
					sbs=sbs.substring(0,sbs.length()-1);
				}
				StringBuffer sb1=new StringBuffer(sbs);
				// where id=?
				sb1.append(" where id=").append(id);
				
				System.out.println(sb1.toString()+"@@@@@@@@@@@@@~~~~~~~~~~~~~~~~~~~~~~~~~~");
				
				aDao.j_execute(sb1.toString(), null);
				o=aDao.queryObjectById("from "+className+" where id=?", new Object[]{id});
				return o;
			}
		}
		return o;
	}

	@Override
	public List<? extends Object> j_queryObjectList(Class c, String sql,
			Object[] args, boolean pager, int startRow, int pageSize)
			throws Exception {
		return aDao.j_queryObjectList(c, sql, args, pager, startRow, pageSize);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
