package myFrameU.shop.util;

import java.util.HashMap;

import myFrame.cache.CacheKey;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.product.entity.ProductType;
import myFrameU.shop.entity.ShopLevel;

public class ShopMainProductTypeUtil {
	/**
	 * 获取一个shop的pts的大类的个数
	 * 		前提是：shop的mainPro....这个字符串已经加上了父亲。
	 */
	public static boolean verCount_shopMainPts(String afterAddFatherIdMainPts,UICacheManager uICacheManager,int shopLevelId) throws Exception{
		HashMap<String,ProductType> ptMap = CacheKey.CKProductType.ALLMAP.getMap(uICacheManager);
		if(null!=ptMap){
			afterAddFatherIdMainPts=afterAddFatherIdMainPts.substring(1,afterAddFatherIdMainPts.length()-1);
			int bigCount=0;
			if(!afterAddFatherIdMainPts.contains("][")){
				ProductType pt = ptMap.get("productTypeId_"+afterAddFatherIdMainPts);
				if(null!=pt){
					if(pt.getIsROOT()==0){
						bigCount=1;
					}
				}
			}else{
				String[] array=afterAddFatherIdMainPts.split("]\\[");
				if(null!=array){
					int len = array.length;
					String ptId = null;
					ProductType pt = null;
					for(int i=0;i<len;i++){
						ptId=array[i];
						pt = ptMap.get("productTypeId_"+ptId);
						if(null!=pt){
							if(pt.getIsROOT()==0){
								bigCount++;
							}
						}
					}
				}
			}
			
			ShopLevel sl = CacheKey.CKShopLevel.ALLMAP.getObject(uICacheManager, shopLevelId);
			if(null!=sl){
				int maxBigCount = sl.getShopMainBigProductTypeCount();
				if(maxBigCount>=bigCount){
					//ok
					return true;
				}else{
					return false;
					//throw new MyStrException("抱歉，您当前级别只能最多选择"+maxBigCount+"个大类");
				}
			}
		}
		return false;
	}
}
