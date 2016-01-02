package myFrameU.queryArgs.entityForm;

import java.util.HashMap;

public class EntityFormUtil {
	private static HashMap<String,EntityForm> entityFormMap = new HashMap<String,EntityForm>();
	public static EntityForm getEntityForm(String className){
		return entityFormMap.get(className);
	}
}
