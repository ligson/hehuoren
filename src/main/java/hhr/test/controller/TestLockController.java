package hhr.test.controller;

import hhr.test.biz.TestLockBizI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.spring.mvc.FatherController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestLockController extends FatherController {
	private static final String lock="lock";
	
	@Autowired
	@Qualifier("tlBiz")
	private TestLockBizI tlBiz;

	public TestLockBizI getTlBiz() {
		return tlBiz;
	}

	public void setTlBiz(TestLockBizI tlBiz) {
		this.tlBiz = tlBiz;
	}
	
	@RequestMapping(value={"/testLock"})
	public void test(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Thread t = Thread.currentThread();
		synchronized(lock){
			System.out.println(t.getId()+":"+t.getName()+"拿到了lock锁，要执行里面的内容......");
					System.out.println("这里是我拿到锁后执行的代码，然后我要睡45s");
			Thread.sleep(1000*45);
			System.out.println(t.getId()+":"+t.getName()+"睡醒了，准备马上放弃锁了..........");
		}
	}
}
