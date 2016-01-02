package myFrameU.queryArgs.util;

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
					operators=" in";
					value="("+value+") ";
					qa.setOperators(operators);
					qa.setValue(value);
				}else if(operators.equals("not in")){
					operators=" not in";
					value="("+value+") ";
					qa.setOperators(operators);
					qa.setValue(value);
				}
				
				if(null==fieldType || fieldType.equals("")){
				
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
	public static Map<String,String> getWhere(String queryArgs,HttpServletRequest req) throws Exception{
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
			
			for(int i=0;i<size;i++){
				qa=queryArgsList.get(i);
				key=qa.getKey();
				value=qa.getValue();
				operators=qa.getOperators();
				nextRel=qa.getNextRel();
				if(operators.equals("daterange")){
				
				
				
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
			}
		}else{
			//return null;
		}
		
		
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
			if(orderBySB_str.equals("")){
				//return null;
			}else{
				return orderBySB_str;
			}
			
		}else{
		
	}
	
	
	
	
	
	
	
	
	//获取用户角色的queryArgs
	public static String getRoleQueryArgs(String shopIdName,String userIdName,String adminIdName,HttpServletRequest req){
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}