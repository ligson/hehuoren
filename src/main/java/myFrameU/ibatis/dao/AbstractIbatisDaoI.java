package myFrameU.ibatis.dao;

import java.util.HashMap;
import java.util.List;

public interface AbstractIbatisDaoI {
	public Object queryForObjectById(String sqlName,int id) throws Exception;
	public Object queryForObject(String sqlName,HashMap<String,String> argsMap) throws Exception;
	
	
	public List<Object> queryForList(String sqlName,HashMap<String,String> argsMap) throws Exception;
	
	
	public void updateObject(String sqlName,HashMap<String,String> argsMap) throws Exception;
	
	public void deleteObject(String sqlName,HashMap<String,String> argsMap) throws Exception;
	
	public void insertObject(String sqlName,HashMap<String,String> argsMap) throws Exception;
	
	
}
