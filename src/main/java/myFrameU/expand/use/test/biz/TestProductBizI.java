package myFrameU.expand.use.test.biz;

import myFrameU.expand.use.biz.ExpandUseBizI;
import myFrameU.expand.use.test.entity.TestProduct;

public interface TestProductBizI extends ExpandUseBizI {
	public void addProduct(TestProduct pro) throws Exception;
	
}
