package myFrameU.upload.image;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.upload.init.InitConfig;
import myFrameU.upload.init.UploadImageEntity;
import myFrameU.util.commonUtil.file.MyFileUtil;
import myFrameU.util.commonUtil.image.JavaImage;

import org.springframework.context.ApplicationContext;

import com.oreilly.servlet.MultipartRequest;

public class UpLoadImage  extends HttpServlet{
	ApplicationContext actx = null;
    private static final long serialVersionUID = -7946856825034537432L;  
    private static InitConfig initConifg= myFrameU.upload.init.InitMavenImpl.ic;
    private static List<UploadImageEntity> list=initConifg.getList();
    private ServletConfig config = null;
    public void init(ServletConfig config) {  
        this.config = config;
    }  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8"); 
    	String saveType = (String)request.getParameter("saveType");
    	String realBaseDir=this.config.getServletContext().getRealPath("");
    	//找到saveType对应的UploadImageEntity
    	//saveType=ueditor-product
    	if(null!=saveType && !saveType.equals("")){
    		UploadImageEntity uie=getUploadImageEntityFromList(list,saveType);
    		upload(realBaseDir,uie,request,response);
    	}
    	
    }
    
    
    private static void upload(String realBaseDir,UploadImageEntity uie,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	
    	if(null!=uie){
			String savePath=uie.getSavePath();
			savePath=realBaseDir+savePath;
			
			
			String state="SUCCESS";
			String msg="";
		    String saveDirectory = savePath;
	        File fileDirectory = new File(saveDirectory);  
	        if(fileDirectory.exists()){  
	        }else{  
	            fileDirectory.mkdirs();  
	        }  
	        
	        
	        int maxPostSize = 8 * 1024 * 1024;  
	        RandomFileNamePolicy rfnp = new RandomFileNamePolicy();  
	          
	        MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", rfnp);  
	       try{
	    	   
	           Enumeration fileNames = multi.getFileNames();  
	           String lastFileName = null;
	           String originalName=null;
	           String[] allowFiles="jpg,jpeg,JPG,JPEG,gif,GIF,png,PNG".split(",");
	           while(fileNames.hasMoreElements()){  
	               String fileName = (String)fileNames.nextElement();
	               //originalName=multi.getOriginalFileName(fileName);
	               if(null != multi.getFile(fileName)){  
	                   lastFileName = multi.getFilesystemName(fileName); 
	                  
	                 //默认为不可上传,先要检测下类型,去除不安全的文件
	                   boolean canUpload=false;
	                   String upex=lastFileName.substring(lastFileName.lastIndexOf(".")+1);
	                   for(String ex:allowFiles){
	                	   if(ex.equals(upex)){
	                		   canUpload=true;
	                		   break;
	                	   }
	                   }
	                   
	                   
	                   if(canUpload){
	                	   //第一）上传源文件
	                	   File img = new File(savePath+"/"+lastFileName);
	                	   //第二）根据最大尺寸来缩小原来的图片，不会生成小图片
	                	   narrowCover(uie,img);
	                	   //第三）根据getMinWay和minWidth minHeight获取小图片
	                	   String minImagePathAndName=createMin(uie,img,realBaseDir);
	                	   //第四）判断是否加水印
	                	   water(uie,img,realBaseDir);
	                	   
	                   }
	               } 
	           }  
	           /*	String title = multi.getParameter("pictitle");
	            title = title.replace("&", "&amp;").replace("'", "&qpos;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;");*/
	           
	           String savePath1=uie.getSavePath();
	           if(savePath1.startsWith("/")){
	        	   savePath1=savePath1.substring(1,savePath1.length());
	           }
	           
	            String ret="{'url':'"+savePath1+lastFileName+"','title':'"+originalName+"','state':'"+state+"'}";
	            System.out.println("输出的ret为："+ret);
	            response.getWriter().print(ret);
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }
			
			
			
			
			
		}
    }
    
    //加水印,覆盖原先的大图片
    public static void water(UploadImageEntity uie,File img,String realBaseDir) throws Exception{
    	boolean water=uie.getWater();
    	if(water){
    		String waterImage=uie.getWaterImage();
    		if(null!=waterImage && !waterImage.equals("")){
    			JavaImage.waterImg(img.getAbsolutePath(), realBaseDir+waterImage);
    		}
    	}else{
    		//不需要加水印
    	}
    }
    
    
  //根据最大尺寸缩小图片,覆盖
    private static void narrowCover(UploadImageEntity uie,File img){
    	JavaImage.narrowImage1(img, uie.getMaxWidth(), uie.getMaxHeight());
    }
    
    //根据getMinWay和minWidth minHeight获取小图片,返回小图片的具体路径
    private static String createMin(UploadImageEntity uie,File img,String realBaseDir) throws Exception{
    	String minImagePath=null;
    	String getminWay=uie.getGetMinWay();
    	String savePathMin=uie.getSavePathMin();
    	if(null==savePathMin || savePathMin.equals("")){
    		return null;
    	}
		savePathMin=realBaseDir+savePathMin;
    	if(null!=getminWay && !getminWay.equals("")){
    		int minHeight=uie.getMinHeight();
    		int minWidth=uie.getMinWidth();
    		if(minHeight>100 && minWidth>100){
    			if(getminWay.equals("cut")){
        			//通过切割来获取小图
    				minImagePath=JavaImage.cutImage(img, minWidth, minHeight, savePathMin);
        		}else if(getminWay.equals("narrow")){
        			minImagePath=JavaImage.narrowImage2(img, minWidth, minHeight, savePathMin);
        		}else{
        			//获取小图片的方式不对
        		}
    		}else{
    			//没有需求切除太小的图片来
    		}
    	}else{
    		//不需要获取小图片
    	}
		return minImagePath;
    }
    
    
    private static UploadImageEntity getUploadImageEntityFromList(List<UploadImageEntity> list,String saveType){
    	if(null!=list && list.size()>0){
    		int size = list.size();
    		UploadImageEntity last = null;
    		UploadImageEntity uie = null;
    		String st=null;
    		for(int i=0;i<size;i++){
    			uie=list.get(i);
    			st=uie.getSaveType();
    			if(st.equals(saveType)){
    				last=uie;
    				break;
    			}
    		}
    		return last;
    	}
		return null;
    }
    
    
}
