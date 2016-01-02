package myFrameU.adv.initData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import myFrameU.adv.entity.AdvertingPage;
import myFrameU.adv.entity.Advertisement;
import myFrameU.adv.entity.Advertising;
import myFrameU.adv.init.InitConfig;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;

public class LoadAdvering extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		
		
		
		InitConfig initConfig = myFrameU.adv.init.InitMavenImpl.ic;
		String advertingInsert=initConfig.getAdvertingInsert();
		if(null!=advertingInsert && !advertingInsert.equals("")){
			if(advertingInsert.equals("database")){
				List<Advertising> aList = initConfig.getaList();
				if(null!=aList){
					int size = aList.size();
					if(size>0){
						Advertising a = null;
						Advertising a1 = null;
						for(int i=0;i<size;i++){
							a=aList.get(i);
							a1=(Advertising)aBiz.findObjectById("from Advertising as ap where ap.markedNum=?", new Object[]{a.getMarkedNum()});
							if(null==a1){
								aBiz.addObject(a);
							}
						}
					}
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		Map<String,Advertising> advertisingIDMap = new HashMap<String,Advertising>();
		Map<String,Advertising> advertisingMap = new HashMap<String,Advertising>();
		List<Advertising> advertisingList = (List<Advertising>)aBiz.findObjectList(Advertising.class, null, "from Advertising as a where a.status='ING'", null, false, 0, 0);
		sc.setAttribute("advertisingList", advertisingList);
		int asize = advertisingList.size();
		Advertising a = null;
		for(int i=0;i<asize;i++){
			a=advertisingList.get(i);
			advertisingMap.put(a.getMarkedNum(), a);
			advertisingIDMap.put("advertisingId_"+a.getId(), a);
		}
		uICacheManager.putObjectCached("web", "advertisingMap", (Serializable) advertisingMap);
		uICacheManager.putObjectCached("web", "advertisingIDMap", (Serializable) advertisingIDMap);
		
		sc.setAttribute("advertisingMap", advertisingMap);
		sc.setAttribute("advertisingIDMap", advertisingIDMap);
		
		
		
		
		
		
	}
	
	
	
}
