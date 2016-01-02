package myFrame.quartz;

import hhr.message.MessageUtil;
import hhr.order.entity.Order;
import hhr.user.util.UserUtil;

import java.util.Date;
import java.util.HashMap;

import myFrame.cache.CacheKey;
import myFrameU.account.biz.AccountBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.global.entity.Global;
import myFrameU.quartz.util.ScheduleJob;
import myFrameU.quartz.util.StartQuartz;
import myFrameU.user.entity.User;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(rollbackFor=Exception.class)
@Service("MyQuartzJobExcute")
public class MyQuartzJobExcute<T>  implements Job {
	
	public static void printMessage(ScheduleJob scheduleJob){
		 String group = scheduleJob.getJobGroup();
	     String name=scheduleJob.getJobName();
	     String id = scheduleJob.getJobId();
	     if(group.equals("USER_")){
	    	 if(name.startsWith("SYQ_")){
	    		 System.out.println("quartz运行[USER_SYQ_ID]---：试用期过了，要检测是否可以转正");
	    	 }
	     }else if(group.equals("ORDER_")){
	    	 if(name.startsWith("WAITCLOSE_")){
	    		 System.out.println("quartz运行[ORDER_WAITCLOSE_]---：订单过了7天，要自动关闭");
	    	 }
	     }
	        
	}
	
	
	@Override
	@Transactional(rollbackFor=Exception.class)
    public void execute(JobExecutionContext context) {
		AccountBizI accountBiz = StartQuartz.accountBiz;
		UICacheManager uICacheManager = StartQuartz.uICacheManager;
		try{
	        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
	        printMessage(scheduleJob);
	        String group = scheduleJob.getJobGroup();
	        String name=scheduleJob.getJobName();
	        String id = scheduleJob.getJobId();
	        HashMap<String,T> extraDataMap = scheduleJob.getExtraDataMap();
	        if(group.equals("USER_")){
	        	if(null!=extraDataMap){
	        		User user = (User)extraDataMap.get("user");
	        		if(null!=user){
	        			int userId = user.getId();
	        			user = (User)accountBiz.findObjectById("from User as u where u.id=?", new Object[]{userId});
	        			if(null!=user){
	        				if(name.equals("SYQ_"+user.getId())){
	        					//试用期到了，要开始验收了啊.......................................
	        					Global g16=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 16);
	        					if(null!=g16){
	        						String g16Value = g16.getMyValue();
	        						if(null!=g16Value && !g16Value.equals("")){
	        							int mustFSCount = new Integer(g16Value).intValue();
	        							int fscount = UserUtil.getFSCount(userId, accountBiz);
	    	        					if(fscount>=mustFSCount){
	    	        						//可以转正了..............
	    	        						user.setBeDate(new Date());
	    	        						user.setUserLevelId(3);//真正的合伙人
	    	        						accountBiz.modifyObject(user);
	    	        						
	    	        						
	    	        						HashMap<String,Object> args=new HashMap<String,Object>();
											args.put("user", user);
											MessageUtil.sendMssage(MessageUtil.MssageTYPE.HHR_SYQOVER_OK.toString(),
													MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), 
													args,accountBiz);
	    	        						
	    	        						
	    	        						
	    	        					}else{
	    	        						//不可以转正..............
	    	        						user.setUserLevelId(1);//降为普通的客户
	    	        						user.setBeProbationDate(null);
	    	        						user.setFensiCount(0);
	    	        						accountBiz.modifyObject(user);
	    	        						
	    	        						/**
	    	        						 * 原先已经是他的粉丝的用户，因为他的推荐人已经降为普通人了，所以也要消除
	    	        						 * 发送message
	    	        						 */
	    	        						String sql="update user_user set tuijianRenId=0,tuijianRenNicheng='' where tuijianRenId=?";
	    	        						accountBiz.j_execute(sql, new Object[]{user.getId()});
	    	        					}
	    	        					//发短信..........................
	    	        					//message
	    	        					/**
	    	        					 * 1、短信
	    	        					 * 2、微信
	    	        					 */
	    	        					
	        						}
	        					}
	        				}
	        			}
	        		}
	        	}
	        }else if(group.equals("ORDER_")){
	        	if(null!=extraDataMap){
	        		Order o = (Order)extraDataMap.get("order");
	        		if(null!=o){
	        			int orderId = o.getId();
	        			o = (Order)accountBiz.findObjectById("from Order as o where o.id=?", new Object[]{orderId});
	        			if(null!=o){
	        				o.setStatus(Order.STATUS.CLOSE.toString());
	        				accountBiz.modifyObject(o);
	        				
	        				
	        			}
	        		}
	        	}
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
    }
}
