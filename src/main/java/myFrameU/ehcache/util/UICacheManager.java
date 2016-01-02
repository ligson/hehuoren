package myFrameU.ehcache.util;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 缓存管理器
 */
public class UICacheManager {

	final static Log log = LogFactory.getLog(UICacheManager.class);

	public CacheManager manager;

	public CacheManager getManager() {
		return manager;
	}

	public void setManager(CacheManager manager) {
		this.manager = manager;
	}

	/**
	 * 从缓存中获取对象
	 * 
	 * @param cache_name
	 * @param key
	 * @return
	 */
	public Serializable getObjectCached(String cache_name, Serializable key) {

		Cache cache = getCache(cache_name);
		if (cache != null) {
			try {
				Element elem = cache.get(key);
				if (elem != null && !cache.isExpired(elem))
					return elem.getValue();
			} catch (Exception e) {
				log.error("Get cache(" + cache_name + ") of " + key
						+ " failed.", e);
			}
		}
		return null;
	}

	public synchronized void removeElement(String cache_name, Serializable key) {
		Cache cache = getCache(cache_name);
		try {
			if (cache != null) {
				Object value = (Object) getObjectCached(cache_name, key);
				/*
				 * if(value instanceof List){ List valueList = (List)value; int
				 * size = valueList.size(); Object myo=null; Method m = null;
				 * Integer id=0; for(int i=0;i<size;i++){ myo=valueList.get(i);
				 * m=myo.getClass().getMethod("getId", null); id=(Integer)
				 * m.invoke(myo, null); myo=valueList.get(i);
				 * removeElement("listDataKey", cache_name+":"+key); } }
				 */
				cache.remove(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把对象放入缓存中
	 * 
	 * @param cache_name
	 * @param key
	 * @param value
	 */
	public synchronized void putObjectCached(String cache_name,
			Serializable key, Serializable value) {
		Cache cache = getCache(cache_name);
		if (cache != null) {
			try {
				cache.remove(key);
				Element elem = new Element(key, value);
				cache.put(elem);
				/*
				 * if(value instanceof List){ List valueList = (List)value; int
				 * size = valueList.size(); Object myo=null; Method m = null;
				 * Integer id=0; for(int i=0;i<size;i++){ myo=valueList.get(i);
				 * m=myo.getClass().getMethod("getId", null); id=(Integer)
				 * m.invoke(myo, null); myo=valueList.get(i);
				 * putObjectCached("listDataKey", cache_name+":"+key,
				 * cache_name+":"+key+":"+id); } }
				 */
			} catch (Exception e) {
				e.printStackTrace();
				log.error("put cache(" + cache_name + ") of " + key
						+ " failed.", e);
			}
		}
	}

	/**
	 * 获取指定名称的缓存
	 * 
	 * @param arg0
	 * @return
	 * @throws IllegalStateException
	 */
	public Cache getCache(String arg0) throws IllegalStateException {
		return manager.getCache(arg0);
	}

	/**
	 * 获取缓冲中的信息
	 * 
	 * @param cache
	 * @param key
	 * @return
	 * @throws IllegalStateException
	 * @throws CacheException
	 */
	public Element getElement(String cache, Serializable key)
			throws IllegalStateException, CacheException {
		Cache cCache = getCache(cache);
		return cCache.get(key);
	}

	/**
	 * 停止缓存管理器
	 */
	public void shutdown() {
		if (manager != null)
			manager.shutdown();
	}

	public void printEhCache(String cache) {
		System.out.println("以下是对" + cache + "缓存区内的监控数据：");
		Cache cCache = getCache(cache);
		System.out.println("对象数：" + cCache.getSize());
		System.out.println("占据内存：" + cCache.getMemoryStoreSize());
		System.out.println("命中次数：" + cCache.getStatistics().getCacheHits());
		System.out.println("错失次数：" + cCache.getStatistics().getCacheMisses());

	}

	public void clearAllDatas(String cacheName) {
		System.out.println("执行了clearAllDatas");
		Cache cCache = getCache(cacheName);
		List<String> keysList = cCache.getKeys();
		int size = keysList.size();
		String key = null;
		for (int i = 0; i < size; i++) {
			key = keysList.get(i);
			removeElement(cacheName, key);
		}
	}

	/**
	 * 创建一个cache
	 * 
	 * @param cacheName
	 * @return
	 */
	private Cache createCache(String cacheName,int timeToIdleSeconds, int timeToLiveSeconds,boolean eternal
			,int maxElementsInMemory,int maxElementsOnDisk,boolean overflowToDisk,boolean diskPersistent,
			int diskExpiryThreadIntervalSeconds,String memoryStoreEvictionPolicy) {
		Cache cache = manager.getCache(cacheName);
		CacheConfiguration config = cache.getCacheConfiguration();
		config.setMaxElementsInMemory(maxElementsInMemory);
		config.setEternal(eternal);
		config.setTimeToIdleSeconds(timeToIdleSeconds);
		config.setTimeToLiveSeconds(timeToLiveSeconds);
		config.setOverflowToDisk(overflowToDisk);
		config.setMaxElementsOnDisk(maxElementsOnDisk);
		config.setDiskPersistent(diskPersistent);
		config.setDiskExpiryThreadIntervalSeconds(diskExpiryThreadIntervalSeconds);
		config.setMemoryStoreEvictionPolicy(memoryStoreEvictionPolicy);
		return cache;
	}
	
	
	
	public Cache createCache(String cacheName,boolean eternal){
		return createCache(cacheName,120,120,eternal,10000,10000000,true,false,120,"LRU");
	}
	
	
	
	
	
	
	
	

}
