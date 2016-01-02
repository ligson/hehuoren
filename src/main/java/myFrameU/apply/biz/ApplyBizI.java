package myFrameU.apply.biz;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import myFrameU.apply.entity.Apply;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;


public interface ApplyBizI{
	//操作通过不通过
	public Apply applyOK(Apply apply,UICacheManager uICacheManager) throws Exception;
	public Apply applyERROR(Apply apply,UICacheManager uICacheManager) throws Exception;
	
	//findById的自己的业务展示，比如我申请参加展会A，那么后台在查看这个申请的时候，需要级联查看这个展会信息。
	public void findById(Apply apply,HttpServletRequest req,UICacheManager uICacheManager) throws Exception;
	
	/**
	 * 所有的submit 申请，都是通过ApplyController这个来访问的
	 * 但是这时候就有问题了，每个apply的title和content都不同，或者是到底能不能提交这个申请都不清楚
	 * 这时候就调用各自的createApply方法
	 */
	public Apply createApply(HttpServletRequest req,UICacheManager uICacheManager) throws Exception;
	
	
	
	
	
}
