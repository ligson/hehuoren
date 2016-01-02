package test;

import java.util.HashMap;
import java.util.Map;

import myFrameU.util.commonUtil.json.JSONUtils;
import myFrameU.weixin.base.entity.IfNosubscribe_subscribeEvent;

public class Test8 {

	public static void main(String[] args) {
		
		String jsonstring = toJSONString();
		
		IfNosubscribe_subscribeEvent e = toBean(jsonstring);
		System.out.println(e.getEventType());
		HashMap<String,String> argsMap = (HashMap<String, String>) e.getArgs();
		
		System.out.println(argsMap.get("heId"));
	}
	
	public static IfNosubscribe_subscribeEvent toBean(String jsonstring){
		return JSONUtils.toBean(jsonstring, IfNosubscribe_subscribeEvent.class);
	}
	
	public static String toJSONString(){
		IfNosubscribe_subscribeEvent e = new IfNosubscribe_subscribeEvent();
		e.setEventType(IfNosubscribe_subscribeEvent.EVENTTYP.BeHeFensi.toString());
		Map<String,String> argsMap = new HashMap<String,String>();
		argsMap.put("heId", 8+"");
		e.setArgs(argsMap);
		
		String s = JSONUtils.toJSONString(e);
		System.out.println(s);
		return s;
	}

}
