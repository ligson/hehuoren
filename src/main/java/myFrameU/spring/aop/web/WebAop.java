package myFrameU.spring.aop.web;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.admin.entity.Admin;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.aop.ExcetpionAOP;
import myFrameU.queryArgs.util.SwitchFGQuery;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.aop.init.InitConfig;
import myFrameU.spring.aop.init.LoginEntity;
import myFrameU.spring.aop.init.MavenAopEntity;
import myFrameU.spring.aop.util.AOPUtil;
import myFrameU.spring.listener.ClassSe;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.httpUtil.filter.RequestFilter;
import myFrameU.util.httpUtil.path.PathUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 如何区分admin、shop、user、fg？
 * 		request.getServletPath()=/bg_global/findAllGlobal
 * 		该路径以/admin/开头的是表示admin的aop，/shop/开头的是表示shop的aop
 */
@Aspect
@Component("aspect")
public class WebAop{
	private static final String biaoshi="aop-aop-aop-aop-aop-aop-aop-aop-aop-aop-aop-aop-aop-aop-aop";
	
	@Autowired
	private UICacheManager uICacheManager;
	@Autowired
	private AbstractBizI aBiz;
	

	public UICacheManager getuICacheManager() {
		return uICacheManager;
	}

	public void setuICacheManager(UICacheManager uICacheManager) {
		this.uICacheManager = uICacheManager;
	}

	public AbstractBizI getaBiz() {
		return aBiz;
	}

	public void setaBiz(AbstractBizI aBiz) {
		this.aBiz = aBiz;
	}
	

