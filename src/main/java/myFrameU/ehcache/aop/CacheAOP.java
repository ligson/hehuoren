package myFrameU.ehcache.aop;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.init.EhcacheEntity;
import myFrameU.ehcache.init.InitCleanEntity;
import myFrameU.ehcache.init.InitConfig;
import myFrameU.ehcache.init.InitMavenImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.spring.aop.interf.AOPI;

import org.aspectj.lang.ProceedingJoinPoint;

public class CacheAOP implements AOPI{
	
	//级联清理缓存
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz){
		try{
			System.out.println("AOP子模块----执行了CacheAOP-aopAfter");
			
			ProceedingJoinPoint pjp=(ProceedingJoinPoint) req.getAttribute("proceedingJoinPoint");
			String controllerClassName=pjp.getTarget().getClass().getName();
			String method=pjp.getSignature().getName();
			
			/*String path=req.getServletPath();
			path=path.replace(".do", "");
			String method=req.getParameter("method");
			System.out.println("path=="+path+";method="+method);//path==/servlet/test;method=null
*/			
			InitConfig ic = InitMavenImpl.ic;
			List<InitCleanEntity> iceList = ic.getInitCleans();
			int size = iceList.size();
			InitCleanEntity ice = null;
			for(int i=0;i<size;i++){
				ice=iceList.get(i);
				String iceMethods=ice.getMethod()+",";//多个method中间用英文逗号隔开
				/*String[] methodArray=null;
				if(iceMethods.contains(",")){
					methodArray=iceMethods.split(",");
				}else{
					methodArray=new String[1];
					methodArray[0]=iceMethods;
				}*/
				
				
				//add....,modify....,
				//只要当前method在这里面就需要 拦截
				if(controllerClassName.equals(ice.getClassName()) && iceMethods.contains(method+",") ){
					List<EhcacheEntity> ceList = ice.getClears();
					int ceListSize = ceList.size();
					EhcacheEntity ee = null;
					for(int j=0;j<ceListSize;j++){
						ee=ceList.get(j);
						cacheManager.removeElement(ee.getCacheName(), ee.getCacheKey());
						//清空之后，判断下这个缓存是不是需要一直有数据，如果是，则你清空了，你需要重新load
						req.getSession().getServletContext().removeAttribute( ee.getCacheKey());
						boolean againLoad=ee.getAgainLoad();
						if(againLoad){
							Class c = Class.forName(ee.getLoadClass());
							//ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz
							Class types[] =new Class[3];
							types[0]=Class.forName("javax.servlet.ServletContext");
							types[1]=Class.forName("myFrameU.ehcache.util.UICacheManager");
							types[2]=Class.forName("myFrameU.biz.AbstractBizI");
							
							Method m = c.getMethod("initData",types );
							m.invoke(c.newInstance(),new Object[]{req.getSession().getServletContext(),cacheManager,aBiz} );
						}
					}
				}else{
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
				return null;
		
	}

	
}
