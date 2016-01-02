package myFrameU.integration.init;

import java.util.HashMap;
import java.util.Map;

public class InitConfig{
	private Class initMavenClass;
	private Map<String,RoleCaseFieldEntity> rcfeMap;
	
	
	//		myFrame...Shop,<updateLogo,ItemRule>
	private Map<String,HashMap<String,ItemRule>> itemRulesMap=null;
	
	
	
	
	
	
	
	
	
	
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public Map<String, RoleCaseFieldEntity> getRcfeMap() {
		return rcfeMap;
	}
	public void setRcfeMap(Map<String, RoleCaseFieldEntity> rcfeMap) {
		this.rcfeMap = rcfeMap;
	}
	public Map<String, HashMap<String, ItemRule>> getItemRulesMap() {
		return itemRulesMap;
	}
	public void setItemRulesMap(Map<String, HashMap<String, ItemRule>> itemRulesMap) {
		this.itemRulesMap = itemRulesMap;
	}

	
}
