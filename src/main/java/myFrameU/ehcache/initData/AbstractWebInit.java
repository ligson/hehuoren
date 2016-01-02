package myFrameU.ehcache.initData;

import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.init.InitConfig;
import myFrameU.ehcache.init.InitMavenImpl;
import myFrameU.ehcache.util.UICacheManager;


public class AbstractWebInit implements WebInitInterface{
	
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		InitConfig ic=InitMavenImpl.ic;
		List<Class> initDataClasses = ic.getInitDataClasses();
		
		int size = initDataClasses.size();
		Class c = null;
		AbstractWebInit webInit=null;
		for(int i=0;i<size;i++){
			c=initDataClasses.get(i);
			webInit=(AbstractWebInit)c.newInstance();
			webInit.initData(sc,uICacheManager,aBiz);
		}
		
		
		
	}




}
