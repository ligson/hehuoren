package hhr.order.util;

import hhr.order.entity.PickUpAddress;

import java.util.ArrayList;
import java.util.List;

import myFrame.cache.CacheKey;
import myFrameU.ehcache.util.UICacheManager;

public class PickUpAddressUtil {
	
/*	
	public static List<PickUpAddress> findPickUpAddressList(int addressId,String product_pickIds,UICacheManager uICacheManager) throws Exception{
		List<PickUpAddress> pickUpAddressList=null;
		if(null!=product_pickIds && !product_pickIds.equals("")){
			pickUpAddressList = new ArrayList<PickUpAddress>();
			//product_pickIds [][][]
			if(addressId==0){
				addressId=420;
			}
			List<PickUpAddress> pickUpAddressList1=CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
			if(null!=pickUpAddressList1){
				int size = pickUpAddressList1.size();
				int addressId_pa = 0;
				PickUpAddress pa = null;
				int paId =0;
				String paIdStr = null;
				for(int i=0;i<size;i++){
					pa=pickUpAddressList1.get(i);
					addressId_pa=pa.getAddressId();
					if(addressId_pa==addressId){
						paId=pa.getId();
						paIdStr="["+paId+"]";
						if(product_pickIds.contains(paIdStr)){
							pickUpAddressList.add(pa);
						}
					}
				}
			}
			
		}
		if(null!=pickUpAddressList){
			int size = pickUpAddressList.size();
			if(size==0){
				pickUpAddressList=null;
			}
		}
		return pickUpAddressList;
	}
	*/
	
	public static List<PickUpAddress> findPickUpAddressList(int addressId,String product_pickIds,UICacheManager uICacheManager) throws Exception{
		List<PickUpAddress> pickUpAddressList=null;
		pickUpAddressList = new ArrayList<PickUpAddress>();
		//product_pickIds [][][]
		if(addressId==0){
			addressId=420;
		}
		List<PickUpAddress> pickUpAddressList1=CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
		if(null!=pickUpAddressList1){
			int size = pickUpAddressList1.size();
			int addressId_pa = 0;
			PickUpAddress pa = null;
			int paId =0;
			String paIdStr = null;
			for(int i=0;i<size;i++){
				pa=pickUpAddressList1.get(i);
				addressId_pa=pa.getAddressId();
				if(addressId_pa==addressId){
					paId=pa.getId();
					paIdStr="["+paId+"]";
					pickUpAddressList.add(pa);
				}
			}
		}
		if(null!=pickUpAddressList){
			int size = pickUpAddressList.size();
			if(size==0){
				pickUpAddressList=null;
			}
		}
		return pickUpAddressList;
	}
	
	
	
	public static List<PickUpAddress> findPickUpAddressList(int addressId,UICacheManager uICacheManager) throws Exception{
		List<PickUpAddress> pickUpAddressList=null;
		pickUpAddressList = new ArrayList<PickUpAddress>();
		//product_pickIds [][][]
		if(addressId==0){
			addressId=420;
		}
		List<PickUpAddress> pickUpAddressList1=CacheKey.CKPickUpAddress.ALLMAP.getList(uICacheManager);
		if(null!=pickUpAddressList1){
			int size = pickUpAddressList1.size();
			int addressId_pa = 0;
			PickUpAddress pa = null;
			int paId =0;
			String paIdStr = null;
			for(int i=0;i<size;i++){
				pa=pickUpAddressList1.get(i);
				addressId_pa=pa.getAddressId();
				if(addressId_pa==addressId){
					paId=pa.getId();
					paIdStr="["+paId+"]";
					pickUpAddressList.add(pa);
				}
			}
		}
		if(null!=pickUpAddressList){
			int size = pickUpAddressList.size();
			if(size==0){
				pickUpAddressList=null;
			}
		}
		return pickUpAddressList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
