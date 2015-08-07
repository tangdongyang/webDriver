package com.jd.o2o.test.webdriver.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;


/**
 * 指定图片进行比较.如果图片相等则删除截图，不相等则转移到error目录做日志(整张图片对比)
 * 
 * @author tangdongyang
 */
public class PictureCompare implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 源图片名称 ,源图片路径：param/testfile/src/
	 * strs[2] - 目标图片名称 ,目的图片路径：param/testfile/des/
	 * strs[3] - 期待值（true-期待相等，false-期待不相等）
	 * @throws IOException 
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) throws IOException {
		return compare(context, strs[1], strs[2], strs[3]);
	}

	/**
	 * 比较2张图片是否一致
	 * @param context
	 * @param srcname 源图片名称
	 * @param desname 目的图片名称
	 * @param flag 期待值（true-期待相等，false-期待不相等）
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> compare(Map<String, Object> context, String srcname, String desname, String flag) throws IOException {
    	String srcpath = Utils.getPath(Constants.PARAM_DIR + File.separator + 
				Constants.TESTFILE_DIR + File.separator + "src\\" + srcname);
    	String despath = Utils.getPath(Constants.PARAM_DIR + File.separator + 
				Constants.TESTFILE_DIR + File.separator + "des\\" + desname);
		boolean result = compare(getPictureBytes(srcpath), getPictureBytes(despath)); //对比结果
		//当图片对比不相等时,将目的图片保存到error目录下，做为日志图片;如果相等，则删除图片
		dealWithPicture(despath, result);
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
	 * 当图片对比不相等时,将目的图片保存到error目录下，做为日志图片
	 * @param despath 图片路径
	 * @param flag true-删除图片，false-将图片转移到error文件夹中
	 */
	private void dealWithPicture(String despath, boolean flag) {
		File file = new File(despath);
		if (flag) {
			file.delete();
		} else {
			file.renameTo(new File(despath.replace("des", "error")));
		}
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
	
    private final static Logger logger = LoggerFactory.getLogger(PictureCompare.class);
}
