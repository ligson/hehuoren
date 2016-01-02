package myFrameU.user.entity;

import java.io.Serializable;
import java.util.Date;

public class Shoucang  implements Serializable{
	private int id;
	private int userId;
	private String scEntity;//被收藏的className
	private int scEntityId;//被收藏的id
	
	private Date relDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getScEntity() {
		return scEntity;
	}
	public void setScEntity(String scEntity) {
		this.scEntity = scEntity;
	}

	public int getScEntityId() {
		return scEntityId;
	}
	public void setScEntityId(int scEntityId) {
		this.scEntityId = scEntityId;
	}
	public Date getRelDate() {
		return relDate;
	}
	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}
	
}
