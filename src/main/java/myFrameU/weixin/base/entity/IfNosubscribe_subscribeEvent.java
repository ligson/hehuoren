package myFrameU.weixin.base.entity;

import java.util.Map;

public class IfNosubscribe_subscribeEvent {
	private String eventType;
	public enum EVENTTYP{
		BeHeFensi
	}
	private Map<String,String> args;
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Map<String, String> getArgs() {
		return args;
	}
	public void setArgs(Map<String, String> args) {
		this.args = args;
	}
	
}
