package myFrameU.quartz.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import myFrame.quartz.QuartzInitPriority;
import myFrameU.exception.exception.MyStrException;
import myFrameU.util.commonUtil.date.DateUtils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;

public class JobManager {
	/** 计划任务map */
	private static Map<String, ScheduleJob> jobMap = new HashMap<String, ScheduleJob>();
	
	//存放过期了的
	private static Map<String,ScheduleJob> jobMapExpired=new HashMap<String,ScheduleJob>();

	public static Map<String, ScheduleJob> getJobMap() {
		return jobMap;
	}
	public static Map<String, ScheduleJob> getJobMapExpired() {
		return jobMapExpired;
	}
	public static ScheduleJob getScheduleJob(String group,String name){
		return jobMap.get(group+"_"+name);
	}
	
	public static void addJob(ScheduleJob scheduleJob) throws Exception{
		try{
		//	System.out.println("添加之前的map的大小："+jobMap.size()+"><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			/*boolean isExpired=scheduleJob.isExpired();
			if(isExpired){
				//过期了就存在这个map里
				jobMapExpired.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(),scheduleJob);
			}else{
				jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(),scheduleJob);
				StartQuartz.initScheduleJob(scheduleJob);
			}*/
			jobMap.put(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName(),scheduleJob);
			StartQuartz.initScheduleJob(scheduleJob);
		}catch(SchedulerException e){
			jobMap.remove(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName());
		}catch(Exception e){
			jobMap.remove(scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName());
			throw e;
		}finally{
			//System.out.println("添加之后的map的大小："+jobMap.size()+"><<<<<");
		}
		
	}

	// 暂停
	public static void pauseJob(ScheduleJob job) throws Exception {
		try{
			//System.out.println("暂停之前的map的大小："+jobMap.size()+"><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			if(null!=job){
				JobKey jobKey = JobKey.jobKey(job.getJobName(),
						job.getJobGroup());
				System.out.println("暂停:"+job.getJobGroup()+"_"+job.getJobName());
				StartQuartz.scheduler.pauseJob(jobKey);
			}else{
				//throw new MyStrException("ScheduleJob未匹配的到");
			}
		}catch(SchedulerException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			throw e;
		}finally{
			//System.out.println("暂停之后的map的大小："+jobMap.size()+"><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
	}

	// 恢复
	public static void resumeJob(ScheduleJob job) throws Exception {
		try{
			//System.out.println("恢复之前的map的大小："+jobMap.size()+"><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			if(null!=job){
				JobKey jobKey = JobKey.jobKey(job.getJobName(),
						job.getJobGroup());
				StartQuartz.scheduler.resumeJob(jobKey);
			}else{
				//throw new MyStrException("ScheduleJob未匹配的到");
			}
		}catch(SchedulerException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			throw e;
		}finally{
			//System.out.println("恢复之后的map的大小："+jobMap.size()+"><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
		
		
	}

	// del
	public static void deleteJob(ScheduleJob job) throws Exception {
		try{
			//System.out.println("删除之前的map的大小："+jobMap.size()+"><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			if(null!=job){
				JobKey jobKey = JobKey.jobKey(job.getJobName(),
						job.getJobGroup());
				StartQuartz.scheduler.deleteJob(jobKey);
			}else{
				//throw new MyStrException("ScheduleJob未匹配的到");
			}
		}catch(SchedulerException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			throw e;
		}finally{
			//System.out.println("删除之后的map的大小："+jobMap.size()+"><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		}
		
		
	}

	
	public static void replaceExpression(ScheduleJob job) throws Exception{
		try{
			if(null!=job){
				StartQuartz.initScheduleJob(job);
			}else{
				//throw new MyStrException("ScheduleJob未匹配的到");
			}
			
		}catch(SchedulerException e){
			System.out.println(e.getMessage());
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public static void removeFromMap(ScheduleJob scheduleJob){
		if(null!=scheduleJob){
			String key=scheduleJob.getJobGroup() + "_" + scheduleJob.getJobName();
			jobMap.remove(key);
		}
	}
	
	
/*	
	
	*//**
	 * 立即执行那些过期的job
	 *//*
	public static void dealWithDateOverJob(ScheduleJob job){
		try{
			String s=job.getCronExpression();
			//String s = "00 45 18 22 05 ? 2015";
			s=s.trim();
			String[] array=s.split(" ");
			StringBuffer newSB=new StringBuffer();
			if(array.length==7){
				//String zhou=array[5];//周
				newSB.append(array[6]).append("-").append(array[4]).append("-").append(array[3]).append(" ").append(array[2])
				.append(":").append(array[1]).append(":").append(array[0]);
				String newS=newSB.toString();
				System.out.println(newS);
				boolean isDate=DateUtils.isDate(newS, DateUtils.formatStr_yyyyMMddHHmmss);
				if(isDate){
					Date date = DateUtils.parse(newS, DateUtils.formatStr_yyyyMMddHHmmss);
					//确定是日期，然后判断日期是否过期
					Date now=new Date();
					long dtime=date.getTime();
					long ntime=now.getTime();
					if(ntime<=dtime){
						//过期了
					}
					System.out.println(date);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
