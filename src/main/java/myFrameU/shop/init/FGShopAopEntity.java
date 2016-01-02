package myFrameU.shop.init;

import java.util.Map;

public class FGShopAopEntity {
	private String cacheName;
	private String prefix;
	//dataName - cahceKey
	private Map<String,String> cacheKeyMap;
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Map<String, String> getCacheKeyMap() {
		return cacheKeyMap;
	}
	public void setCacheKeyMap(Map<String, String> cacheKeyMap) {
		this.cacheKeyMap = cacheKeyMap;
	}
	
}
