package myFrameU.shop.util;

import javax.servlet.http.HttpServletRequest;

import myFrame.cache.CacheKey;
import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.shop.entity.Certification;
import myFrameU.shop.entity.Shop;
import myFrameU.shop.entity.ShopTemplateSimple;
import myFrameU.user.entity.Shoucang;

public class QueryShopUtil {
	
	public static Shoucang queryShoucang(int userId,int shopId,AbstractBizI aBiz) throws Exception{
		Shoucang sc  = null;
		if(userId!=0 && shopId!=0){
			sc = (Shoucang)aBiz.findObjectById("from Shoucang as sc where sc.scEntity=? and sc.scEntityId=? and sc.userId=?", 
					new Object[]{Shop.class.getName(),shopId,userId});
		}
		return sc;
	}
	
	
	
	public static Shop queryShop(AbstractBizI aBiz,HttpServletRequest req,UICacheManager uICacheManager,Integer id) throws Exception{
		Shop shop =null;
		if(null!=id && id.intValue()!=0){
			shop = (Shop)aBiz.findObjectById("from Shop as s where s.id=?", new Object[]{id});
			if(null!=shop){
				int addressId=shop.getAddressId();
				Address address = (Address)CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
				req.setAttribute("address", address);
				
				shop.setMainProductTypeList(uICacheManager);
				req.setAttribute("shop", shop);
				ShopTemplateSimple sts = (ShopTemplateSimple)aBiz.findObjectById("from ShopTemplateSimple as sts where sts.shopId=?", new Object[]{id});
				req.setAttribute("shopTemplateSimple", sts);
				
				Certification c=(Certification)aBiz.findObjectById("from Certification as c where c.whoclassName=? and c.whoId=?", 
						new Object[]{Shop.class.getName(),id});
				req.setAttribute("certification", c);
			}
		}
		return shop;
	}
}
