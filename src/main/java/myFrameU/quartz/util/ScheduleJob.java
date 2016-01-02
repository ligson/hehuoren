package myFrameU.quartz.util;

import java.util.Date;
import java.util.HashMap;

import myFrameU.util.commonUtil.date.DateUtils;


public class ScheduleJob<T> {
	/** 任务id */
    private String jobId;
 
    /** 任务名称 */
    private String jobName;
 
    /** 任务分组 */
    private String jobGroup;
 
    /** 任务状态 0禁用 1启用 2删除*/
    private String jobStatus;
 
    /** 任务运行时间表达式 */
    private String cronExpression;
 
    /** 任务描述 */
    private String desc;
    
    
    private boolean expired;//是否过期，默认为false（不过期），true为过期

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		/*if(isExpired()){
			//ss mm HH dd MM ? yyyy
			//2222年，1月，1日，0点，0分，0秒
			this.cronExpression = "00 00 00 01 01 ? 2222";
		}else{
			this.cronExpression = cronExpression;
		}*/
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	private HashMap<String,T> extraDataMap;

	public HashMap<String, T> getExtraDataMap() {
		return extraDataMap;
	}

	public void setExtraDataMap(HashMap<String, T> extraDataMap2) {
		this.extraDataMap = extraDataMap2;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	
	
	
	
	
	
	
	
	
	
	
    
}
