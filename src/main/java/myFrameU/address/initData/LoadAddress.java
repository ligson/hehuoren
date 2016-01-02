package myFrameU.address.initData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;

public class LoadAddress extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		
		/**
		 * 存储三种：
		 * 		1）所有的根目录
		 * 		2）所有的数据
		 * 		3）所有已经开通了的城市站数据
		 */
		
		
		HashMap<String,Address> addressIsOpen_map = new HashMap<String,Address>();//已经开通的城市站
		
		
		//所有的根目录
		HashMap<String,Address> addressROOT_map = new HashMap<String,Address>();
		List<Address> addressROOT_list = (List<Address>)aBiz.findObjectList(Address.class, null, "from Address as a where a.isROOT=0",null, false, 0, 0);
		sc.setAttribute("addressROOT_list", addressROOT_list);
		int size = addressROOT_list.size();
		Address a = null;
		for(int i=0;i<size;i++){
			a=addressROOT_list.get(i);
			addressROOT_map.put("addId_"+a.getId(), a);
		}
		sc.setAttribute("addressROOT_map", addressROOT_map);
		uICacheManager.putObjectCached("web", "addressROOT_map", (Serializable)addressROOT_map);
		
		
		HashMap<String,Address> addressAll_map = new HashMap<String,Address>();
		List<Address> addressList_all = (List<Address>)aBiz.findObjectList(Address.class, null, "from Address as a left join fetch a.parent",new String[]{"getChilds"}, false, 0, 0);
		sc.setAttribute("addressList_all", addressList_all);
		int all_size=addressList_all.size();
		Address all_a=null;
		Set<Address> citys=null;
		Address city=null;
		String firstZm=null;
		for(int i=0;i<all_size;i++){
			all_a=addressList_all.get(i);//区
			if(all_a.getJibie()==2){
				if(all_a.getIsOpen()==1){
					addressIsOpen_map.put("addId_"+all_a.getId(), all_a);
				}
			}
			addressAll_map.put("addId_"+all_a.getId(), all_a);
		}
		
		sc.setAttribute("addressAll_map", addressAll_map);
		uICacheManager.putObjectCached("web", "addressAll_map", (Serializable)addressAll_map);
		uICacheManager.putObjectCached("web", "addressAll_list", (Serializable)addressList_all);
		
		
		sc.setAttribute("addressIsOpen_map", addressIsOpen_map);
		uICacheManager.putObjectCached("web", "addressIsOpen_map", (Serializable)addressIsOpen_map);
	
	}

	
	
	
	
	
	
	
}
