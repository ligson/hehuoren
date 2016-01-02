package myFrameU.ibatis.dao;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import myFrameU.ibatis.init.InitConfig;
import myFrameU.ibatis.init.InitMavenImpl;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class AbstractIbatisDaoImpl implements AbstractIbatisDaoI {
	private static SqlMapClient sqlMapClient = null;
	static {
		try {
			InitConfig initConfig = InitMavenImpl.ic;
			Reader reader = Resources.getResourceAsReader(initConfig.getIbatisXMLPath());
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public Object queryForObjectById(String sqlName, int id) throws Exception {
		return sqlMapClient.queryForObject(sqlName,id);
	}

	@Override
	public Object queryForObject(String sqlName, HashMap<String, String> argsMap)
			throws Exception {
		if(null==argsMap){
			return sqlMapClient.queryForObject(sqlName);
		}else{
			return sqlMapClient.queryForObject(sqlName, argsMap);
		}
	}

	@Override
	public List<Object> queryForList(String sqlName,
			HashMap<String, String> argsMap) throws Exception {
		if(null==argsMap){
			return sqlMapClient.queryForList(sqlName);
		}else{
			return sqlMapClient.queryForList(sqlName,argsMap);
		}
		
	}

	@Override
	public void updateObject(String sqlName, HashMap<String, String> argsMap)
			throws Exception {
		if(null==argsMap){
			sqlMapClient.update(sqlName);
		}else{
			sqlMapClient.update(sqlName, argsMap);
		}
		
	}

	@Override
	public void deleteObject(String sqlName, HashMap<String, String> argsMap)
			throws Exception {
		if(null==argsMap){
			sqlMapClient.delete(sqlName);
		}else{
			sqlMapClient.delete(sqlName, argsMap);
		}
		
	}

	@Override
	public void insertObject(String sqlName, HashMap<String, String> argsMap)
			throws Exception {
		if(null==argsMap){
			sqlMapClient.insert(sqlName);
		}else{
			sqlMapClient.insert(sqlName, argsMap);
		}
	}

}
