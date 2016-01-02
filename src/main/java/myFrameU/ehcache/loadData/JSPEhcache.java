package myFrameU.ehcache.loadData;
import java.util.HashMap;
import java.util.List;

import myFrameU.ehcache.util.UICacheManager;


public class JSPEhcache {
	
	private static UICacheManager ucache;


	public UICacheManager getUcache() {
		return ucache;
	}

	public void setUcache(UICacheManager ucache) {
		this.ucache = ucache;
	}
	public static List list_args(String cacheName,String key,String impl,Integer size,String args){
		List list =null;
		try{
			list = (List)ucache.getObjectCached(cacheName, key);
			if(null!=list){
				
			}else{
				impl="myFrame.ehcache.jsp.loadImpl."+impl;
				Class implC=Class.forName(impl);
				AbstractLoadCacheDataImpl alcdi=(AbstractLoadCacheDataImpl)implC.newInstance();
				alcdi.setCacheName(cacheName);
				alcdi.setKey(key);
				alcdi.setSize(size);
				alcdi.setArgs(args);
				list=alcdi.loadData_list(ucache);
				System.out.println("这是通过jspEhcache标签获取缓存中的数据,没有获取到,重新去aBiz去数据库读取"+cacheName+"!!!!!!!!!!!!!!"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static List list_hql(String cacheName,String key,String impl,Integer size,String hqlName){
		List list =null;
		try{
			list = (List)ucache.getObjectCached(cacheName, key);
			if(null!=list){
				
			}else{
				impl="myFrame.ehcache.jsp.loadImpl."+impl;
				Class implC=Class.forName(impl);
				AbstractLoadCacheDataImpl alcdi=(AbstractLoadCacheDataImpl)implC.newInstance();
				alcdi.setCacheName(cacheName);
				alcdi.setKey(key);
				alcdi.setSize(size);
				alcdi.setHqlName(hqlName);
				list=alcdi.loadData_list(ucache);
				System.out.println("这是通过jspEhcache标签获取缓存中的数据,没有获取到,重新去aBiz去数据库读取"+cacheName+"!!!!!!!!!!!!!!"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/*public static List list(String cacheName,String key,String impl,Integer size){
		List list =null;
		try{
			list = (List)ucache.getObjectCached(cacheName, key);
			if(null!=list){
				
			}else{
				impl="myFrame.ehcache.jsp.loadImpl."+impl;
				Class implC=Class.forName(impl);
				AbstractLoadCacheDataImpl alcdi=(AbstractLoadCacheDataImpl)implC.newInstance();
				alcdi.setCacheName(cacheName);
				alcdi.setKey(key);
				alcdi.setSize(size);
				list=alcdi.loadData_list(ucache, aBiz);
				System.out.println("这是通过jspEhcache标签获取缓存中的数据,没有获取到,重新去aBiz去数据库读取"+cacheName+"!!!!!!!!!!!!!!"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}*/
	
	
	//加上addressId
	/*public static List list(String cacheName,String key,String impl,Integer size,Integer addressId){
		List list =null;
		try{
			if(null==addressId || addressId==0){
			}else{
				key=addressId+"_"+key;
			}
			list = (List)ucache.getObjectCached(cacheName, key);
			if(null!=list){
				
			}else{
				impl="myFrame.ehcache.jsp.loadImpl."+impl;
				Class implC=Class.forName(impl);
				AbstractLoadCacheDataImpl alcdi=(AbstractLoadCacheDataImpl)implC.newInstance();
				alcdi.setCacheName(cacheName);
				alcdi.setKey(key);
				alcdi.setSize(size);
				alcdi.setAddressId(addressId);
				list=alcdi.loadData_list(ucache, aBiz);
				System.out.println("这是通过jspEhcache标签获取缓存中的数据,没有获取到,重新去aBiz去数据库读取"+cacheName+"!!!!!!!!!!!!!!"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	*/
	
	
	//加上传递参数,以前都是通过key来的特征标志,但是那样一个是麻烦,并且很难看明白
	//args的格式为key=value|key=value
	//传过来之后，自动组成map<String,String>
	public static List list(String cacheName,String key,String impl,Integer size,Integer addressId,String args){
		List list =null;
		try{
			if(null==addressId || addressId==0){
			}else{
				key=addressId+"_"+key;
			}
			if(null!=args && !args.equals("") && args.contains("=")){
				key=args+"_"+key;
			}
			list = (List)ucache.getObjectCached(cacheName, key);
			if(null!=list){
				
			}else{
				impl="myFrame.ehcache.jsp.loadImpl."+impl;
				Class implC=Class.forName(impl);
				AbstractLoadCacheDataImpl alcdi=(AbstractLoadCacheDataImpl)implC.newInstance();
				alcdi.setCacheName(cacheName);
				alcdi.setKey(key);
				alcdi.setSize(size);
				alcdi.setArgs(args);
				
				list=alcdi.loadData_list(ucache);
				System.out.println("这是通过jspEhcache标签获取缓存中的数据,没有获取到,重新去aBiz去数据库读取"+cacheName+"!!!!!!!!!!!!!!"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static HashMap map(String cacheName,String key,String impl,Integer size){
		HashMap list =null;
		try{
			list = (HashMap)ucache.getObjectCached(cacheName, key);
			if(null!=list){
				
			}else{
				impl="myFrame.ehcache.jsp.loadImpl."+impl;
				Class implC=Class.forName(impl);
				AbstractLoadCacheDataImpl alcdi=(AbstractLoadCacheDataImpl)implC.newInstance();
				alcdi.setCacheName(cacheName);
				alcdi.setKey(key);
				alcdi.setSize(size);
				list=alcdi.loadData_map(ucache);
				System.out.println("这是通过jspEhcache标签获取缓存中的数据,没有获取到,重新去aBiz去数据库读取"+cacheName+"!!!!!!!!!!!!!!"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static Object one(String cacheName,String key,String impl){
		Object one =null;
		try{
			one = (Object)ucache.getObjectCached(cacheName, key);
			if(null!=one){
				
			}else{
				impl="myFrame.ehcache.jsp.loadImpl."+impl;
				Class implC=Class.forName(impl);
				AbstractLoadCacheDataImpl alcdi=(AbstractLoadCacheDataImpl)implC.newInstance();
				alcdi.setCacheName(cacheName);
				alcdi.setKey(key);
				one=alcdi.loadData_one(ucache);
				System.out.println("这是通过jspEhcache标签获取缓存中的数据,没有获取到,重新去aBiz去数据库读取"+cacheName+"!!!!!!!!!!!!!!"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return one;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
}
