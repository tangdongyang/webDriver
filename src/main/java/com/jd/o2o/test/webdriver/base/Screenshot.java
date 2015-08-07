package com.jd.o2o.test.webdriver.base;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;

/**
 * 屏幕截图
 * 
 * @author tangdongyang
 */
public class Screenshot implements BaseAction {

	@Override
	/**
	 * strs[0]	-	指令
	 * strs[1]	-	图片存放的位置
	 */
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) throws IOException {
		
		return screenshot(context, strs[1]);
	}

	/**
	 * 屏幕截图
	 * 
	 * @param driver			driver
	 * @param saveString		要保存的文件路径
	 * @return context			程序的上下文引用
	 * @throws IOException 
	 */
	public WebDriver screenshot(WebDriver driver, String saveString) throws IOException {
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(saveString));
		
		logger.info("截图成功，图片存放地址【" + saveString + "】");
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		return driver;
	}
	
	/**
	 * 屏幕截图
	 * 
	 * @param context			程序的上下文引用
	 * @param name		要保存的文件名称
	 * @return context			程序的上下文引用
	 * @throws IOException 
	 */
	public Map<String, Object> screenshot(Map<String, Object> context, String name) throws IOException {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		String saveString = Utils.getPath(Constants.PARAM_DIR + File.separator + 
				Constants.TESTFILE_DIR + File.separator + "des\\" + name);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(saveString));
		
		logger.info("截图成功，图片存放地址【" + saveString + "】");
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(Screenshot.class);
}
