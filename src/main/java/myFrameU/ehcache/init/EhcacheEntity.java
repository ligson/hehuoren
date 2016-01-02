package myFrameU.ehcache.init;

public class EhcacheEntity {
	private String cacheName;
	private String cacheKey;
	private String loadClass;
	private boolean againLoad;
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	public String getLoadClass() {
		return loadClass;
	}
	public void setLoadClass(String loadClass) {
		this.loadClass = loadClass;
	}
	public boolean getAgainLoad() {
		return againLoad;
	}
	public void setAgainLoad(boolean againLoad) {
		this.againLoad = againLoad;
	}
	
	
}
