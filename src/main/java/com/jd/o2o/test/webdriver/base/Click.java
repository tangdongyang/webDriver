package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.helper.FindWebElement;
import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;

/**
 * 在指定地方点击
 * 
 * @author tangdongyang
 */
public class Click implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 点击的地方
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return click(context, strs[1]);
	}

	/**
	 * 点击指定的地方
	 * 
	 * @param context			程序上下文引用
	 * @param xpath				要点击的xpath或者context中的变量
	 * @return	context			返回程序的上下文引用
	 */
	public Map<String, Object> click(Map<String, Object> context, String xpath) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		WebElement element = FindWebElement.getWebElement(context, driver, xpath);
		element.click();
		logger.info("click," + xpath + "【success】");
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(Click.class);
}
