package myFrameU.sms.sdkN.entity;

public class SendResultEntity {
	/**
	 * <?xml version="1.0" encoding="utf-8" ?><returnsms>
			 <returnstatus>Success</returnstatus>
			 <message>ok</message>
			 <remainpoint>24</remainpoint>
			 <taskID>490742</taskID>
			 <successCounts>1</successCounts></returnsms>
	 */
	private String returnstatus;
	private String message;
	private String remainpoint;
	private String taskID;
	private String successCounts;
	public String getReturnstatus() {
		return returnstatus;
	}
	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRemainpoint() {
		return remainpoint;
	}
	public void setRemainpoint(String remainpoint) {
		this.remainpoint = remainpoint;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public String getSuccessCounts() {
		return successCounts;
	}
	public void setSuccessCounts(String successCounts) {
		this.successCounts = successCounts;
	}
	
	
	
}
