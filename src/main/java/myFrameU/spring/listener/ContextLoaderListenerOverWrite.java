package myFrameU.spring.listener;


import hhr.message.MessageUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import myFrame.quartz.MyQuartzJobExcute;
import myFrameU.account.biz.AccountBizI;
import myFrameU.biz.AbstractBizI;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.mavenInit.AutoInit;
import myFrameU.myFrameInit.MyFrameConfigXml;
import myFrameU.quartz.util.StartQuartz;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.weixin.base.util.RefreshAccessToken;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class ContextLoaderListenerOverWrite extends ContextLoaderListener  implements ServletContextListener {
	
    @Override  
    /** 
     * @description 重写ContextLoaderListener的contextInitialized方法 
     */  
    public void contextInitialized(ServletContextEvent event) {  
    	
    	
    	
    	String webAppRootKey = event.getServletContext().getRealPath("/");    
        System.setProperty("webapp.root" , webAppRootKey);   
        
        super.contextInitialized(event);  
        
        
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());  
        //----------------------------------------------------------------------------------
        
        //第一）初始化myFrame一些大型的常量。
        HashMap<String,String> map = (HashMap<String, String>) MyFrameConfigXml.getConfigMap();
        
        
        //第二）初始化myFrame中各个模块的静态参数
        AutoInit.autoInit(map.get("initMaven-xmlPath"));
        

        //第三）初始化ehcache缓存里的数据。
        event.getServletContext().setAttribute("applicationContext",applicationContext); 
        UICacheManager uICacheManager=(UICacheManager)applicationContext.getBean("uICacheManager");
        AbstractBizI aBiz=(AbstractBizI)applicationContext.getBean("aBiz");
        AccountBizI accountBiz=(AccountBizI)applicationContext.getBean("accountBiz");
       // OrderBizImpl orderBiz=(OrderBizImpl)applicationContext.getBean("orderBiz");
        try {
        	AbstractWebInit webInit = new AbstractWebInit();
			webInit.initData(event.getServletContext(),uICacheManager,aBiz);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        
        //第四）自定义的一些其他的操作
        try{
        	String listenerMyClassStr=map.get("listener-myClass");
        	String listenerMethodStr=map.get("listener-method");
    		if(null!=listenerMyClassStr && !listenerMyClassStr.equals("") && null!=listenerMethodStr && !listenerMethodStr.equals("")){
    			Class c = Class.forName(listenerMyClassStr);
    			Method m = c.getDeclaredMethod(listenerMethodStr,ServletContext.class,UICacheManager.class,AbstractBizI.class);
    			m.invoke(c.newInstance(),event.getServletContext(),uICacheManager,aBiz); 
    			
    		}
    		
    		//第五）生成空的form
    		SDynaActionForm sDynaActionForm=new SDynaActionForm();
    		uICacheManager.putObjectCached("web", "sDynaActionForm", (Serializable) sDynaActionForm);
    		
    		/*//第六）生成最后加时的map
    		HashMap<String,AuctionItem> lastJIASHIMap=new HashMap<String,AuctionItem>();
    		uICacheManager.putObjectCached("web", "lastJIASHIMap", (Serializable) lastJIASHIMap);
    		*/
    		
    		
    		StartQuartz.scheduler=(Scheduler)applicationContext.getBean("scheduler");
    		StartQuartz.uICacheManager=uICacheManager;
    		StartQuartz.QuartzJobExcuteClass=MyQuartzJobExcute.class;
    		StartQuartz.accountBiz=accountBiz;
    		//StartQuartz.orderBiz=orderBiz;
    		StartQuartz.execute();
    		
    		
    		MessageUtil.uICacheManager=uICacheManager;
    		
    		//每隔一个小时，获取微信的accessToken
            RefreshAccessToken weixin_rat=new RefreshAccessToken(3600,uICacheManager);
            
            
            ClassSe.canInit(null);
            
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		
		
    }  
    public void contextDestroyed(ServletContextEvent event) {//在这里关闭监听器，所以在这里销毁定时器。  
     } 
}  