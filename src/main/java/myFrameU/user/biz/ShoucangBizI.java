package myFrameU.user.biz;

import myFrameU.biz.AbstractBizI;
import myFrameU.user.entity.Shoucang;

public interface ShoucangBizI extends AbstractBizI{
	public void  shoucang(Shoucang sc)  throws Exception;
	
	public void removeSc(Shoucang sc) throws Exception;
}