	//通过within匹配目标方法的class  
	@Around("within(*..controller.*Controller)") 
    public Object arountAction(ProceedingJoinPoint pjp) throws Exception{  
		
        /*Object[] os=pjp.getArgs();
        int size = os.length;
        Object o = null;
        for(int i=0;i<size;i++){
        	o=os[i];
        	System.out.println(o.toString());
        }*/
       
        HttpServletRequest request = (HttpServletRequest) pjp.getArgs()[0];
        ClassSe.canInit(request);
       // System.out.println(request.getQueryString()+"==="+request.getMethod()+"========="+request.getRequestURI()+"==="+request.getRequestURL()+"========");
        // 调用的方法名  
        /*System.out.println("signature.name="+pjp.getSignature().getName());
        System.out.println("signature.declaringTypeName="+pjp.getSignature().getDeclaringTypeName());
        System.out.println("signature.getDeclaringType="+pjp.getSignature().getDeclaringType());
        System.out.println("signature.getModifiers="+ pjp.getSignature().getModifiers());
       
        
        
        System.out.println("kind="+pjp.getKind());
        System.out.println("getSourceLocation="+pjp.getSourceLocation());
        System.out.println("getStaticPart="+pjp.getStaticPart());
        System.out.println("getThis="+pjp.getThis().toString());
        // 获取目标对象  
        Object target = pjp.getTarget();  
        System.out.println("target=="+target.toString());
        */
        /**
         *  request.getContextPath()=/yishupaipai
			request.getServletPath()=/bg_global/findAllGlobal
			request.getRequestURL()=http://localhost:8080/yishupaipai/bg_global/findAllGlobal
			request.getRequestURI()=/yishupaipai/bg_global/findAllGlobal
         */
        //接口request参数检查，  
       // HttpServletRequest request = (HttpServletRequest) RequestFilter.threadLocal.get().get("request");
        HttpServletResponse response = (HttpServletResponse) RequestFilter.threadLocal.get().get("response");
        request.setAttribute("proceedingJoinPoint", pjp);
        
        try {
        	//============================================================
        	String roleClassName=getCurrentRoleClassName(request);
            request.setAttribute("roleClassName", roleClassName);
            int roleId=getCurrentRoleID(request, aBiz);
            request.setAttribute("roleId", roleId);
            //=============================================================
            
            User user = (User)request.getSession().getAttribute("myUser");
            if(null!=user){
            	user = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{user.getId()});
            	request.getSession().setAttribute("myUser", user);
            }
            
            
            
            
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		    response.setHeader("Pragma", "No-cache");
		    response.setHeader("Cache-control", "no-cache, no-store");
		    response.setDateHeader("Expires", 0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
       
		
		
		
       // System.out.println(biaoshi+"request="+request+";;response="+response);
        
        
       // HttpServletRequest request = (HttpServletRequest) pjp.getArgs()[0];  
        
        Object result = null;  
        try {  
        	InitConfig initConfig=myFrameU.spring.aop.init.InitMavenImpl.ic;
        	List<MavenAopEntity> aopAfterList = initConfig.getAopAfterList();
        	List<MavenAopEntity> aopBeforeList = initConfig.getAopBeforeList();
        	if(login(request,response,initConfig)){
        		System.out.println("已经通过了login aop的拦截"+biaoshi);
        		executiveMavenAops(request,response,aopBeforeList,"aopBefore");
        		
        		
        		result =  pjp.proceed(); 
        		executiveMavenAops(request,response,aopAfterList,"aopAfter");
        		
        		//清空每次的queryArgs
        		myFrameU.spring.mvc.SDynaActionForm form=(SDynaActionForm) uICacheManager.getObjectCached("web", "sDynaActionForm");
        		form.getFormMap().clear();
        	}
        } catch (Exception e) { 
        	request.setAttribute("exception", e);
        	ExcetpionAOP exceptionAOP=new ExcetpionAOP();
        	exceptionAOP.aopAfter(request, response, uICacheManager, aBiz);
        	//throw e;
        	//这里的throw千万别跑出去，一旦跑出去则myStr...就不会显示了。
        } catch (Throwable e) {  
        }  
        if(result instanceof String){
        	return AOPUtil.modifyResult(result.toString(), request);  
        }
        return result;
    }  
	

	
	
	
	public void executiveMavenAops(HttpServletRequest req,HttpServletResponse res,List<MavenAopEntity> aopList,String currentMethod) throws Exception{
		String path_pre=getReqPrefix(req);
		int size = aopList.size();
		MavenAopEntity mae=null;
		Class c = null;
		Method m = null;
		String interceptPrefix=null;
		for(int i=0;i<size;i++){
			mae=aopList.get(i);
			c=mae.getAopClass();
			interceptPrefix=mae.getInterceptPrefix();
			
			m = c.getDeclaredMethod(currentMethod, javax.servlet.http.HttpServletRequest.class,javax.servlet.http.HttpServletResponse.class,myFrameU.ehcache.util.UICacheManager.class,myFrameU.biz.AbstractBizI.class);
			if(null==interceptPrefix || interceptPrefix.equals("") || interceptPrefix.equals("all")){
				//说明这个aop所有的请求都拦截
				m.invoke(c.newInstance(), req,res,uICacheManager,aBiz); 
				// System.out.println("执行了"+c.getName()+"."+currentMethod+"方法"+biaoshi);
			}else{
				//   /admin/,/shop/,/user/
				if(interceptPrefix.contains(",")){
					String[] pres=interceptPrefix.split(",");
					int len=pres.length;
					String pre=null;
					for(int j=0;j<len;j++){
						pre=pres[j];
						if(path_pre.equals(pre)){
							m.invoke(c.newInstance(), req,res,uICacheManager,aBiz); 
						//	System.out.println("执行了"+c.getName()+"."+currentMethod+"方法"+biaoshi);
						}
					}
				}else{
					//说明只拦截一个
					//如果当前request的path_pre=/admin/，而这个aop的拦截前缀是/admin/
					if(path_pre.equals(interceptPrefix)){
						m.invoke(c.newInstance(), req,res,uICacheManager,aBiz); 
					//	System.out.println("执行了"+c.getName()+"."+currentMethod+"方法"+biaoshi);
					}
				}
			}
		}
	}
	
	public boolean login(HttpServletRequest req,HttpServletResponse res,InitConfig initConfig) throws Exception{
    	String loginStatus=initConfig.getLogin_status();
    	if(loginStatus.equals("open")){
    		String path_pre=getReqPrefix(req);
			if(null!=path_pre && !path_pre.equals("")){
				HashMap<String,LoginEntity> loginEntityMap = initConfig.getLoginEntityMap();
				if(null!=loginEntityMap){
					LoginEntity le = loginEntityMap.get(path_pre);
					if(null!=le){
						String roleSessionKey=le.getSaveRoleSessionKey();
						Object role = req.getSession().getAttribute(roleSessionKey);
						String ifNotLoginPath=le.getIfNotLoginPath();
						if(null!=role){
							//登录了已经
							return true;
						}else{
							//没有登录
							res.sendRedirect(PathUtil.getBasePath(req)+ifNotLoginPath);
						}
					}else{
						return true;
					}
				}else{
					//  /global/findAll
					//此时解析出来的是  /global/ 在map里没有，那就说明是前台的,则不拦截
					return true;
				}
			}else{
				/**
				 * 1)没有/
				 * 2)只有1个/
				 * 这两者都是属于前台的，如/findNews
				 */
				return true;
			}
    	}else{
    		return true;
    	}
		return false;
	}
	
	
	
	
	
	//获取这个请求的前缀
	public static String getReqPrefix(HttpServletRequest req){
		String path_pre=null;
		String servletPath = req.getServletPath();
		//以（/admin/global/findAllGlobal）为例，先判断这个路径字符串是不是符合规则的（以/开头，且存在至少2个/），截取第二个/之前的数据（包括第二个/）
		if(servletPath.startsWith("/")){
			/**
			 * 将wrap单独拿出来
			 */
			if(servletPath.startsWith("/wrap/")){
				String noWrap=servletPath.replace("/wrap/", "");
				int twoIndex=noWrap.indexOf("/", 1);
				if(twoIndex>1){
					//  /admin/global/findAll..
					path_pre=noWrap.substring(0,twoIndex+1);//  /admin/
					path_pre="/wrap/"+path_pre;
				}else{
					path_pre="/wrap/";
				}
			}else{
				int twoIndex=servletPath.indexOf("/", 1);//6,就是第7个
				if(twoIndex>1){
					//  /admin/global/findAll..
					path_pre=servletPath.substring(0,twoIndex+1);//  /admin/
				}
			}
		}
		return path_pre;
	}
	
	//==============================================================================================
	public static String getCurrentRoleClassName(HttpServletRequest req){
		String prefix=getReqPrefix(req);
		System.out.println("webAop===prefix"+prefix);
		if(null!=prefix && !prefix.equals("")){
			if(prefix.equals("/user/") || prefix.equals("/wrap/user/")){
				return User.class.getName();
			}else if(prefix.equals("/shop/") || prefix.equals("/wrap/shop/")){
				return Shop.class.getName();
			}else if(prefix.equals("/admin/")){
				return Admin.class.getName();
			}else{
				return "";
			}
		}
		return "";
	}
	
	public static int getCurrentRoleID(HttpServletRequest req,AbstractBizI aBiz) throws Exception{
		String prefix=getReqPrefix(req);
		if(null!=prefix && !prefix.equals("")){
			if(prefix.equals("/user/") || prefix.equals("/wrap/user/")){
				User user = getCurrentUSER(req,aBiz);
				if(null!=user){
					return user.getId();
				}
			}else if(prefix.equals("/shop/") || prefix.equals("/wrap/shop/")){
				Shop shop = getCurrentSHOP(req,aBiz);
				if(null!=shop){
					return shop.getId();
				}
				
			}else if(prefix.equals("/admin/")){
				Admin admin = getCurrentADMIN(req, aBiz);
				if(null!=admin){
					return admin.getId();	
				}
				
			}else{
				return 0;
			}
		}
		return 0;
	}
	public static String getCurrentRoleName(HttpServletRequest req,AbstractBizI aBiz) throws Exception{
		String prefix=getReqPrefix(req);
		if(null!=prefix && !prefix.equals("")){
			if(prefix.equals("/user/")||prefix.equals("/wrap/user/")){
				User user = getCurrentUSER(req,aBiz);
				return user.getNicheng();
			}else if(prefix.equals("/shop/")||prefix.equals("/wrap/shop/")){
				Shop shop = getCurrentSHOP(req,aBiz);
				return shop.getName();
			}else if(prefix.equals("/admin/")){
				Admin admin = getCurrentADMIN(req, aBiz);
				return admin.getName();
			}else{
				return null;
			}
		}
		return null;
	}
	//======================================================================================================================
	public static User getCurrentUSER(HttpServletRequest request,AbstractBizI aBiz) throws Exception{
		User user = (User)request.getSession().getAttribute("myUser");
    	if(null==user){
    		/*user=(User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{1});
    		request.getSession().setAttribute("myUser", user);*/
    	}
		return user;
	}
	
	public static Shop getCurrentSHOP(HttpServletRequest request,AbstractBizI aBiz) throws Exception{
		Shop shop = (Shop)request.getSession().getAttribute("myShop");
    	if(null==shop){
    		/*shop=(Shop)aBiz.findObjectById("from Shop as u where u.id=?", new Object[]{3});
    		request.getSession().setAttribute("myShop", shop);*/
    	}
		return shop;
	}
	
	public static Admin getCurrentADMIN(HttpServletRequest request,AbstractBizI aBiz) throws Exception{
		Admin admin = (Admin)request.getSession().getAttribute("myAdmin");
    	if(null==admin){
    		/*admin=(Admin)aBiz.findObjectById("from Admin as u where u.id=?", new Object[]{1});
    		request.getSession().setAttribute("myAdmin", admin);*/
    	}
		return admin;
	}
	//===================================================================
	public static boolean isFG(HttpServletRequest req){
		String prefix=getReqPrefix(req);
		System.out.println("webAop===prefix"+prefix);
		if(null!=prefix && !prefix.equals("")){
			if(prefix.equals("/user/")){
				return false;
			}else if(prefix.equals("/shop/")){
				return false;
			}else if(prefix.equals("/admin/")){
				return false;
			}else if(prefix.startsWith("/fg")){
				return true;
			}
		}
		return false;
	}
}