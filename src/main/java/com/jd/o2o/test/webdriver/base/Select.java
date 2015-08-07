package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.helper.FindWebElement;
import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;

/**
 * 下拉列表
 * 
 * @author tangdongyang
 */
public class Select implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - select元素的xpath
	 * strs[2] - 要选中的值
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return select(context, strs[1], strs[2]);
	}

	/**
	 * 选择下拉框显示的值
	 * 
	 * @param context		程序的上下文引用
	 * @param xpath			下拉框的xpath
	 * @param value			下拉框要选中的值
	 * @return context		程序的上下文引用
	 */
	public Map<String, Object> select(Map<String, Object> context, String xpath, String value) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		try {
			WebElement element = FindWebElement.getWebElement(context, driver, xpath);
			org.openqa.selenium.support.ui.Select selectElement = new org.openqa.selenium.support.ui.Select(element);
			selectElement.selectByVisibleText(value);
			
			logger.info("选择了下拉框【" + xpath + "】中的值【" + value + "】");
		} catch (Exception e) {
			Assert.fail("select的时候，出现异常：【" + xpath + "】-【" + value + "】", e);
		}
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(Select.class);
}
