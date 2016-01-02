package myFrameU.ehcache.loadData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myFrameU.ehcache.util.UICacheManager;

public interface LoadCacheDataI {
	public HashMap<String,String> getArgsMapFromArgs() throws Exception;
	public List loadData_list(UICacheManager uICacheManager) throws Exception;
	public Map<String,Object> createArgs() throws Exception;
	public Object loadData_one(UICacheManager uICacheManager) throws Exception;
	public HashMap loadData_map(UICacheManager uICacheManager) throws Exception;
}
