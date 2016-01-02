package myFrameU.ehcache.loadData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myFrameU.ehcache.util.UICacheManager;

public class AbstractLoadCacheDataImpl implements LoadCacheDataI {
	private static HashMap<String,String> argsMap = new HashMap<String,String>();
	private String cacheName;
	private String key;
	private int size;
	private String hqlName;
	private String args;
	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHqlName() {
		return hqlName;
	}

	public void setHqlName(String hqlName) {
		this.hqlName = hqlName;
	}

	public List loadData_list(UICacheManager uICacheManager)
			throws Exception {
		return null;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Map<String, Object> createArgs() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public Object loadData_one(UICacheManager uICacheManager)
			throws Exception {
		return null;
	}

	public HashMap loadData_map(UICacheManager uICacheManager)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

	public HashMap<String, String> getArgsMapFromArgs()
			throws Exception {
		if(null!=args && !args.equals("")){
			//正在判断形式,先不
			String[] array=null;
			if(args.contains("\\|")){
				array=args.split("\\|");
			}else{
				array=new String[]{args};
			}
			int len=array.length;
			String arg=null;
			String[] selfArray=null;
			for(int i=0;i<len;i++){
				arg=array[i];
				if(null!=arg && !arg.equals("")){
					if(arg.contains("=")){
						selfArray=arg.split("=");
						if(selfArray.length==2){
							argsMap.put(selfArray[0], selfArray[1]);
						}
					}
				}
			}
			
		}
		return argsMap;
	}

}
