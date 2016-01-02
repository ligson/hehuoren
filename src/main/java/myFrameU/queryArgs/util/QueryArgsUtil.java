package myFrameU.queryArgs.util;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import javax.servlet.http.HttpServletRequest;import myFrameU.admin.entity.Admin;import myFrameU.expand.use.entity.JSONPropertyValues;import myFrameU.queryArgs.form.QueryArgs;import myFrameU.queryArgs.form.QueryOrderBys;import myFrameU.queryArgs.util.OtherEntityUtil.QueryArgsJOIN;import myFrameU.shop.entity.Shop;import myFrameU.spring.mvc.CommonField;import myFrameU.user.entity.User;import myFrameU.util.commonUtil.json.JSONUtils;import myFrameU.util.commonUtil.json.JsonValidator;

public class QueryArgsUtil {
	private static List<QueryArgs> filterQueryArgs(List<QueryArgs> queryArgsList){
		List<QueryArgs> queryArgsList_ok = new ArrayList<QueryArgs>();
		int size = queryArgsList.size();
		QueryArgs qa=null;
		String key=null;
		String value=null;
		String nextRel = null;
		String fieldType=null;
		String operators=null;
		boolean saveToOkList=true;
		for(int i=0;i<size;i++){
			qa=queryArgsList.get(i);
			key= qa.getKey();
			value = qa.getValue();
			if(null!=key && !key.equals("") && null!=value && !value.equals("")){
				
				nextRel=qa.getNextRel();
				fieldType=qa.getFiledType();
				operators=qa.getOperators();
				
				if(nextRel==null || nextRel.equals("")){
					//如果没有写明这个关系，默认是and
					qa.setNextRel("and");
				}
				
				
				//处理运算符。
				if(null==operators || operators.equals("")){
					operators="=";
				}else if(operators.equals("like")){
					value=value.replaceAll("'", "");
					value="%"+value+"%";
					qa.setOperators(" like ");
					qa.setValue(value);
				}else if(operators.equals("lastlike")){
					value=value.replaceAll("'", "");
					value=value+"%";
					qa.setOperators(" like ");
					qa.setValue(value);
				}else if(operators.equals("in")){
					operators=" in";					if(null==fieldType || fieldType.equals("") || fieldType.equals("varchar")){						//字符串.END,FINISH-->'END','FINISH'						value=value.replaceAll(",", "','");						value="'"+value+"'";					}
					value="("+value+") ";
					qa.setOperators(operators);
					qa.setValue(value);
				}else if(operators.equals("not in")){
					operators=" not in";					if(null==fieldType || fieldType.equals("") || fieldType.equals("varchar")){						//字符串.END,FINISH-->'END','FINISH'						value=value.replaceAll(",", "','");						value="'"+value+"'";					}
					value="("+value+") ";
					qa.setOperators(operators);
					qa.setValue(value);
				}
				
				if(null==fieldType || fieldType.equals("")){					qa.setFiledType("varchar");					if(value.contains("'")){					}else{						qa.setValue("'"+qa.getValue()+"'");					}				}								//处理value，如果value中是用|链接的，则认定为是or												
				
			}else{
				saveToOkList=false;
			}
			if(saveToOkList){
				queryArgsList_ok.add(qa);
			}
		}
		return queryArgsList_ok;
	}
	//[{'key':'proType','value':'2','filedType':'int'},{'key':'status','value':'1','filedType':'int'}]
	public static Map<String,String> getWhere(String queryArgs,HttpServletRequest req) throws Exception{		Map<String,String> whereMap=null;		whereMap=new HashMap<String,String>();		StringBuffer where_hql=new StringBuffer();		StringBuffer where_sql=new StringBuffer();
		if(null!=queryArgs){
			if(queryArgs.equals("null")){
				queryArgs=null;
				return null;
			}
			queryArgs=queryArgs.replace(".jsp", "");
			//1第一步先检查传递进来的参数格式是否正确
			if(!queryArgs.equals("")){
				queryArgs=queryArgs.replaceAll("'", "\"");
				boolean jsonHege=JsonValidator.validate(queryArgs);
				if(!jsonHege){
					throw new Exception("传进来的queryArgs非json格式出错");
				}
			}
			
			
			
			List<QueryArgs> queryArgsList = JSONUtils.toBeanList(queryArgs, QueryArgs.class);
			queryArgsList=filterQueryArgs(queryArgsList);
			
			
			int size = queryArgsList.size();
			QueryArgs qa=null;
			String key=null;
			String value=null;
			String operators=null;
			String nextRel=null;
									/**			 * 1、在以下循环中判断是否含有除了c.之外的别称，如果有则意味着是有其他的表			 * 2、在OtherEntityUtil中查询XXX.这个别称，看看这个XXX为key的到底是关联哪个表，哪个实体类			 * 	3、XXX必须是以joinkey（jk_）开头			 * 4、join的on的级联关系中，必须是以C.xxx=JK_xxx.这样的格式，就是C原表必须在前面			 */			boolean joinOtherEntity=false;			String joinKey=null;			String[] keyArray=null;
			for(int i=0;i<size;i++){				String withAs="c.";
				qa=queryArgsList.get(i);
				key=qa.getKey();
				value=qa.getValue();
				operators=qa.getOperators();
				nextRel=qa.getNextRel();								//处理key				String sql_key=null;				String hql_key=null;				if(key.startsWith("jk_")){					joinOtherEntity=true;					//jk_sp.status					//jk_sp.account.id					keyArray=key.split("\\.");					if(null!=keyArray){						int len=keyArray.length;						if(len==2 || len==3){							joinKey=keyArray[0];						}					}					withAs="";				}								if(key.contains("|")){					//这种情况说明，数据库表字段名和实体类名不同，如外键。cb.id|cb_id					String[] sql_hql_key_array=key.split("\\|");					if(null!=sql_hql_key_array){						if(sql_hql_key_array.length==2){							sql_key=sql_hql_key_array[1];							hql_key=sql_hql_key_array[0];						}					}				}else{					//不存在的话，那么sql_key和hql_key就是key					sql_key=key;					hql_key=key;				}								
				if(operators.equals("daterange")){					//日期区间					/**					 * 1、传递过来的开始时间-结束时间，中间用-隔开。					 * 2、开始时间完善为00:00:00，结束时间完善为23:59:59					 */					if(value.contains("_")){						value=value.replaceAll("'", "");						String[] array = value.split("_");						if(null!=array){							int len = array.length;							if(len==2){								String beginDates = array[0]+" 00:00:00";								String endDates = array[1]+" 23:59:59";								where_hql.append(withAs).append(hql_key).append(" between '").append(beginDates).append("' and '").append(endDates).append("' ").append(nextRel).append(" ");								where_sql.append(withAs).append(sql_key).append(" between '").append(beginDates).append("' and '").append(endDates).append("' ").append(nextRel).append(" ");															}else if(len==1){															}						}					}				}else{					where_hql.append(withAs).append(hql_key).append(operators).append(value).append(" ").append(nextRel).append(" ");					where_sql.append(withAs).append(sql_key).append(operators).append(value).append(" ").append(nextRel).append(" ");									}
				
				
				
			}
									
			String where_hql_str=where_hql.toString();
			String where_sql_str=where_sql.toString();
			if(where_hql_str.endsWith(" and ")){
				where_hql_str=where_hql_str.substring(0,where_hql_str.length()-5);
			}else if(where_hql_str.endsWith(" or ")){
				where_hql_str=where_hql_str.substring(0,where_hql_str.length()-4);
			}
			if(where_sql_str.endsWith(" and ")){
				where_sql_str=where_sql_str.substring(0,where_sql_str.length()-5);
			}else if(where_sql_str.endsWith(" or ")){
				where_sql_str=where_sql_str.substring(0,where_sql_str.length()-4);
			}									if(joinOtherEntity){				//如果存在join其他表，则去OtherEntityUtil里去查找哪个表哪个类				if(null!=joinKey){					QueryArgsJOIN qajoin=OtherEntityUtil.queryArgsJOINMap.get(joinKey);					if(null!=qajoin){						whereMap.put("join", "yes");						StringBuffer lastHQLSB = new StringBuffer();						lastHQLSB.append(" ").append(qajoin.getJoinType()).append(" ").append(qajoin.getTable()).append(qajoin.getAs()).append(qajoin.getOn()).append(" where ").append(where_sql_str);						StringBuffer lastSQLSB = new StringBuffer();						lastSQLSB.append(" ").append(qajoin.getJoinType()).append(" ").append(qajoin.getTable()).append(qajoin.getAs()).append(qajoin.getOn()).append(" where ").append(where_sql_str);						where_hql_str=lastHQLSB.toString();						where_sql_str=lastSQLSB.toString();						System.out.println("where_hql_str="+where_hql_str);						System.out.println("where_sql_str="+where_sql_str);						whereMap.put("hql", where_hql_str);						whereMap.put("sql", where_sql_str);											}				}			}else{				whereMap.put("hql", " where "+where_hql_str);				whereMap.put("sql", " where "+where_sql_str);			}			
		}else{
			//return null;
		}				//处理扩展属性		//[{'pId':'3','pvId':'25'}]		String qap= (String)req.getAttribute("queryArgsProperty");		if(null!=qap && !qap.equals("") && !qap.equals("[]")){			String hql = whereMap.get("hql");			String sql = whereMap.get("sql");			StringBuffer hqlSB=null;			StringBuffer sqlSB=null;			if(null!=hql){				hqlSB=new StringBuffer(hql);			}else{				hqlSB=new StringBuffer("");			}						if(null!=sql){				sqlSB=new StringBuffer(sql);			}else{				sqlSB=new StringBuffer("");			}												qap=qap.replaceAll("'", "\"");			List<JSONPropertyValues> list = JSONUtils.toBeanList(qap, JSONPropertyValues.class);			int size = list.size();			JSONPropertyValues jsonp=null;			String jsonString=null;			for(int kkk=0;kkk<size;kkk++){				jsonp=list.get(kkk);				if(null!=jsonp){					String pvId=jsonp.getPvId();					jsonString=jsonp.toString();					jsonString=jsonString.replaceAll("'", "''");					if(null==hql && null==sql){						if(pvId.contains(",")){							//checkbox							// '{''pId'':''3'',''pvId'':''.*13.*14.*''}'														hqlSB.append(" regexp(c.propertyValues_Ids,'").append(jsonString).append("'").append(")=1").append(" and ");							sqlSB.append(" c.propertyValues_Ids REGEXP '").append(jsonString).append("'").append(" and ");						}else{							hqlSB.append(" regexp(c.propertyValues_Ids,'").append(jsonString).append("'").append(")=1").append(" and ");							sqlSB.append(" c.propertyValues_Ids REGEXP '").append(jsonString).append("'").append(" and ");						}					}else{						if(pvId.contains(",")){							//checkbox							hqlSB.append(" and 	regexp(c.propertyValues_Ids,'").append(jsonString).append("'").append(")=1")/*.append(" and ")*/;							sqlSB.append(" and 	c.propertyValues_Ids REGEXP '").append(jsonString).append("'")/*.append(" and ")*/;						}else{							hqlSB.append(" and 	regexp(c.propertyValues_Ids,'").append(jsonString).append("'").append(")=1")/*.append(" and ")*/;							sqlSB.append(" and 	c.propertyValues_Ids REGEXP '").append(jsonString).append("'")/*.append(" and ")*/;						}					}									}			}									String hqls_=hqlSB.toString();			String sqls_=sqlSB.toString();			if(hqls_.endsWith(" and ")){				hqls_=hqls_.substring(0,hqls_.length()-5);			}			if(sqls_.endsWith(" and ")){				sqls_=sqls_.substring(0,sqls_.length()-5);			}						whereMap.put("hql", hqls_);			whereMap.put("sql", sqls_);									/*whereMap.put("hql", " regexp(c.propertyValues_Ids,'{''pId:3,pvId=25)')=1");			whereMap.put("sql", " c.propertyValues_Ids REGEXP '(pId=3,pvId=25)'");			*/						System.out.println("queryArgsUtils========where=hql="+hqls_+"===ddddddddddddddddddddddddddddddd");					}				
		
		
		return whereMap;
	}
	
	
	
	public static String getOrder(String orderBy) throws Exception{
		StringBuffer orderBySB=null;
		if(null!=orderBy && !orderBy.equals("")){
			orderBy=orderBy.replaceAll("'", "\"");
			boolean jsonHege1=JsonValidator.validate(orderBy);
			if(!jsonHege1){
				throw new Exception("传进来的orderBy非json格式出错");
			}
			orderBySB=new StringBuffer();
			
			List<QueryOrderBys> orderByList = JSONUtils.toBeanList(orderBy, QueryOrderBys.class);
			int size = orderByList.size();
			QueryOrderBys qo = null;
			String qoField=null;
			String field=null;
			String ad=null;
			for(int i=0;i<size;i++){
				qo=orderByList.get(i);
				field=qo.getField();
				ad=qo.getAd();
				orderBySB.append("c.").append(field).append(" ").append(ad).append(",");
			}
			String orderBySB_str=orderBySB.toString();
			if(orderBySB_str.endsWith(",")){
				orderBySB_str=orderBySB_str.substring(0,orderBySB_str.length()-1);
			}
			if(orderBySB_str.equals("")){				//默认order by id desc				return " c.id desc";
				//return null;
			}else{
				return orderBySB_str;
			}
			
		}else{			return " c.id desc";		}
		
	}
	
	
	
	
	
	
	
	
	//获取用户角色的queryArgs
	public static String getRoleQueryArgs(String shopIdName,String userIdName,String adminIdName,HttpServletRequest req){		String queryArgs = (String)req.getParameter(CommonField.queryArgs);		String role = (String) req.getAttribute(CommonField.roleClassName);				if(role.equals(Shop.class.getName())){			if(null!=shopIdName && !shopIdName.equals("")){				Shop shop = (Shop)req.getSession().getAttribute("myShop");				if(null!=queryArgs){					String containsS="{'key':'"+shopIdName+"','value':'"+shop.getId()+"','filedType':'int'}";					if(!queryArgs.contains(containsS)){						queryArgs=queryArgs.substring(0,queryArgs.length()-1);						queryArgs=queryArgs+","+containsS+"]";						//queryArgs=queryArgs+","+"{'key':'"+shopIdName+"','value':'"+shop.getId()+"','filedType':'int'}]";					}				}else{					queryArgs="[{'key':'"+shopIdName+"','value':'"+shop.getId()+"','filedType':'int'}]";				}			}		}else if(role.equals(User.class.getName())){			if(null!=userIdName && !userIdName.equals("")){				User user = (User)req.getSession().getAttribute("myUser");				if(null!=queryArgs){					String containsS="{'key':'"+userIdName+"','value':'"+user.getId()+"','filedType':'int'}";					if(!queryArgs.contains(containsS)){						queryArgs=queryArgs.substring(0,queryArgs.length()-1);						//queryArgs=queryArgs+","+"{'key':'"+userIdName+"','value':'"+user.getId()+"','filedType':'int'}]";						queryArgs=queryArgs+","+containsS+"]";					}				}else{					queryArgs="[{'key':'"+userIdName+"','value':'"+user.getId()+"','filedType':'int'}]";				}			}		}else if(role.equals(Admin.class.getName())){			if(null!=adminIdName && !adminIdName.equals("")){				Admin admin = (Admin)req.getSession().getAttribute("myAdmin");				if(null!=queryArgs){					String containsS="{'key':'"+adminIdName+"','value':'"+admin.getId()+"','filedType':'int'}";					if(queryArgs.contains(containsS)){						queryArgs=queryArgs.substring(0,queryArgs.length()-1);						queryArgs=queryArgs+","+containsS+"]";					}				}else{					queryArgs="[{'key':'"+adminIdName+"','value':'"+admin.getId()+"','filedType':'int'}]";				}			}		}				return queryArgs;	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
