package myFrameU.sms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import myFrameU.util.commonUtil.json.JSONUtils;

/**
 * 
 * 短信记录
 *
 */
public class SMSRecord   implements Serializable{
	private int id;
	private Date relDate;
	private String telPhone;
	private String sdkMtType;//类型,请参考getConstnat
	private String sendOrRec;//是接收还是发送。S是发送，R是接收
	private String content;
	private String ext;
	private String stime;
	private String rrid;
	private String sdkResult;
	
	
	private String smsRecordOtherJSON;
	
	
	
	
	
	
	public List<SmsRecordOther> getOtherList(){
		return (List<SmsRecordOther>) JSONUtils.toBean(getSmsRecordOtherJSON(), SmsRecordOther.class);
	}
	public String getOtherValue(String key){
		List<SmsRecordOther> list = (List<SmsRecordOther>) JSONUtils.toBean(getSmsRecordOtherJSON(), SmsRecordOther.class);
		if(null!=list){
			int size = list.size();
			if(size>0){
				if(size==1){
					SmsRecordOther so=list.get(0);
					if(so.getKey().equals(key)){
						return so.getValue();
					}
				}else{
					SmsRecordOther so=null;
					String key1=null;
					for(int i=0;i<size;i++){
						so=list.get(i);
						key1=so.getKey();
						if(null!=key1){
							if(key1.equals(key)){
								return so.getValue();
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	/*private int userId;
	private int shopId;
	private int osoiId;*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getRelDate() {
		return relDate;
	}
	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getSdkMtType() {
		return sdkMtType;
	}
	public void setSdkMtType(String sdkMtType) {
		this.sdkMtType = sdkMtType;
	}
	public String getSendOrRec() {
		return sendOrRec;
	}
	public void setSendOrRec(String sendOrRec) {
		this.sendOrRec = sendOrRec;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getRrid() {
		return rrid;
	}
	public void setRrid(String rrid) {
		this.rrid = rrid;
	}
	
	public String getSdkResult() {
		return sdkResult;
	}
	public void setSdkResult(String sdkResult) {
		this.sdkResult = sdkResult;
	}
	public String getSmsRecordOtherJSON() {
		return smsRecordOtherJSON;
	}
	public void setSmsRecordOtherJSON(String smsRecordOtherJSON) {
		this.smsRecordOtherJSON = smsRecordOtherJSON;
	}
	
	
	
	
	
	
	
	
	
	
}
