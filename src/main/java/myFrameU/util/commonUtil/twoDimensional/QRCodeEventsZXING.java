package myFrameU.util.commonUtil.twoDimensional;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QRCodeEventsZXING {

	public static void main(String[] args) {
		try{
			String text = "http://localhost:8080/024lm/wrap/user/beHeFensi?queryUserType=id&id=3";
			int width = 400;
			int height = 400;
			String format = "png";
			Hashtable hints = new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
					BarcodeFormat.QR_CODE, width, height, hints);
			File outputFile = new File("E:/new.png");
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void createImg(String FilePath, String content,int widthHeight) {
		try{
			int width=widthHeight;
			int height=widthHeight;
			String format = "png";
			Hashtable hints = new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
					BarcodeFormat.QR_CODE, width, height, hints);
			File outputFile = new File(FilePath);
			MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
