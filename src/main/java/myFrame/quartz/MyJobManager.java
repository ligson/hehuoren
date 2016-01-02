package myFrame.quartz;

import hhr.order.entity.Order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myFrame.cache.CacheKey;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.global.entity.Global;
import myFrameU.quartz.util.JobManager;
import myFrameU.quartz.util.ScheduleJob;
import myFrameU.quartz.util.StartQuartz;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.date.DateUtils;
import myFrameU.util.commonUtil.enumU.EnumUtil;

/**
 * 
 * 根据本项目，重新再次包装JobManager管理器
	[秒] [分] [小时] [日] [月] [周] [年] 
	*	*	*	*	*	*	*
 */
public class MyJobManager extends JobManager{
	/**
	 * 优先级执行顺序
	 * 		拍品-----》专场统计----》专场退还保证金
	 * 		now-------now+5-----------now+10
	 */
	
	public static void initJobsFromDatabase(){
		System.out.println("从数据库中拿出还没处理的job来");
		UICacheManager uICacheManager =StartQuartz.uICacheManager;
		
		
		List<Order> orderList = (List<Order>)uICacheManager.getObjectCached("web", "orderWait");
		if(null!=orderList){
			int size = orderList.size();
			Order order = null;
			for(int i=0;i<size;i++){
				order= orderList.get(i);
				ORDER_WAIT_CLOSE(order);
			}
		}
		HashMap<String,User> userSYQMap=(HashMap<String,User>)uICacheManager.getObjectCached("web", "userSYQMap");
		if(null!=userSYQMap){
			String userIdStr = null;
			User user = null;
			int levelId=0;
			for(Map.Entry<String, User> entry: userSYQMap.entrySet()) {
				userIdStr = entry.getKey();
				user = entry.getValue();
				levelId=user.getUserLevelId();
				if(levelId==2){
					USER_SYQ(user);
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	
	//将某一个auction在它规定的时间点儿上唤醒它，将状态改装为ing
		public static void USER_SYQ(User user){
			UICacheManager uICacheManager =StartQuartz.uICacheManager;
			try{
				HashMap<String,User> extraDataMap=new HashMap<String,User>();
				extraDataMap.put("user", user);
				Date beProbationDate = user.getBeProbationDate();
				if(null!=beProbationDate){
					Global g15=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 15);
					if(null!=g15){
						String g15Value = g15.getMyValue();
						if(null!=g15Value && !g15Value.equals("")){
							int timeDay = new Integer(g15Value).intValue();
							if(timeDay>=1){
								beProbationDate=DateUtils.getNextDate_date(beProbationDate, timeDay);
								Date now = new Date();
								long ntime=now.getTime();
								
								ScheduleJob scheduleJob = new ScheduleJob();
								scheduleJob.setJobGroup("USER_");
								scheduleJob.setJobName("SYQ_"+user.getId());
								scheduleJob.setJobId(""+user.getId());
								scheduleJob.setJobStatus("1");
								scheduleJob.setDesc("");
								scheduleJob.setExpired(false);
								String e=null;
								
								long bdTime=beProbationDate.getTime();
								//如果不是项目刚启动时候注册的job
								//如果过期了，就立即执行
								//如果不过期，就正常注册
								if(ntime>=bdTime){
									//过期了
									e=DateUtils.format(now, DateUtils.SS_MM_HH_DD_MM_YYYY);
								}else{
									e=DateUtils.format(beProbationDate, DateUtils.SS_MM_HH_DD_MM_YYYY);
								}
								
								System.out.println("-------------->>>>.-------------e="+e);
								scheduleJob.setCronExpression(e);
								scheduleJob.setExtraDataMap(extraDataMap);
								JobManager.addJob(scheduleJob);
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
	
	
			//将等待...的订单关闭
			public static void ORDER_WAIT_CLOSE(Order o){
				if(null!=o){
					String orderPayType = o.getOrderPayType();
					if(EnumUtil.equalsE(Order.ORDERPAYTYPE.Unknown, orderPayType)
							|| EnumUtil.equalsE(Order.ORDERPAYTYPE.Huodaofukuan, orderPayType) ){
						UICacheManager uICacheManager =StartQuartz.uICacheManager;
						try{
							HashMap<String,Order> extraDataMap=new HashMap<String,Order>();
							extraDataMap.put("order", o);
							Date beginDate = o.getCreateDate();
							if(null!=beginDate){
								beginDate=DateUtils.getNextDate_date(beginDate, 7);
								Date now = new Date();
								long ntime=now.getTime();
								
								ScheduleJob scheduleJob = new ScheduleJob();
								scheduleJob.setJobGroup("ORDER_");
								scheduleJob.setJobName("WAITCLOSE_"+o.getId());
								scheduleJob.setJobId(""+o.getId());
								scheduleJob.setJobStatus("1");
								scheduleJob.setDesc("");
								scheduleJob.setExpired(false);
								String e=null;
								
								long bdTime=beginDate.getTime();
								//如果不是项目刚启动时候注册的job
								//如果过期了，就立即执行
								//如果不过期，就正常注册
								if(ntime>=bdTime){
									//过期了
									e=DateUtils.format(now, DateUtils.SS_MM_HH_DD_MM_YYYY);
								}else{
									e=DateUtils.format(beginDate, DateUtils.SS_MM_HH_DD_MM_YYYY);
								}
								
								System.out.println("-------------->>>>.-------------e="+e);
								scheduleJob.setCronExpression(e);
								scheduleJob.setExtraDataMap(extraDataMap);
								JobManager.addJob(scheduleJob);
								
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				
			}
			
	
	
	
}
