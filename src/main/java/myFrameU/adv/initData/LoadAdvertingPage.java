package myFrameU.adv.initData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import myFrameU.adv.entity.AdvertingPage;
import myFrameU.adv.init.InitConfig;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;

public class LoadAdvertingPage extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		InitConfig initConfig = myFrameU.adv.init.InitMavenImpl.ic;
		String pageInsert=initConfig.getPageInsert();
		if(null!=pageInsert && !pageInsert.equals("")){
			if(pageInsert.equals("database")){
				List<AdvertingPage> apList = initConfig.getPageList();
				if(null!=apList){
					int size = apList.size();
					if(size>0){
						AdvertingPage ap = null;
						AdvertingPage ap1 = null;
						for(int i=0;i<size;i++){
							ap=apList.get(i);
							ap1=(AdvertingPage)aBiz.findObjectById("from AdvertingPage as ap where ap.nameKey=?", new Object[]{ap.getNameKey()});
							if(null==ap1){
								aBiz.addObject(ap);
							}
						}
					}
				}
			}
		}
		
		
		
		
		Map<String,AdvertingPage> advertingPageMap = new HashMap<String,AdvertingPage>();
		List<AdvertingPage> aPageList = (List<AdvertingPage>)aBiz.findObjectList(AdvertingPage.class, null, "from AdvertingPage", null, false, 0, 0);
		int size = aPageList.size();
		AdvertingPage ap = null;
		for(int i=0;i<size;i++){
			ap=aPageList.get(i);
			advertingPageMap.put(ap.getNameKey(), ap);
		}
		uICacheManager.putObjectCached("web", "advertingPageMap", (Serializable) advertingPageMap);
		sc.setAttribute("advertingPageMap", advertingPageMap);
		
	}
	
	
	
	
	
	
}
