package myFrameU.integration.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 积分明细
 *
 */
public class IntegrationItem implements Serializable{
	private int id;
	private Integration integration;
	private Date relDate;
	private String event;//事件
	private String smallEvent;
	private int addOrMinus;//是减分还是加分。1代表加分，2代表减分
	private float fraction;//分数

	private String who;
	private int whoId;
	private int addressId;
	private String addressTreeIds;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integration getIntegration() {
		return integration;
	}

	public void setIntegration(Integration integration) {
		this.integration = integration;
	}

	public Date getRelDate() {
		return relDate;
	}

	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getAddOrMinus() {
		return addOrMinus;
	}

	public void setAddOrMinus(int addOrMinus) {
		this.addOrMinus = addOrMinus;
	}

	public float getFraction() {
		return fraction;
	}

	public void setFraction(float fraction) {
		this.fraction = fraction;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public int getWhoId() {
		return whoId;
	}

	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}

	public String getSmallEvent() {
		return smallEvent;
	}

	public void setSmallEvent(String smallEvent) {
		this.smallEvent = smallEvent;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddressTreeIds() {
		return addressTreeIds;
	}

	public void setAddressTreeIds(String addressTreeIds) {
		this.addressTreeIds = addressTreeIds;
	}
	
	
}
