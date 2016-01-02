package myFrameU.util.commonUtil.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyFileUtil {
	public static List<File> list = new ArrayList<File>();

	/**
	 * 
	 * @param path
	 *            .获取这个路径下的
	 * @param level
	 * @return
	 */
	public static List<File> getAllJavaFile(File path, int level) {
		File files[] = path.listFiles(); // 获得目录下所有文件
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) { // 用递归列出子目录
				getAllJavaFile(files[i], (level + 1));
			} else {
				if (files[i].getName().matches(".*\\.hbm.xml$")) {
					list.add(files[i]);
				} else {
				}
			}
		}
		return list;
	}

	public static List<File> getAllFiles(File path, int level, String matchesStr) {
		File files[] = path.listFiles(); // 获得目录下所有文件
		if (null != files) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // 用递归列出子目录
					getAllFiles(files[i], (level + 1), matchesStr);
				} else {
					if (files[i].getName().matches(matchesStr)) {
						list.add(files[i]);
					} else {
					}
				}
			}
		}

		return list;
	}

	public static List<File> getAllFiles_(File path, int level,
			String matchesStr) {
		File files[] = path.listFiles(); // 获得目录下所有文件
		if (null != files) {
			System.out.println(files.length);
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) { // 用递归列出子目录
					getAllFiles(files[i], (level + 1), matchesStr);
				} else {
					if (files[i].getName().endsWith(matchesStr)) {
						list.add(files[i]);
					} else {
					}
				}
			}
		}

		return list;
	}

	/**
	 * 写文件
	 * 
	 * @param filePathAndName
	 * @param fileContent
	 */
	public static void writeFile(String filePathAndName, String fileContent) {
		try {
			File f = new File(filePathAndName);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(fileContent);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getFileByte(File f) {
		FileInputStream fis = null;
		int size = 0;
		try {
			fis = new FileInputStream(f);
			size = fis.available();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return size;
	}

	/**

	  */
	public static void refreshFileList(String strPath) throws Exception {
		List<File> filelist = new ArrayList<File>();
		File dir = new File(strPath);
		File[] files = dir.listFiles();

		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				refreshFileList(files[i].getAbsolutePath());
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();
				if (strFileName.endsWith("jsp") || strFileName.endsWith("js")
						|| strFileName.endsWith("css")) {
					String s = readFile(strFileName);
					if (s.contains("localhost")) {
						System.out.println("---" + strFileName);
					}
				}
				// filelist.add(files[i].getAbsolutePath());
			}
		}
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	public static String readFile(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			sb.append(line).append("\r\n");
		}
		br.close();
		return sb.toString();
	}

	public static String readFile(String filePath, String bm) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), bm));
		StringBuffer sb = new StringBuffer("");
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

	public static void downFile(String webUrl, String localURL, String newName) {
		try {
			URL url = new URL(webUrl);
			if (null == url) {
				return;
			}

			// 创建流
			BufferedInputStream in = new BufferedInputStream(
					new URL(webUrl).openStream());

			// 生成图片名
			int index = webUrl.lastIndexOf("/");
			String sName = webUrl.substring(index + 1, webUrl.length());
			// System.out.println(sName);
			String[] sExts = sName.split("\\.");
			String sExt = null;
			if (null != sExts && sExts.length == 2) {
				sExt = sName.split("\\.")[1];
			}

			File img = null;
			if (null != newName) {
				if (null != sExt) {
					if (newName.endsWith(".jpg") || newName.endsWith(".gif")
							|| newName.endsWith(".png")
							|| newName.endsWith(".jpeg")
							|| newName.endsWith(".JPG")
							|| newName.endsWith(".JPEG")
							|| newName.endsWith(".GIF")) {
						img = new File(localURL + newName);
					} else {
						img = new File(localURL + newName + "." + sExt);
					}

				} else {
					if (newName.endsWith(".jpg") || newName.endsWith(".gif")
							|| newName.endsWith(".png")
							|| newName.endsWith(".jpeg")
							|| newName.endsWith(".JPG")
							|| newName.endsWith(".JPEG")
							|| newName.endsWith(".GIF")) {
						img = new File(localURL + newName);
					} else {
						img = new File(localURL + newName + ".jpg");
					}
				}
			} else {
				img = new File(localURL + sName);
			}

			// 生成图片
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(img));
			byte[] buf = new byte[2048];
			int length = in.read(buf);
			while (length != -1) {
				out.write(buf, 0, length);
				length = in.read(buf);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为文件重命名
	 * @param file
	 * @return
	 */
    public File rename(File file) {  
        int index = file.getName().lastIndexOf("."); //
        String body = file.getName().substring(0, index); //
        String postfix = ""; //
        String timer = ""; //  
        if (index != -1) {  
            timer = new Date().getTime() + ""; 
            postfix = file.getName().substring(index); 
        } else {  
            timer = new Date().getTime() + "";  
            postfix = ""; 
        }  
        String newName = timer + postfix; 
        return new File(file.getParent(), newName);
    }  
    
    
    
    
    
    
    
    
    public static String getSize(File f){
    	String size = null;
    	if(null!=f){
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
    	}
		return size;
    }
    
    
    
    
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
       boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
       boolean flag = false;
       File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    
    
    
    
}
