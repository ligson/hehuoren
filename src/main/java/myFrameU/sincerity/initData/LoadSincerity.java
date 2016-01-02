package myFrameU.sincerity.initData;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.sincerity.entity.SincerityLevel;
import myFrameU.sincerity.entity.SincerityType;

public class LoadSincerity extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		HashMap<String,SincerityLevel> sincertityLevelMap = new HashMap<String,SincerityLevel>();
		List<SincerityLevel> sincerityLevelList = (List<SincerityLevel>)aBiz.findObjectList(SincerityLevel.class, null, "from SincerityLevel", null, false, 0, 0);
		int lsize = sincerityLevelList.size();
		SincerityLevel sl = null;
		for(int i=0;i<lsize;i++){
			sl=sincerityLevelList.get(i);
			sincertityLevelMap.put("sincerityLevelId_"+sl.getId(), sl);
		}
		uICacheManager.putObjectCached("web", "sincertityLevelMap", sincertityLevelMap);
		
		
		
		HashMap<String,SincerityType> stMap = new HashMap<String,SincerityType>();
		List<SincerityType> stList = (List<SincerityType>)aBiz.findObjectList(SincerityType.class, null, "from SincerityType", null, false, 0, 0);
		int tsize = stList.size();
		SincerityType st = null;
		for(int i=0;i<tsize;i++){
			st=stList.get(i);
			stMap.put("sincerityTypeId_"+st.getId(), st);
		}
		uICacheManager.putObjectCached("web", "sincerityTypeMap", stMap);
		
		
		
		
	}
	
	
	
	
	
}
