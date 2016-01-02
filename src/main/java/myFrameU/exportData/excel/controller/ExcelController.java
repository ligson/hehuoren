package myFrameU.exportData.excel.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;
import myFrameU.exportData.excel.init.ExcelDataEntity;
import myFrameU.exportData.excel.init.InitConfig;
import myFrameU.exportData.excel.util.ExcelUtil;
import myFrameU.exportData.excel.util.JxlExcelUtils;
import myFrameU.exportData.excel.vo.Product;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/excel")
public class ExcelController extends FatherController {
	/**
	 * 选择某一个特定的类，然后根据queryArgs进行筛选导出
	 * @param req
	 * @param res
	 * @return
	 */
	public String exportExcelSingle(HttpServletRequest req, HttpServletResponse res){
		return null;
	}
	
	
	/**
	 * 选择某几个类，联合一起全部下载
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/exportExcelAll")
	public String exportExcelAll(HttpServletRequest req, HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String excel_classes = form.getString(ExcelUtil.excel_classes);//[shop][admin][global]
		if(null!=excel_classes && !excel_classes.equals("")){
			// 第一步：获取wwb
			WritableWorkbook wwb = JxlExcelUtils.createWWB(res);
			
			InitConfig initConfig = myFrameU.exportData.excel.init.InitMavenImpl.ic;
			Map<String, ExcelDataEntity> edeMap = initConfig.getEdeMap();
			String simpleClassName=null;
			ExcelDataEntity ede=null;
			for (Map.Entry<String, ExcelDataEntity> entry : edeMap.entrySet()) {
				simpleClassName=entry.getKey();
				ede=entry.getValue();
				if(excel_classes.contains("["+simpleClassName+"]")){
					//需要导出global实体类的数据
					// 第二步：向wwb中插入若干sheet
					Class clazz=Class.forName(ede.getClassName());
					List<Object> oList = (List<Object>) aBiz.findObjectList(clazz, null, "from "+ede.getClassName()+" ", null, false, 0, 0);
					wwb=ExcelUtil.insertSheet(ede,wwb,oList, res);
				}
			}
			// 第三步：向response中写入wwb
			JxlExcelUtils.writeWWB(res, wwb);
		}

		return null;
	}
	
	@RequestMapping(value = "/findNeeds")
	public String findNeeds(HttpServletRequest req, HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		InitConfig initConfig = myFrameU.exportData.excel.init.InitMavenImpl.ic;
		Map<String, ExcelDataEntity> edeMap = initConfig.getEdeMap();
		req.setAttribute("edeMap", edeMap);
		return this.getForward("systools/excel", req);
	}
}
