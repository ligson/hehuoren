package myFrameU.upload.image;
import java.io.File;  
import java.util.Date;  
  
import com.oreilly.servlet.multipart.FileRenamePolicy;  
  /**
   * 
   * 为文件重命名
   *
   */

public class RandomFileNamePolicy implements FileRenamePolicy {  
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
}  