package myFrameU.expand.use.test.biz;

import org.springframework.stereotype.Service;

import myFrameU.expand.use.biz.ExpandUseBizImpl;
import myFrameU.expand.use.test.entity.TestProduct;
@Service("proBiz")
public class TestProductBizImpl extends ExpandUseBizImpl implements TestProductBizI {
	@Override
	public void addProduct(TestProduct pro) throws Exception {
		aDao.insertObject(pro);
	}

}
