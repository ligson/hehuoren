package myFrameU.global.initData;

import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.global.entity.Global;

public class LoadGlobal extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		
		//insertData(aBiz);
		
		List<Global> amList = (List<Global>)aBiz.findObjectList(Global.class, null, "from Global as g where g.xm='h' order by g.id desc", null, false, 0, 0);
		TreeMap<String,Global> globalMap = new TreeMap<String,Global>();
		TreeMap<String,Global> globalMap1 = new TreeMap<String,Global>();
		int size = amList.size();
		Global m = null;
		for(int i=0;i<size;i++){
			m=amList.get(i);
			globalMap.put(m.getNamePy(), m);
			globalMap1.put("globalId_"+m.getId(), m);
		}
		uICacheManager.putObjectCached("web", "globalMap", globalMap);
		uICacheManager.putObjectCached("web", "globalMap1", globalMap1);
		sc.setAttribute("globalMap", globalMap);
	}
	
	
	
	
	
}
