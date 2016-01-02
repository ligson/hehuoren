package hhr.test.biz;

import hhr.test.entity.TestEntity;
import myFrameU.biz.AbstractBizI;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.exception.exception.MyStrException;

import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
@Service("tt1Biz")
public class TestTranBizImpl1 extends AbstractBizImpl implements TestTranBizI {
	
	@Override
	public void testTran() throws Exception {
		
		
		
		TestEntity te = new TestEntity();
		te.setName("new");
		te.setPassword("new");
		aDao.insertObject(te);
		Thread.sleep(10*1000);
		
	}

	@Override
	public void testTran2() throws Exception {
		
		throw new Exception(".....................//");
	}
	
	

}
