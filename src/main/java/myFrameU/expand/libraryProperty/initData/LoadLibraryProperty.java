package myFrameU.expand.libraryProperty.initData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.expand.libraryProperty.entity.SystemLibraryProperty;
import myFrameU.expand.libraryProperty.entity.SystemLibraryPropertyValue;

public class LoadLibraryProperty extends AbstractWebInit {
	@Override
	public void initData(ServletContext sc, UICacheManager uICacheManager,
			AbstractBizI aBiz) throws Exception {
		HashMap<String,SystemLibraryProperty> libraryPropertyMap=new HashMap<String,SystemLibraryProperty>();
		List<SystemLibraryProperty> list = (List<SystemLibraryProperty>) aBiz
				.findObjectList(SystemLibraryProperty.class, null,
						"from SystemLibraryProperty as s", null, false, 0, 0);
		int size = list.size();
		SystemLibraryProperty p = null;
		for (int i = 0; i < size; i++) {
			p = list.get(i);
			libraryPropertyMap.put("pId_"+p.getId(), p);
		}

		sc.setAttribute("libraryPropertyMap", libraryPropertyMap);
		uICacheManager.putObjectCached("web", "libraryPropertyMap", libraryPropertyMap);
		
		
		
		
		//===================================================================================
		List<SystemLibraryPropertyValue> pvlist = (List<SystemLibraryPropertyValue>) aBiz
				.findObjectList(SystemLibraryPropertyValue.class, null,
						"from SystemLibraryPropertyValue as s", null, false, 0, 0);
		HashMap<String,SystemLibraryPropertyValue> libraryPropertyValueMap=new HashMap<String,SystemLibraryPropertyValue>();
		int vsize = pvlist.size();
		SystemLibraryPropertyValue pv = null;
		for (int i = 0; i < vsize; i++) {
			pv = pvlist.get(i);
			libraryPropertyValueMap.put("pvId_"+pv.getId(), pv);
		}
		
		sc.setAttribute("libraryPropertyValueMap", libraryPropertyValueMap);
		uICacheManager.putObjectCached("web", "libraryPropertyValueMap", libraryPropertyValueMap);
		
		/*for (Map.Entry<String, SystemLibraryPropertyValue> entry : libraryPropertyValueMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue() + "=======================########$$$$$$$$$$$%%%%%%%%%%^^^^^^^^^^^^^^==========================");
		}
		*/
		
		
		
		
	}
}
