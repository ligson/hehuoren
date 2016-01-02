package hhr.order.initData;


import hhr.order.entity.PickUpAddress;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.product.entity.ProductType;

public class LoadPickUpAddress extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		HashMap<String,PickUpAddress> map = new HashMap<String,PickUpAddress>();
		List<PickUpAddress> puaList = (List<PickUpAddress>)aBiz.findObjectList(PickUpAddress.class, null, "from PickUpAddress as pt order by pt.id", null, false, 0, 0);
		int size = puaList.size();
		PickUpAddress pt = null;
		for(int i=0;i<size;i++){
			pt=puaList.get(i);
			map.put("pickUpAddressId_"+pt.getId(), pt);
		}
		
		sc.setAttribute("pickUpAddressMap", map);
		uICacheManager.putObjectCached("web", "pickUpAddressMap", map);
	}

	
	
	
	
	
	
	
}
