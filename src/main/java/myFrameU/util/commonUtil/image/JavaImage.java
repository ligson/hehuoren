package myFrameU.util.commonUtil.image;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class JavaImage {

	public static void main(String[] args) throws MalformedURLException,
			IOException, URISyntaxException, AWTException {
		// 此方法仅适用于JdK1.6及以上版本
		Desktop
				.getDesktop()
				.browse(
						new URL(
								"http://static.youku.com/v1.0.0349/v/swf/loader.swf?VideoIDS=XNTg3OTgwMTUy&embedid=MTc1LjE2OS4yMDEuMjAzAjE0Njk5NTAzOAIC&wd=&vext=pid%3D%26emb%3DMTc1LjE2OS4yMDEuMjAzAjE0Njk5NTAzOAIC%26bc%3D%26type%3D0")
								.toURI());
		Robot robot = new Robot();
		robot.delay(10000);
		Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();
		// 最大化浏览器
		robot.keyRelease(KeyEvent.VK_F11);
		robot.delay(2000);
		Image image = robot.createScreenCapture(new Rectangle(0, 0, width,
				height));
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		// 保存图片
		ImageIO.write(bi, "jpg", new File("E:/google.jpg"));
	}

	public static void narrowImage1(File img, int minW, int minH) {
		try {
			Image image = ImageIO.read(img);
			if (null != image) {
				int srcH = image.getHeight(null);
				int srcW = image.getWidth(null);
				if (srcH <= minH && srcW <= minW) {
					return;
				}
				int tmpH = minW;
				int tmpW = minH;
				while (srcH > minH || srcW > minW) {
					if (srcW > minW) {
						tmpH = srcH * minW / srcW;
						srcH = tmpH;
						srcW = minW;
					}
					if (srcH > minH) {
						tmpW = srcW * minH / srcH;
						srcW = tmpW;
						srcH = minH;
					}
				}

				BufferedImage bufferedImage = new BufferedImage(srcW, srcH,
						BufferedImage.TYPE_3BYTE_BGR);
				bufferedImage.getGraphics()
						.drawImage(
								image.getScaledInstance(srcW, srcH,
										Image.SCALE_SMOOTH), 0, 0, srcW, srcH,
								null);

				FileOutputStream fos = new FileOutputStream(img);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
				encoder.encode(bufferedImage);
				fos.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Map<String, Integer> getImageWH(File img) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (img.exists()) {
			Image image;
			try {
				image = ImageIO.read(img);
				int srcH = image.getHeight(null);
				int srcW = image.getWidth(null);
				map.put("width", srcW);
				map.put("height", srcH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public static String narrowImage2(File img, int width, int height,
			String newSmallImageDir) {
		String newImageFileUrl = null;
		String newImageName = null;
		try {
			
			Image image = ImageIO.read(img);
			int srcH = image.getHeight(null);
			int srcW = image.getWidth(null);
			if (srcH <= height && srcW <= width) {
				// return "";
			}
			int tmpH = width;
			int tmpW = height;
			
			while (srcH > height || srcW > width) {
				if (srcW > width) {
					tmpH = srcH * width / srcW;
					srcH = tmpH;
					srcW = width;
				}
				if (srcH > height) {
					tmpW = srcW * height / srcH;
					srcW = tmpW;
					srcH = height;
				}
			}

			BufferedImage bufferedImage = new BufferedImage(srcW, srcH,
					BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics().drawImage(
					image.getScaledInstance(srcW, srcH, Image.SCALE_SMOOTH), 0,
					0, srcW, srcH, null);
			newImageName = newImageName(img.getName());
			newImageFileUrl = newSmallImageDir + newImageName;
			FileOutputStream fos = new FileOutputStream(newImageFileUrl);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufferedImage);
			fos.close();
			return newImageName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImageName;

	}

	public static String narrowImage3(File img, int width, int height,
			String newSmallImageDir) {
		String newImageFileUrl = null;
		String newImageName = null;
		try {
			Image image = ImageIO.read(img);
			int srcH = image.getHeight(null);
			int srcW = image.getWidth(null);
			if (srcH <= height && srcW <= width) {
				// return "";
			}
			int tmpH = width;
			int tmpW = height;
			while (srcH > height || srcW > width) {
				if (srcW > width) {
					tmpH = srcH * width / srcW;
					srcH = tmpH;
					srcW = width;
				}
				if (srcH > height) {
					tmpW = srcW * height / srcH;
					srcW = tmpW;
					srcH = height;
				}
			}

			BufferedImage bufferedImage = new BufferedImage(srcW, srcH,
					BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics().drawImage(
					image.getScaledInstance(srcW, srcH, Image.SCALE_SMOOTH), 0,
					0, srcW, srcH, null);
			newImageName = newImageName1(img.getName());
			newImageFileUrl = newSmallImageDir + newImageName;
			FileOutputStream fos = new FileOutputStream(newImageFileUrl);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufferedImage);
			fos.close();
			return newImageName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImageName;

	}

	public static String newImageName1(String bigImageName) {
		String newImageName = null;
		if (bigImageName != null && !"".equals(bigImageName)) {
			if (bigImageName.contains(".")) {
				String[] nameExt = bigImageName.split("\\.");
				String name = nameExt[0];
				String ext = nameExt[1];
				String newName = name + "_big";
				newImageName = newName + "." + ext;
			}
		}
		return newImageName;
	}

	public static String newImageName(String bigImageName) {
		String newImageName = null;
		if (bigImageName != null && !"".equals(bigImageName)) {
			if (bigImageName.contains(".")) {
				String[] nameExt = bigImageName.split("\\.");
				String name = nameExt[0];
				String ext = nameExt[1];
				String newName = name + "_min";
				newImageName = newName + "." + ext;
			}
		}
		return newImageName;
	}

	
	public static void waterImg(String oldBigImgUrl, String shuiyin) throws Exception {
		OutputStream os = null;
		InputStream is = new FileInputStream(oldBigImgUrl);
		JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
		BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
		int width = buffImg.getWidth();
		int height = buffImg.getHeight();
		Graphics g = buffImg.getGraphics();

		ImageIcon imgIcon = null;
		Image img = null;

		if (width <= 300 || height <= 200) {

		} else if ((width <= 500 && width > 300)
				|| (height <= 400 && height > 200)) {
			// 加上shuiyin1.png
			System.out.println("1111111111111");
			imgIcon = new ImageIcon(shuiyin);
			img = imgIcon.getImage();
			g.drawImage(img, width - 110, height - 47, null);
		} else {
			System.out.println("2222222222222222");
			// 用大水印shuiyin.png
			imgIcon = new ImageIcon(shuiyin);
			img = imgIcon.getImage();
			g.drawImage(img, width - 210, height - 84, null);
		}
		g.dispose();
		os = new FileOutputStream(oldBigImgUrl);
		JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
		en.encode(buffImg);
		is.close();
		os.close();
	}

	/**
	 * 截取一个图像的中央区域
	 * 
	 * @param image
	 *            图像File
	 * @param w
	 *            需要截图的宽度
	 * @param h
	 *            需要截图的高度
	 * @return 返回一个
	 * @throws IOException
	 */
	public static String cutImage(File image, int w, int h,
			String newSmallImageDir) throws IOException {
		String newImageName = newImageName(image.getName());
		String toPathAndName = newSmallImageDir + newImageName;
		
		// 判断参数是否合法
		if (null == image || 0 == w || 0 == h) {
			new Exception("哎呀，截图出错！！！");
		}
		InputStream inputStream = new FileInputStream(image);
		// 用ImageIO读取字节流
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		BufferedImage distin = null;
		// 返回源图片的宽度。
		int srcW = bufferedImage.getWidth();
		// 返回源图片的高度。
		int srcH = bufferedImage.getHeight();

		if (srcW < w || srcH < h) {
			// 如果原先的图片的宽度小于要截取的宽度,或者原先的高度小于指定的高度。总之只要截不出来的，就不截。
			System.out.println("原来图片的宽度为=" + srcW + ";原来图片的高度为：" + srcH);
			System.out.println("图片太小，不能截取");
			return null;
		} else {
			if (srcH * w >= srcW * h) {
				// 这样的话，先按照宽度为235来压缩再截取
				narrowImage_2(image, w, 0, toPathAndName);
				System.out.println("11111111111111111111111");
			} else {
				narrowImage_2(image, 0, h, toPathAndName);
				System.out.println("222222222222222222");
			}
			String apath = image.getAbsolutePath();
			File newImg = new File(toPathAndName);
			// 重新获取新的图片的input
			inputStream = new FileInputStream(newImg);
			// 用ImageIO读取字节流
			bufferedImage = ImageIO.read(inputStream);
			// 返回源图片的宽度。
			if (null != bufferedImage) {
				srcW = bufferedImage.getWidth();
				// 返回源图片的高度。
				srcH = bufferedImage.getHeight();

				int x = 0, y = 0;
				// 使截图区域居中
				x = srcW / 2 - w / 2;
				y = srcH / 2 - h / 2;
				srcW = srcW / 2 + w / 2;
				srcH = srcH / 2 + h / 2;
				// 生成图片
				distin = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
				Graphics g = distin.getGraphics();
				g.drawImage(bufferedImage, 0, 0, w, h, x, y, srcW, srcH, null);
				ImageIO.write(distin, "jpg", new File(toPathAndName));

				//
				return toPathAndName;
			}
		}
		return null;
	}

	public static boolean cutImage_xxyy(File image, int w, int h, int x1,
			int x2, int y1, int y2, String toPathAndName) throws IOException {
		// 判断参数是否合法
		if (null == image || 0 == w || 0 == h) {
			new Exception("哎呀，截图出错！！！");
		}

		if (x2 != 0 && y2 != 0) {

			InputStream inputStream = new FileInputStream(image);
			// 用ImageIO读取字节流
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			BufferedImage distin = null;
			// 返回源图片的宽度。
			int srcW = bufferedImage.getWidth();
			// 返回源图片的高度。
			int srcH = bufferedImage.getHeight();

			if (srcW < w || srcH < h) {
				// 如果原先的图片的宽度小于要截取的宽度,或者原先的高度小于指定的高度。总之只要截不出来的，就不截。
				System.out.println("原来图片的宽度为=" + srcW + ";原来图片的高度为：" + srcH);
				System.out.println("图片太小，不能截取");
				return false;
			} else {
				if ((x1 + w) <=srcW && x2 <= srcW && (y1 + h) <= srcH
						&& y2 <= srcH) {
					if (srcH * w >= srcW * h) {
						// 这样的话，先按照宽度为235来压缩再截取
						// narrowImage_2(image, w, 0, toPathAndName);
						// System.out.println("11111111111111111111111");
					} else {
						// narrowImage_2(image, 0, h, toPathAndName);
						// System.out.println("222222222222222222");
					}
					String apath = image.getAbsolutePath();
					copyImage(apath, toPathAndName);
					File newImg = new File(toPathAndName);
					// 重新获取新的图片的input
					inputStream = new FileInputStream(newImg);
					// 用ImageIO读取字节流
					bufferedImage = ImageIO.read(inputStream);
					// 返回源图片的宽度。
					if (null != bufferedImage) {
						srcW = bufferedImage.getWidth();
						// 返回源图片的高度。
						srcH = bufferedImage.getHeight();

						int x = 0, y = 0;
						// 使截图区域居中
						x = x1;
						y = y1;
						srcW = x2;
						srcH = y2;
						// 生成图片
						distin = new BufferedImage(w, h,
								BufferedImage.TYPE_3BYTE_BGR);
						Graphics g = distin.getGraphics();
						g.drawImage(bufferedImage, 0, 0, w, h, x, y, srcW,
								srcH, null);
						ImageIO.write(distin, "jpg", new File(toPathAndName));

						//
						return true;
					}
				}
			}
		}

		return false;
	}

	public static void copyImage(String source, String target) {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		try {
			fin = new FileInputStream(source);
			fout = new FileOutputStream(target);
			byte[] b = new byte[512];
			int n;
			while ((n = fin.read(b)) != -1) {
				fout.write(b, 0, n);// 这里改改
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
				fout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String narrowImage_2(File img, int width, int height,
			String toPathAndName) {
		String newImageFileUrl = null;
		String newImageName = null;
		try {
			Image image = ImageIO.read(img);
			int srcH = image.getHeight(null);
			int srcW = image.getWidth(null);
			if (srcH <= height && srcW <= width) {
				// return "";
			}
			int tmpH = width;
			int tmpW = height;
			if (width == 0) {
				// 说明按照高度来截取
				while (srcH > height) {
					if (srcH > height) {
						tmpW = srcW * height / srcH;
						srcW = tmpW;
						srcH = height;
					}
				}
			} else {
				// 说明按照宽度来截取
				while (srcW > width) {
					if (srcW > width) {
						tmpH = srcH * width / srcW;
						srcH = tmpH;
						srcW = width;
					}
				}
			}

			BufferedImage bufferedImage = new BufferedImage(srcW, srcH,
					BufferedImage.TYPE_3BYTE_BGR);
			bufferedImage.getGraphics().drawImage(
					image.getScaledInstance(srcW, srcH, Image.SCALE_SMOOTH), 0,
					0, srcW, srcH, null);
			newImageName = newImageName(img.getName());
			newImageFileUrl = toPathAndName;
			FileOutputStream fos = new FileOutputStream(newImageFileUrl);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufferedImage);

			/*
			 * JPEGEncodeParam
			 * jep=JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage); 压缩质量
			 * jep.setQuality(1, true); encoder.encode(bufferedImage, jep);
			 * //encoder.encode(tag); //近JPEG编码
			 */

			fos.close();
			return newImageName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newImageName;

	}

	// wBigH:宽不能大于高几倍。HbigW高不能大于宽几倍
	public static int verify(File image, int minW, int minH, int maxW,
			int maxH, float wBigH, float hBigW) throws Exception {
		InputStream inputStream = new FileInputStream(image);
		// 用ImageIO读取字节流
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		// 返回源图片的宽度。
		int srcW = bufferedImage.getWidth();
		// 返回源图片的高度。
		int srcH = bufferedImage.getHeight();

		if (srcW < minW || srcH < minH) {
			// 如果宽度和高度小于最小指定值的话
			return 1;
		} else if (srcW > maxW && srcH > maxH) {
			// 如果都大于指定宽高
			return 2;
		} else if (((float) srcW / srcH) > wBigH) {
			// 如果宽太宽了
			return 3;
		} else if (((float) srcH / srcW) > hBigW) {
			// 如果高太高了
			return 4;
		} else {
			return 5;
		}
	}

}
