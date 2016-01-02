package myFrame.cache;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.util.commonUtil.collection.MyCollectionUtils;

public interface CacheKey {
	
	static final String web="web";
	<T> HashMap<String,T> getMap(UICacheManager uICacheManager);
	<T> T getObject(UICacheManager uICacheManager,int id);
	<T> List<T> getList(UICacheManager uICacheManager);
	public enum CKAddress implements CacheKey {
		ROOTMAP("addressROOT_map","addId_"),ALLMAP("addressAll_map","addId_"),OPENMAP("addressIsOpen_map","addId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKAddress(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	
	public enum CKProductType implements CacheKey {
		ALLMAP("productTypeMap","productTypeId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKProductType(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		public String getcKey() {
			return cKey;
		}

		public void setcKey(String cKey) {
			this.cKey = cKey;
		}

		public String getMapKey() {
			return mapKey;
		}

		public void setMapKey(String mapKey) {
			this.mapKey = mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	
	
	
	
	public enum CKProductTypeTesefuwu implements CacheKey {
		ALLMAP("productTesefuwuMap","productTesefuwuId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKProductTypeTesefuwu(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public enum CKGolbal implements CacheKey {
		ALLMAP("globalMap1","globalId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKGolbal(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		public <T> TreeMap<String, T> getTreeMap(UICacheManager uICacheManager) {
			return (TreeMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, T> map = (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		public <T> T getObjectTree(UICacheManager uICacheManager,int id) {
			TreeMap<String, T> map = (TreeMap<String, T>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, T> map = (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
		public <T> List<T> getListTree(UICacheManager uICacheManager) {
			TreeMap<String, T> map = (TreeMap<String, T>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	

	
	public enum CKShopLevel implements CacheKey {
		ALLMAP("shopLevelMap","shopLevelId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKShopLevel(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	

	public enum CKUserLevel implements CacheKey {
		ALLMAP("userLevelMap","userLevelId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKUserLevel(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	
	
	
	
	
	
	public enum CKAuctionPeriodVO implements CacheKey {
		WAITBEGINMAP("auctionTimeMap","auctionPeriodId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKAuctionPeriodVO(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
		public <T> void refresh(UICacheManager uICacheManager, HashMap<String, T> map) {
			uICacheManager.putObjectCached(web, cKey, map);
		}
		
	}
	
	

	
	
	
	//========================================================================
	public enum CKAdvertingPage implements CacheKey {
		ALLMAP("advertingPageMap","");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKAdvertingPage(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		public String getcKey() {
			return cKey;
		}

		public void setcKey(String cKey) {
			this.cKey = cKey;
		}

		public String getMapKey() {
			return mapKey;
		}

		public void setMapKey(String mapKey) {
			this.mapKey = mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		public <T> T getObjectKey(UICacheManager uICacheManager,String key) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(key);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	
	public enum CKAdvertising implements CacheKey {
		markedNumMap("advertisingMap",""),idMap("advertisingIDMap","advertisingId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKAdvertising(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		public String getcKey() {
			return cKey;
		}

		public void setcKey(String cKey) {
			this.cKey = cKey;
		}

		public String getMapKey() {
			return mapKey;
		}

		public void setMapKey(String mapKey) {
			this.mapKey = mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		public <T> T getObjectByMarkedNum(UICacheManager uICacheManager,String markedNum) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(markedNum);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	

	public enum CKAdvertisement implements CacheKey {
		addId_advertingMarked_map("advertisementMap","");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKAdvertisement(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		public String getcKey() {
			return cKey;
		}

		public void setcKey(String cKey) {
			this.cKey = cKey;
		}

		public String getMapKey() {
			return mapKey;
		}

		public void setMapKey(String mapKey) {
			this.mapKey = mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		public <T> T getObjectByMarkedNum(UICacheManager uICacheManager,int addressId,String advertingMarkedNum) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(addressId+"_"+advertingMarkedNum);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	
	
	//================================================================================

	public enum CKSincerityLevel implements CacheKey {
		ALLMAP("sincertityLevelMap","sincerityLevelId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKSincerityLevel(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	public enum CKSincerityType implements CacheKey {
		ALLMAP("sincerityTypeMap","sincerityTypeId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKSincerityType(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	//======================================

	public enum CKPickUpAddress implements CacheKey {
		ALLMAP("pickUpAddressMap","pickUpAddressId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKPickUpAddress(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		public String getcKey() {
			return cKey;
		}

		public void setcKey(String cKey) {
			this.cKey = cKey;
		}

		public String getMapKey() {
			return mapKey;
		}

		public void setMapKey(String mapKey) {
			this.mapKey = mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
	

	public enum CKNewsType implements CacheKey {
		ALLMAP("newsTypeMap","newsTypeId_");
		// 成员变量
		private String cKey;
		private String mapKey;
		// 构造方法
		private CKNewsType(String cKey,String mapKey) {
			this.cKey=cKey;
			this.mapKey=mapKey;
		}
		
		public String getcKey() {
			return cKey;
		}

		public void setcKey(String cKey) {
			this.cKey = cKey;
		}

		public String getMapKey() {
			return mapKey;
		}

		public void setMapKey(String mapKey) {
			this.mapKey = mapKey;
		}

		@Override
		public <T> HashMap<String, T> getMap(UICacheManager uICacheManager) {
			return (HashMap<String, T>) uICacheManager.getObjectCached(web, cKey);
		}
		@Override
		public <T> T getObject(UICacheManager uICacheManager,int id) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (T) map.get(mapKey+id);
		}
		@Override
		public <T> List<T> getList(UICacheManager uICacheManager) {
			HashMap<String, Object> map = (HashMap<String, Object>) uICacheManager.getObjectCached(web, cKey);
			return (List<T>) MyCollectionUtils.map2list(map);
		}
	}
	
	
}
