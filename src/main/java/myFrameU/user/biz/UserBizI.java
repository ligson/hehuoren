package myFrameU.user.biz;

import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;
import myFrameU.user.entity.User;

public interface UserBizI extends AbstractBizI{
	public void registUser(User user,Address a) throws Exception;
}
