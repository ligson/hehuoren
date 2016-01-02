package myFrameU.weixin.base.entity;

import java.util.List;

public class TemplateMsg {
	private String url;
	private String openId;
	private String template_id;
	private List<TemplateData> dataList;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public List<TemplateData> getDataList() {
		return dataList;
	}
	public void setDataList(List<TemplateData> dataList) {
		this.dataList = dataList;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
