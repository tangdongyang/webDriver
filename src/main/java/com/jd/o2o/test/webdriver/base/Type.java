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
 * 在指定地方输入
 * 
 * @author tangdongyang
 *
 */
public class Type implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 查找元素的路径
	 * strs[2] - 输入的值
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return type(context, strs[1], strs[2]);
	}
	
	/**
	 * 在指定的xpath输入值
	 * 
	 * @param context		程序的上下文引用
	 * @param xpath			要进行输入的xpath
	 * @param value			要输入的值
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> type(Map<String, Object> context, String xpath, String value) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		WebElement element = FindWebElement.getWebElement(context, driver, xpath);
		element.clear();		//先清空
		element.sendKeys(value);	//再输入
		
		logger.info("type," + xpath + "," + value + "【success】");
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(Type.class);
}
