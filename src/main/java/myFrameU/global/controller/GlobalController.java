package myFrameU.global.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.app.util.AppJsonResultUtil;
import myFrameU.biz.AbstractBizI;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.global.init.InitConfig;
import myFrameU.global.init.InitMavenImpl;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("globalController")
@RequestMapping(value="/admin/global")
public class GlobalController extends FatherController {
	@RequestMapping(value="/findAllGlobal")
	public String findAllGlobals(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm nForm =this.getSDynaActionForm(req);
		List<Global> globalList =CacheKey.CKGolbal.ALLMAP.getListTree(uICacheManager);
		req.setAttribute(AppJsonResultUtil.app_+"globalList",globalList);
		/*TreeMap<String,Global> globalMap = (TreeMap<String,Global>)uICacheManager.getObjectCached("web", "globalMap");
		List<Global> globalList = null;
		Global  g=null;
		if(null==globalMap){
			
		}else{
			globalList=new ArrayList<Global>();
			for(Map.Entry<String, Global> entry: globalMap.entrySet()) {
				if(null==g){
					g=entry.getValue();
				}
				globalList.add(entry.getValue());
			}
		}*/
		
		
		
		return this.getForward("setUp/global", req);
	}
	
	
	@RequestMapping(value="/modify")
	public void modify(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm nForm =this.getSDynaActionForm(req);
		Integer id = (Integer)nForm.getInteger("id");
		String myValue=nForm.getString("value");
System.out.println("myValue==="+myValue);
		String bz = nForm.getString("bz");
		if(null!=id && null!=myValue){
			if(id!=0 && !myValue.equals("")){
				Global g = (Global)aBiz.findObjectById("from Global as g where g.id=?", new Object[]{id});
				if(null!=g){
					
					InitConfig initConfig = InitMavenImpl.ic;
					String status = initConfig.getStatus();
					if(null!=status && status.equals("open")){
						String modGlobalClassStr = initConfig.getModGlobalClass();
						Class modGlobalClass = Class.forName(modGlobalClassStr);
						String namePy=g.getNamePy();
						List<String> needModGlobalNamepysList=initConfig.getNeedModGlobalNamepysList();
						if(needModGlobalNamepysList.contains(namePy)){
							//需要级联
							if(null!=modGlobalClass){
								String modMethodStr=g.getNamePy();
								Method modM = modGlobalClass.getDeclaredMethod(modMethodStr, Global.class,AbstractBizI.class);
								if(null!=modM){
									g=(Global) modM.invoke(g, g,aBiz);
									if(null!=g){
										//级联修改成功
										aBiz.modifyObject(g);
										success(req);
									}
								}
							}
						}else{
							//不需要级联
							aBiz.modifyObject(g);
							success(req);
						}
					}else{
						
						
						if(id.intValue()==1){
							boolean canMod=false;
							int newmyValue=new Integer(myValue).intValue();
							//如果要修改最后的10分钟的10，那么就必须保证新值<g2(每次加时g2分钟)
							Global g2=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 2);
							if(null!=g2){
								int addInte=new Integer(g2.getMyValue()).intValue();
								if(newmyValue<addInte){
									canMod=true;
								}
							}
							if(canMod){
								//ok，这时候才能修改
								g.setMyValue(myValue);
								aBiz.modifyObject(g);
								success(req);
							}else{
								throw new MyStrException("抱歉,您要修改的值必须要小于每次加时的"+g2.getMyValue()+"数值");
							}
						}else if(id.intValue()==2){
							boolean canMod=false;
							//如果要修改每次加时5分钟的5，那么久必须保证g1的值要小于这个新值
							int newmyValue=new Integer(myValue).intValue();
							Global g1=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager,1);
							if(null!=g1){
								int lastT=new Integer(g1.getMyValue()).intValue();
								if(newmyValue>lastT){
									canMod=true;
								}
							}
							if(canMod){
								//ok，这时候才能修改
								g.setMyValue(myValue);
								aBiz.modifyObject(g);
								success(req);
							}else{
								throw new MyStrException("抱歉,您要修改的值必须要大于最后"+g1.getMyValue()+"分钟检测是否要加时的数字");
							}
						}else if(id.intValue()==9){
							//专场结束什么时候退还保证金，必须大于等于1天
							int myValueI = new Integer(myValue).intValue();
							if(myValueI<=0){
								throw new MyStrException("抱歉，您要修改的值必须大于等于1天");
							}else{
								Global g6=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 6);
								Global g7=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 7);
								if(null!=g6 && null!=g7){
									int g67=new Integer(g6.getMyValue()).intValue()+new Integer(g7.getMyValue()).intValue();
									if(myValueI<=g67){
										throw new MyStrException("抱歉，所设的值必须大于[中标后N天不付款宣布失败]+[失败后M天自动将冻结资金转给商家]之和");
									}else{
										g.setMyValue(myValue);
										aBiz.modifyObject(g);
										success(req);
									}
								}
							}
						}else if(id.intValue()==12){
							//平台收取的店铺入驻费用
							/**
							 * 如果从0变成1000，也就是从不收费变成收费，以前不收费而开通的就开通了吧。
							 * 如果从1000变成0，也就是收取费用，那么将wait状态的店铺的状态修改为ok
							 */
							String old = g.getMyValue();
							float oldShopRegistPrice = new Float(old).floatValue();
							float newShopRegistPrice = new Float(myValue).floatValue();
							if(oldShopRegistPrice>0 && newShopRegistPrice==0){
								//以前收费，现在不收费了。
								aBiz.j_execute("update shop set status='OK' where status='WAIT'", null);
							}
						}else{
							g.setMyValue(myValue);
							aBiz.modifyObject(g);
							success(req);
						}
						
						
					}
					
				}
			}
		}else{
			throw new MyRefererException("MyRefererException");
		}
	}
	
	
	
	@RequestMapping(value="/findGlobalById")
	public String findGlobalById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm nForm =this.getSDynaActionForm(req);
		Integer id = (Integer)nForm.getInteger("id");
		if(null!=id){
			if(id!=0){
				Global g = (Global)aBiz.findObjectById("from Global as g where g.id=?", new Object[]{id});
				if(null!=g){
					req.setAttribute(AppJsonResultUtil.app_+"global", g);
				}
			}
		}
		return "global";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
