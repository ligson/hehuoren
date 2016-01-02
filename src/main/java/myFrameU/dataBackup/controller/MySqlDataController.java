package myFrameU.dataBackup.controller;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.dataBackup.entity.MySqlData;
import myFrameU.dataBackup.init.InitConfig;
import myFrameU.dataBackup.util.JavaMysql;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.date.DateUtils;
import myFrameU.util.commonUtil.file.MyFileUtil;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import myFrameU.util.httpUtil.path.PathUtil;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mysqlDataController")
@RequestMapping(value = "/admin/dataBackup")
public class MySqlDataController extends FatherController {
	
	
	
	//按日期排序
	private static File[] orderByDate(String fliePath) {
	   File file = new File(fliePath);
	   File[] fs = file.listFiles();
	   Arrays.sort(fs,new Comparator< File>(){
	     public int compare(File f1, File f2) {
		long diff = f1.lastModified() - f2.lastModified();
		if (diff < 0)
		  return 1;
		else if (diff == 0)
		  return 0;
		else
		  return -1;
	     }
	     public boolean equals(Object obj) {
		return true;
	     }
			
	     });
	     for (int i = fs.length-1; i >-1; i--) {
	    	 System.out.println(fs[i].getName());
	    	 System.out.println(new Date(fs[i].lastModified()));
	      }
		return fs;
	  }
	
	@RequestMapping(value="/findAlls")
	public String findAlls(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm nform = this.getSDynaActionForm(req);
		List<MySqlData> mysqlDataList = new ArrayList<MySqlData>();
		InitConfig initConfig = myFrameU.dataBackup.init.InitMavenImpl.ic;
		String sqlPath = initConfig.getSqlPath();
		
		
		//File path = new File(sqlPath);
		//File files[] = path.listFiles();
		File files[]=orderByDate(sqlPath);
		
		
		int len=files.length;
		File sqlFile = null;
		MySqlData mysqlData = null;
		for(int i=0;i<len;i++){
			sqlFile = files[i];
			if(sqlFile.isFile()){
				if(sqlFile.getAbsoluteFile().toString().endsWith("sql")){
					mysqlData=new MySqlData();
					mysqlData.setFilePath(sqlFile.getName());
					Long time =sqlFile.lastModified();
			        Calendar cd = Calendar.getInstance();
			        cd.setTimeInMillis(time);
			        mysqlData.setRelDate(DateUtils.format(cd.getTime(), DateUtils.formatStr_yyyyMMddHHmmss));
					mysqlData.setSize(MyFileUtil.getSize(sqlFile));
					mysqlDataList.add(mysqlData);
				}
			}
		}
		
		
		
		
		
		int size  = mysqlDataList.size();
		MySqlData d = null;
		for(int i=0;i<size;i++){
			d=mysqlDataList.get(i);
			System.out.println(d.getFilePath()+"==="+d.getRelDate()+"==="+d.getSize());
		}
		
		
		req.setAttribute("mySqlDataLit", mysqlDataList);
		
		
		
		
		
		
		/*String queryArgs = nform.getString("queryArgs");
		String orderBy = nform.getString("orderBy");
		aBiz.findEntitysByArgs(MySqlData.class, EntityTableUtil.tableName(MySqlData.class.getName()), queryArgs, orderBy, null, true, 0, "mySqlDataLit", req);
		*/
		
		
		return this.getForward("systools/mySqlDataList", req);
	}
	
	
	@RequestMapping(value="/backup")
	public void backup(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm nform = this.getSDynaActionForm(req);
		String sqlName=new Date().getTime()+".sql";
		sqlName=JavaMysql.backup(sqlName);
		Thread.sleep(10000);
		
		File f = new File(sqlName);
		String size = "0K";
		if(f.exists()){
			long lsize = f.length();
			lsize=lsize/1024;
			if(lsize>1024){
				float lsize_=(float)lsize/1024;
				size=lsize_+"M";
			}else{
				size=lsize+"KB";
			}
		}
		//aBiz.addObject(d);
	}
	
	
	@RequestMapping(value="/load")
	public void load(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm nform = this.getSDynaActionForm(req);
		//Integer id=nform.getInteger("id");
		String sqlFileName=nform.getString("sqlFileName");
		if(null!=sqlFileName){
			//先备份
			String basePath=PathUtil.getBasePath(req);
			HttpClientUtil.get(basePath+"/admin/dataBackup/backup", null);
			
			InitConfig initConfig = myFrameU.dataBackup.init.InitMavenImpl.ic;
			String sqlPath = initConfig.getSqlPath();
			JavaMysql.load(sqlPath+sqlFileName);
			
		}
	}
	
	
	
	@RequestMapping(value="/down")
	public void down(HttpServletRequest req,HttpServletResponse res) throws Exception{
		OutputStream os = res.getOutputStream();  
		SDynaActionForm nform = this.getSDynaActionForm(req);
		//Integer id=nform.getInteger("id");
		String sqlFileName=nform.getString("sqlFileName");
		if(null!=sqlFileName){
			InitConfig initConfig = myFrameU.dataBackup.init.InitMavenImpl.ic;
			String sqlPath = initConfig.getSqlPath();
			File f=new File(sqlPath+sqlFileName);
			if(null!=f){
				try {  
			        res.reset();  
			        res.setHeader("Content-Disposition", "attachment; filename="+sqlFileName);  
			        res.setContentType("application/octet-stream; charset=utf-8");  
			        os.write(FileUtils.readFileToByteArray(f));  
			        os.flush();  
			    } finally {  
			        if (os != null) {  
			            os.close();  
			        }  
			    }   
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
