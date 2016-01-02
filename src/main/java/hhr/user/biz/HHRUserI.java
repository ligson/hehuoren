package hhr.user.biz;

import java.util.HashMap;

import myFrameU.account.biz.AccountBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.user.entity.User;

public interface HHRUserI {
	public void beFensi(User user,User he,UICacheManager uICacheManager,AccountBizI accountBiz) throws Exception;
	
	//查找一个user的所有的粉丝，and，所有粉丝的所有订单
	//返回值：fensiList，fensiOrderList
	public HashMap<String,Object> getUser_fs_and_fsOrders(User user) throws Exception;
	
}
