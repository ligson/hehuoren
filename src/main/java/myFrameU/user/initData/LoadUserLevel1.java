package myFrameU.user.initData;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.initData.AbstractWebInit;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.user.entity.User;

public class LoadUserLevel1 extends AbstractWebInit{
	
	@Override
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		
		/**
		 * 存储虚拟用户
		 */
		HashMap<String,User> level1UsersMap = new HashMap<String,User>();
		List<User> userList = (List<User>)aBiz.findObjectList(User.class, null, "from User as u where u.userLevelId=1", null, false, 0, 0);
		if(null!=userList){
			int size = userList.size();
			User user = null;
			for(int i=0;i<size;i++){
				user=userList.get(i);
				level1UsersMap.put("userId_"+user.getId(), user);
			}
			sc.setAttribute("level1UsersMap", level1UsersMap);
		}
		
	}

	
	
	
	
	
	
	
}
