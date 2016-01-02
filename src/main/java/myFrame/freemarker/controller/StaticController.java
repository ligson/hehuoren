package myFrame.freemarker.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.freemarker.util.FreemarkerUtil;
import myFrameU.spring.mvc.FatherController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 前台的页面的静态化方法一律以fg开头
 *
 */
@Controller
@RequestMapping("/admin/static/")
public class StaticController extends FatherController {
	private static FreemarkerUtil fu = new FreemarkerUtil();
	@RequestMapping(value="index")
	public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		return "manage/admin/systools/static";
	}
}
