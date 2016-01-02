package myFrameU.queryArgs.util;

import java.util.HashMap;

import myFrameU.shop.entity.Shop;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

public class OtherEntityUtil {
	public enum QueryArgsJOIN{
		jk_sc_shop("yishupaipai.shop.entity.Shop",EntityTableUtil.tableNameC(Shop.class)+""," left join "," as jk_sc_shop "," on c.scEntityId=jk_sc_shop.id ");
		
		
		private String className;
		private String table;
		private String joinType;
		private String as;
		private String on;
		QueryArgsJOIN(String className,String table,String joinType,String as,String on){
			this.className=className;
			this.table=table;
			this.joinType=joinType;
			this.as=as;
			this.on=on;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getTable() {
			return table;
		}
		public void setTable(String table) {
			this.table = table;
		}
		public String getJoinType() {
			return joinType;
		}
		public void setJoinType(String joinType) {
			this.joinType = joinType;
		}
		public String getAs() {
			return as;
		}
		public void setAs(String as) {
			this.as = as;
		}
		public String getOn() {
			return on;
		}
		public void setOn(String on) {
			this.on = on;
		}
		
		
	}
	public static HashMap<String,QueryArgsJOIN> queryArgsJOINMap=new HashMap<String,QueryArgsJOIN>();
	static{
		queryArgsJOINMap.put("jk_sc_shop", QueryArgsJOIN.jk_sc_shop);
	}
}
