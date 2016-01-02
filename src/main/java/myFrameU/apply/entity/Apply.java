package myFrameU.apply.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import myFrameU.util.commonUtil.json.JSONUtils;

/**
 * 
 * 申请
 *
 */
public class Apply   implements Serializable{
	private int id;
	//Sponsor发起人
	private String sponsorClassName;
	private int sponsorId;//发起人ID
	private String sponsorName;
	
	//审批人
	private String dealWithClassName;//
	private int dealWithId;
	
	
	private String applyTypeKey;
	private String applyTitle;
	private String applyContent;
	private String speed;//审批进度
	public enum SPEED{WAIT,ING,FINISH};//等待审批，正在审批，完成审批
	private String result;
	public enum RESULT{APPLYOK,APPLYERROR};//审批结果，通过审批，不通过审批,
	
	private String remarks;//备注。
	
	private Date dealDate;
	private Date relDate;//发起时间

	
	/**
	 * {'zhanhuiId':'4','dsId':'3','sssId':'v'}
	 */
	private String extraData;//额外的数据，如要申请参加展会，你得保存下它要参加哪个展会吧，这样才能审批通过不通过
	
	//=====================================================================
	//根据extraData来生成map
	private HashMap<String,String> extraDataMap;

	
	public HashMap<String, String> getExtraDataMap() {
		return JSONUtils.toHashMap(extraData);
	}


	public void setExtraDataMap(HashMap<String, String> extraDataMap) {
		this.extraDataMap = extraDataMap;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getSponsorClassName() {
		return sponsorClassName;
	}


	public void setSponsorClassName(String sponsorClassName) {
		this.sponsorClassName = sponsorClassName;
	}


	public int getSponsorId() {
		return sponsorId;
	}


	public void setSponsorId(int sponsorId) {
		this.sponsorId = sponsorId;
	}


	public String getDealWithClassName() {
		return dealWithClassName;
	}


	public void setDealWithClassName(String dealWithClassName) {
		this.dealWithClassName = dealWithClassName;
	}


	public int getDealWithId() {
		return dealWithId;
	}


	public void setDealWithId(int dealWithId) {
		this.dealWithId = dealWithId;
	}


	public String getApplyTypeKey() {
		return applyTypeKey;
	}


	public void setApplyTypeKey(String applyTypeKey) {
		this.applyTypeKey = applyTypeKey;
	}


	public String getApplyTitle() {
		return applyTitle;
	}


	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}


	public String getApplyContent() {
		return applyContent;
	}


	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}


	public String getSpeed() {
		return speed;
	}


	public void setSpeed(String speed) {
		this.speed = speed;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Date getRelDate() {
		return relDate;
	}


	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}


	public Date getDealDate() {
		return dealDate;
	}


	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}


	public String getExtraData() {
		return extraData;
	}


	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}


	public String getSponsorName() {
		return sponsorName;
	}


	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	
	
	
}
