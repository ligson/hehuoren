package myFrame.quartz.initData;



import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.user.entity.User;

public class LoadHHRSyq extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		/**
		 * 找出所有在试用期的user
		 */
		List<User> userList = (List<User>)aBiz.findObjectList(User.class, null, 
				"from User as u where u.userLevelId=2", null, false, 0, 0);
		if(null!=userList){
			HashMap<String,User> userSYQMap = new HashMap<String,User>();
			int size = userList.size();
			User user = null;
			for(int i=0;i<size;i++){
				user = userList.get(i);
				userSYQMap.put("userId_"+user.getId(), user);
			}
			uICacheManager.putObjectCached("web", "userSYQMap", userSYQMap);
		}
		
		
		
		
	}

	
	
	
	
	
	
	
}
