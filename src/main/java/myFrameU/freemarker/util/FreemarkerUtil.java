package myFrameU.freemarker.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import myFrameU.freemarker.init.InitMavenImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	public Template getTemplate(String ftl){
		Template temp =null;
		Configuration cfg = new Configuration();
		 //加载并设置freemarker.properties  
		try {
			 Properties p = new Properties(); 
		     p.load(this.getClass().getResourceAsStream("freemarker.properties"));
		     cfg.setSettings(p);  
		     
		     cfg.setClassForTemplateLoading(this.getClass(), InitMavenImpl.ic.getFtlPackage());
		     cfg.setClassicCompatible(true);
		     temp = cfg.getTemplate(ftl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
		
	}
	
	
	
	public void print(String name,Map<String,Object> root){
		//将模板输出到流中， 这里是输出到控制台上
		Template tmp = this.getTemplate(name);
		try {
			tmp.process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void print2File(String name,Map<String,Object> root,String file) throws Exception{
		System.out.println(file);
		//将模板输出到流中， 这里是输出到文件中去
		Template tmp = this.getTemplate(name);
		//FileWriter fw =null;
		Writer out  = null;
		File f = new File(InitMavenImpl.ic.getFtlToPagePath()+file);
		//File f = new File("E:/www.qingcijiao.com/ROOT/"+file);
		out  =   new  BufferedWriter( new  OutputStreamWriter( new FileOutputStream(f),"utf-8") );
		try {
			//fw = new FileWriter(f);
			tmp.process(root,out);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/*public static String getAbsoulutPath(){
		return System.getProperty("peixun.root");
	}*/
	
	
	
	
	
	
	
	
}
