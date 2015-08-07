package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;

/**
 * 关闭浏览器
 * 
 * @author tangdongyang
 * 
 */
public class Close implements BaseAction {

	/**
	 * strs[0] - 指令
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return close(context);
	}

	/**
	 * 关闭浏览器
	 * 
	 * @param 	context		程序的上下文引用
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> close(Map<String, Object> context) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);

		if (null != driver) {
			driver.quit();
//			driver.close();
			driver = null;
			
			context.put(Constants.DRIVER, driver);
			logger.info("关闭浏览器成功");
		}

		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}

	private final static Logger logger = LoggerFactory.getLogger(Close.class);
}
