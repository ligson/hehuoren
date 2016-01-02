package myFrameU.product.initData;


import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.product.entity.ProductType;

public class LoadProductType extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		HashMap<String,ProductType> map = new HashMap<String,ProductType>();
		List<ProductType> ptList = (List<ProductType>)aBiz.findObjectList(ProductType.class, null, "from ProductType as pt order by pt.id", new String[]{"getChilds"}, false, 0, 0);
		int size = ptList.size();
		ProductType pt = null;
		for(int i=0;i<ptList.size();i++){
			pt=ptList.get(i);
			map.put("productTypeId_"+pt.getId(), pt);
		}
		
		sc.setAttribute("productTypeMap", map);
		uICacheManager.putObjectCached("web", "productTypeMap", map);
	}

	
	
	
	
	
	
	
}
