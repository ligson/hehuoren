package hhr.test.biz;

import hhr.test.entity.TestEntity;
import myFrameU.biz.AbstractBizI;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.dao.AbstractDaoI;
import myFrameU.exception.exception.MyStrException;

import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
@Service("ttBiz")
public class TestTranBizImpl extends AbstractBizImpl implements TestTranBizI {
	
	@Override
	public void testTran() throws Exception {
		//Transaction trans = aDao.getHt().getSessionFactory().getCurrentSession().getTransaction();
		System.out.println("000000000000000");
		TestEntity te = new TestEntity();
		te.setName("test5");
		te.setPassword("test5");
		aDao.insertObject(te);
		
		throw new MyStrException("测试");
	}

	@Override
	public void testTran2() throws Exception {
		throw new MyStrException("测试");
	}

}
