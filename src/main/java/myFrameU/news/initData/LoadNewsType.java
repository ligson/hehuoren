package myFrameU.news.initData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.news.entity.NewsType;

public class LoadNewsType extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		List<NewsType> ntList = (List<NewsType>)aBiz.findObjectList(NewsType.class, null, "from NewsType", new String[]{"getChilds"}, false, 0, 0);
		if(null!=ntList){
			HashMap<String,NewsType> newsTypeMap = new HashMap<String,NewsType>();
			int size = ntList.size();
			NewsType nt  = null;
			for(int i=0;i<size;i++){
				nt=ntList.get(i);
				newsTypeMap.put("newsTypeId_"+nt.getId(), nt);
			}
			sc.setAttribute("newsTypeMap", newsTypeMap);
			uICacheManager.putObjectCached("web", "newsTypeMap", newsTypeMap);
		}
		
		
	}

	
	
	
	
	
	
	
}
