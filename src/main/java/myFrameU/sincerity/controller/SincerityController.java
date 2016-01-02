package myFrameU.sincerity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.sincerity.entity.SincerityLevel;
import myFrameU.sincerity.entity.SincerityType;
import myFrameU.spring.mvc.FatherController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class SincerityController extends FatherController {
	@RequestMapping(value={"admin/sincerity/findLevelAndTypes"})
	public String findLevelAndTypes(HttpServletRequest req,HttpServletResponse res) throws Exception{
		List<SincerityLevel> sincerityLevelList = CacheKey.CKSincerityLevel.ALLMAP.getList(uICacheManager);
		req.setAttribute("sincerityLevelList", sincerityLevelList);
		
		List<SincerityType> sincerityTypeList = CacheKey.CKSincerityType.ALLMAP.getList(uICacheManager);
		req.setAttribute("sincerityTypeList", sincerityTypeList);
		
		
		return "manage/admin/setUp/sincerity";
	}
	@RequestMapping(value={"admin/sincerity/toOper"})
	public String toOper(HttpServletRequest req,HttpServletResponse res) throws Exception{
		List<SincerityLevel> sincerityLevelList = CacheKey.CKSincerityLevel.ALLMAP.getList(uICacheManager);
		req.setAttribute("sincerityLevelList", sincerityLevelList);
		List<SincerityType> sincerityTypeList = CacheKey.CKSincerityType.ALLMAP.getList(uICacheManager);
		req.setAttribute("sincerityTypeList", sincerityTypeList);
		return "manage/admin/setUp/sincerityOper";
	}
	@RequestMapping(value={"admin/sincerity/searchShopSin"})
	public String searchShopSin(HttpServletRequest req,HttpServletResponse res) throws Exception{
		List<SincerityLevel> sincerityLevelList = CacheKey.CKSincerityLevel.ALLMAP.getList(uICacheManager);
		req.setAttribute("sincerityLevelList", sincerityLevelList);
		List<SincerityType> sincerityTypeList = CacheKey.CKSincerityType.ALLMAP.getList(uICacheManager);
		req.setAttribute("sincerityTypeList", sincerityTypeList);
		
		
		return "manage/admin/setUp/sincerityList";
	}
}
