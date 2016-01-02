package myFrameU.exportData.excel.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;
import myFrameU.exportData.excel.init.ExcelDataEntity;

import org.apache.commons.collections.map.ListOrderedMap;

public class ExcelUtil {
	//request.getParameters 格式必须是[shop][admin][global]
	public static final String excel_classes="excel_classes";
	
	public static WritableWorkbook insertSheet(ExcelDataEntity ede,WritableWorkbook wwb,List<Object> oList,HttpServletResponse res) throws Exception{
		Class c = Class.forName(ede.getClassName());
		List<ListOrderedMap> objData=new ArrayList<ListOrderedMap>();
		
		//第一）处理fields和fieldNames
		//id,name,myValue,bz
		String[] fieldArray=ede.getFields().split(",");
		//List<String> fieldList=Arrays.asList(fieldArray);
		String[] fieldNameArray=ede.getFieldNames().split(",");
		List<String> fieldNameList=Arrays.asList(fieldNameArray);
		
		
		//第二）获取sheetName
		String sheetName=ede.getClassNameChinese();
		
		
		//第三）处理oList，获取数据objData list。
		int size = oList.size();
		Object o = null;
		for(int i=0;i<size;i++){
			o=oList.get(i);
			ListOrderedMap map = new ListOrderedMap();
			int len=fieldArray.length;
			String field=null;
			for(int j=0;j<len;j++){
				field=fieldArray[j];
				String firstZM=field.trim().substring(0,1).toUpperCase();//将首字母变大写
				String fieldBig=firstZM+field.trim().subSequence(1, field.length());
				Method m = c.getDeclaredMethod("get"+fieldBig); 
				map.put(field, m.invoke(o));
			}
			objData.add(map);
		}
		
		//第四）插入sheet
		wwb = JxlExcelUtils.exportToExcel(res, objData, sheetName, fieldNameList, wwb);
		
		return wwb;
	}
}
