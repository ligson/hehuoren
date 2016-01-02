package myFrameU.history.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import myFrameU.util.commonUtil.json.JSONUtils;

/**
 * 
 * 历史操作记录
 *
 */
public class History   implements Serializable{
	private int id;
	private String historyType;
	//将安全级别高的做历史记录，如登录，如操作账户（申请提现，充值，修改财务密码）
	public enum HISTROYTYPE{LOGIN,ACCOUNT}
	
	private String whoclassName;
	private String whoName;
	private int whoId;
	
	
	private String title;
	private Date relDate;
	private String ip;
	private String addressStr;
	
	
	private String extraData;
	//=================================
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


	

	public String getHistoryType() {
		return historyType;
	}


	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}


	public String getWhoclassName() {
		return whoclassName;
	}


	public void setWhoclassName(String whoclassName) {
		this.whoclassName = whoclassName;
	}


	public String getWhoName() {
		return whoName;
	}


	public void setWhoName(String whoName) {
		this.whoName = whoName;
	}


	public int getWhoId() {
		return whoId;
	}


	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Date getRelDate() {
		return relDate;
	}


	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getExtraData() {
		return extraData;
	}


	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}


	public String getAddressStr() {
		return addressStr;
	}


	public void setAddressStr(String addressStr) {
		this.addressStr = addressStr;
	}
	
	
	
}
