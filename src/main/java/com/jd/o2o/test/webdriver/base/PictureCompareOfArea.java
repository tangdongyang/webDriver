package com.jd.o2o.test.webdriver.base;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;


/**
 * 指定图片的对比区域进行比较.如果图片相等则删除截图，不相等则转移到error目录做日志（部分图像对比）
 * 
 * @author tangdongyang
 */
public class PictureCompareOfArea implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 源图片名称 ,源图片路径：param/testfile/src/
	 * strs[2] - 目标图片名称 ,目的图片路径：param/testfile/des/
	 * strs[3] - 期待值（true-期待相等，false-期待不相等）
	 * strs[4] - 对比区域的起始点坐标（例如：320,220(宽,高):表示从图片的320,220坐标点开始截图比较）
	 * strs[5] - 对比区域宽度
	 * strs[6] - 对比区域高度
	 * @throws IOException 
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) throws IOException {
		return compare(context, strs[1], strs[2], strs[3], strs[4], strs[5], strs[6]);
	}

	/**
	 * 比较2张图片指定部分的区域是否一致
	 * @param context
	 * @param srcname 源图片名称
	 * @param desname 目的图片名称
	 * @param flag 期待值（true-期待相等，false-期待不相等）
	 * @param startpoint 对比图像起始点坐标,以英文逗号分隔（例如：333,222）
	 * @param width 待对比的区域宽度
	 * @param height 待对比的区域高度
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> compare(Map<String, Object> context, String srcname, 
				String desname, String flag, String startpoint, String width,
					String height) throws IOException {
    	String srcpath = Utils.getPath(Constants.PARAM_DIR + File.separator + 
				Constants.TESTFILE_DIR + File.separator + "src\\" + srcname);
    	String despath = Utils.getPath(Constants.PARAM_DIR + File.separator + 
				Constants.TESTFILE_DIR + File.separator + "des\\" + desname);
    	//截取指定区域图片，返回新图片地址（源图片）
    	String newSrc = getNewpictureOfArea(srcpath, srcname.split("\\.")[0] + "-srcarea.jpg", startpoint, width, height);
    	//实际图片
    	String newDes = getNewpictureOfArea(despath, desname.split("\\.")[0] + "-desarea.jpg", startpoint, width, height);
    	
		boolean result = compare(getPictureBytes(newSrc), getPictureBytes(newDes)); //对比结果
		//当图片对比不相等时,将目的图片保存到error目录下，做为日志图片;如果相等，则删除图片
		dealWithPicture(despath, newSrc,newDes, result);
		if (Boolean.valueOf(flag)) {
			Assert.assertEquals(true, result);
			logger.info("pictureCompare,期待：相等，实际：相等【success】");
		} else {
			Assert.assertEquals(false, result);
			logger.info("pictureCompare,期待：不相等，实际：不相等【success】");
		}
		
		return context;
	}
	
	/**
	 * 从源图片上截取指定区域的图片进行保存，并返回新图片地址
	 * @param oldPicutrePath 源图片地址
	 * @param newPictureName 新图片名称（默认保存到param/testfile/error/目录下）
	 * @param startpoint 对比图像起始点坐标,以英文逗号分隔（例如：333,222）
	 * @param width 待对比的区域宽度
	 * @param height 待对比的区域高度
	 * @return
	 * @throws IOException 
	 */
	private String getNewpictureOfArea(String oldPicutrePath, String newPictureName, 
			String startpoint, String width, String height) throws IOException{
		String newPicturePath = "";
		
		Iterator<?> readers = ImageIO.getImageReadersByFormatName("png"); 
		ImageReader reader = (ImageReader) readers.next(); 

		// 取得图片读入流 
		InputStream source = null;
		try {
			source = new FileInputStream(oldPicutrePath);
			ImageInputStream iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true); 

			// 图片参数 
			ImageReadParam param = reader.getDefaultReadParam(); 
			String[] point = new String[2];
			
			if (startpoint.contains(",")) {
				point[0] = startpoint.split(",")[0];
				point[1] = startpoint.split(",")[1];
			} else if (startpoint.contains("，")) {
				point[0] = startpoint.split("，")[0];
				point[1] = startpoint.split("，")[1];
			} else {
				logger.error("起始坐标点定义出现异常：" + startpoint);
				return newPicturePath;
			}

			Rectangle rect = new Rectangle(Integer.valueOf(point[0]), Integer.valueOf(point[1])
					, Integer.valueOf(width), Integer.valueOf(height));
			param.setSourceRegion(rect); 
			BufferedImage bi = reader.read(0, param);
			
			newPicturePath = Utils.getPath(Constants.PARAM_DIR + File.separator + 
					Constants.TESTFILE_DIR + File.separator + "error\\" + newPictureName);
			ImageIO.write(bi, "png", new File(newPicturePath));
		} finally {
			source.close();
		}
		
		return newPicturePath;
	}
	
	/**
	 * 当图片对比不相等时,将目的图片保存到error目录下，做为日志图片
	 * @param despath 图片路径
	 * @param newSrc 区域图片地址（源图片）
	 * @param newDes 区域图片地址（目的图片）
	 * @param flag true-删除图片，false-将目的图片转移到error文件夹中
	 */
	private void dealWithPicture(String despath, String newSrc, String newDes, boolean flag) {
		File file = new File(despath);
		File file2 = new File(newSrc);
		File file3 = new File(newDes);
		if (flag) {
			file.delete();
		} else {
			file.renameTo(new File(despath.replace("des", "error")));
		}
		file2.delete();
		file3.delete();
	}

	/**
	 * 比较2张图片是否一致
	 * @param context
	 * @param src 源图片地址
	 * @param des 目的图片地址
	 * @param flag 期待值（true-期待相等，false-期待不相等）
	 * @return
	 * @throws IOException
	 */
	public void compare(String src, String des, String flag) throws IOException {
		boolean result = compare(getPictureBytes(src), getPictureBytes(des)); //对比结果
		if (Boolean.valueOf(flag)) {
			Assert.assertEquals(true, result);
		} else {
			Assert.assertEquals(false, result);
		}
	}
	
	/**
	 * 对byte数组进行比较
	 * @param a byte数组a
	 * @param b byte数组b
	 */
    private boolean compare(byte[] a, byte[] b) {
    	for (int i = 0; i < a.length; i++) {
    		if(a[i] == b[i]) {
    			continue;
    		} else {
    			logger.warn("源图像与目的图像不一致！");
    			return false;
    		}
    	}
    	return true;
    }
	
    /**
     * 将图像转化为buye数组
     * @param path 图像路径
     * @return
     * @throws IOException 
     */
    private byte[] getPictureBytes(String path) throws IOException {
		File file = new File(path);
		byte[] tuPianBytes = new byte[(int) file.length()];
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			in.read(tuPianBytes);
			in.close();
		} finally {
			in.close();
		}
		return tuPianBytes;
	}
	
    private final static Logger logger = LoggerFactory.getLogger(PictureCompareOfArea.class);
}
