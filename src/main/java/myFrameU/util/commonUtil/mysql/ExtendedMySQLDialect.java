package myFrameU.util.commonUtil.mysql;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class ExtendedMySQLDialect extends MySQLDialect {
	/*
	 * 使用方式 date_add(now(), 1, WEEK)
	 */
	public ExtendedMySQLDialect() {
		super();
		
		//date_add函数的原型：date_add(now(), 1, WEEK) 
		//除了week还有
		/**
		 * select date_add(@dt, interval 1 day);   - 加1天
select date_add(@dt, interval 1 hour);   -加1小时
select date_add(@dt, interval 1 minute);    - 加1分钟
select date_add(@dt, interval 1 second); -加1秒
select date_add(@dt, interval 1 microsecond);-加1毫秒
select date_add(@dt, interval 1 week);-加1周
select date_add(@dt, interval 1 month);-加1月
select date_add(@dt, interval 1 quarter);-加1季
select date_add(@dt, interval 1 year);-加1年
		 */
		registerFunction("date_add", new SQLFunctionTemplate(Hibernate.DATE, "date_add(?1, INTERVAL ?2 ?3)"));
		registerFunction("date_sub", new SQLFunctionTemplate(Hibernate.DATE, "date_sub(?1, INTERVAL ?2 ?3)"));
		registerFunction("sql_left", new SQLFunctionTemplate(Hibernate.STRING, "left(?1,?2)"));
		registerFunction("sql_right", new SQLFunctionTemplate(Hibernate.STRING, "right(?1,?2)"));
		
		//registerFunction("to_days", new SQLFunctionTemplate(Hibernate.DATE, "to_days(?1)"));
		
		registerFunction( "regexp", new SQLFunctionTemplate(Hibernate.BOOLEAN, "?1 REGEXP ?2") );
		registerFunction("sql_zuhe", new SQLFunctionTemplate(Hibernate.BOOLEAN, "?1 REGEXP ?2") );
		
		registerHibernateType(Types.REAL, Hibernate.DOUBLE.getName());  
		
		
		registerHibernateType(-4, Hibernate.STRING.getName());
		registerHibernateType(-1, Hibernate.STRING.getName());
		
	}
}