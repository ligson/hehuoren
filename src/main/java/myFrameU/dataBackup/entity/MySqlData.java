package myFrameU.dataBackup.entity;

import java.io.Serializable;
import java.util.Date;

public class MySqlData implements Serializable{
	private int id;
	private String filePath;
	private String size;
	//private Date relDate;
	private String relDate;
	
	private int current;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}


	public String getRelDate() {
		return relDate;
	}

	public void setRelDate(String relDate) {
		this.relDate = relDate;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
	
}
