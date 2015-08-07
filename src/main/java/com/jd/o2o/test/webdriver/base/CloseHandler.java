package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.util.Constants;

/**
 * 关闭指定的窗口
 * 
 * @author tangdongyang
 */
public class CloseHandler implements BaseAction {

	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		WebDriver window = (WebDriver) context.get(strs[1]);			//要关闭的窗口
		context.remove(strs[1]);	//将这个保存的变量移除掉
		
		logger.info("窗口【" + window.getTitle() + "】关闭");
		window.close();
		
		try {
			Thread.sleep(Constants.SLEEP_TIMES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return context;
	}

	private final static Logger logger = LoggerFactory.getLogger(CloseHandler.class);
}
