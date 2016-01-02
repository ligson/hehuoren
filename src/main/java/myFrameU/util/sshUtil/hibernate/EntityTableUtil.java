package myFrameU.util.sshUtil.hibernate;

import myFrameU.util.sshUtil.init.InitMavenImpl;

public class EntityTableUtil {
	public static String tableName(String entityName){
		return InitMavenImpl.ic.getEntityTableMap().get(entityName);
	}
	
	public static String tableNameC(Class c ){
		String entityName=c.getName();
		return InitMavenImpl.ic.getEntityTableMap().get(entityName);
	}
}
