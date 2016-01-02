package test;

import java.io.File;
import java.util.List;

import myFrameU.util.commonUtil.file.MyFileUtil;

public class Test2 {
	public static final String old="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">";
	public static final String s1="<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">";
	public static final String s2="<meta name=\"renderer\" content=\"webkit\">";
	public static final String newStr=old+"\n"+s1+"\n"+s2;
	
	public static void add(){
		try{
			List<File> fileList = MyFileUtil.getAllFiles(new File("E:/艺术拍拍/application/yishupaipai/WebContent/pcjsp/"),2, ".*");
			int size = fileList.size();
			File f = null;
			String content = null;
			String absolutePath=null;
			for(int i=0;i<size;i++){
				f=fileList.get(i);
				absolutePath=f.getAbsolutePath();
				if(absolutePath.endsWith(".jsp")){
					content=MyFileUtil.readFile(f.getAbsolutePath());
					if(!content.contains(s1)){
						content=content.replace(old, newStr);
						MyFileUtil.writeFile(absolutePath, content);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void delete(){
		try{
			List<File> fileList = MyFileUtil.getAllFiles(new File("E:/艺术拍拍/application/yishupaipai/src/"),2, ".*");
			int size = fileList.size();
			File f = null;
			String content = null;
			String absolutePath=null;
			int count=0;
			for(int i=0;i<size;i++){
				f=fileList.get(i);
				absolutePath=f.getAbsolutePath();
				System.out.println(absolutePath);
				
				if(absolutePath.endsWith(".java")){
					count++;
					/*content=MyFileUtil.readFile(f.getAbsolutePath());
					if(content.contains(s1)){
						content=content.replace(s1, "");
						content=content.replace(s2, "");
						MyFileUtil.writeFile(absolutePath, content);
					}*/
				}
			}
			System.out.println(count);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		delete();
	}
}
