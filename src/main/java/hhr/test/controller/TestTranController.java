package hhr.test.controller;

import hhr.test.biz.TestTranBizI;
import hhr.test.biz.TestTranBizImpl1;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.spring.mvc.FatherController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestTranController extends FatherController {
	@Autowired
	@Qualifier("tt1Biz")
	private TestTranBizI ttBiz;
	
	public TestTranBizI getTtBiz() {
		return ttBiz;
	}

	
	public void setTtBiz(TestTranBizI ttBiz) {
		this.ttBiz = ttBiz;
	}

	@RequestMapping(value={"/test"})
	public void test(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ttBiz.testTran();
	}
	@RequestMapping(value={"/test2"})
	public void test2(HttpServletRequest req,HttpServletResponse res) throws Exception{
		ttBiz.testTran2();
	}
	
}
