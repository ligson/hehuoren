package myFrameU.quartz.util;

import myFrame.quartz.MyJobManager;
import myFrameU.account.biz.AccountBizI;
import myFrameU.ehcache.util.UICacheManager;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;



public class StartQuartz {
	public static Scheduler scheduler;
	public static Class QuartzJobExcuteClass;
	public static AccountBizI accountBiz;
	//public static OrderBizImpl orderBiz;
	public static UICacheManager uICacheManager;
	
	
	
	
	
	//schedulerFactoryBean 由spring创建注入
	public static void execute() throws Exception{
		MyJobManager.initJobsFromDatabase();
	}
	
	
	
	
	
	public static void initScheduleJob(ScheduleJob job) throws Exception{
		try{
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			 
			//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		 
			//不存在，创建一个
			if (null == trigger) {
				JobDetail jobDetail = JobBuilder.newJob(QuartzJobExcuteClass)
					.withIdentity(job.getJobName(), job.getJobGroup()).build();
				jobDetail.getJobDataMap().put("scheduleJob", job);
		 
				//表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression());
		 
				//按新的cronExpression表达式构建一个新的trigger
				trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
		 
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				//表达式调度构建器
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
					.getCronExpression());
		 
				//按新的cronExpression表达式重新构建trigger
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
					.withSchedule(scheduleBuilder).build();
		 
				//按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
		}catch(SchedulerException e){
			String em = e.getMessage();
			if(em.endsWith("will never fire.")){
				System.out.println("这个时间策略已经过期，永远不会执行，所以报错");
			}
			throw e;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
