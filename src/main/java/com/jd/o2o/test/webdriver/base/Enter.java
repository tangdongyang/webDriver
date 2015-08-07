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
 * 在指定xpath点击回车
 * 
 * @author tangdongyang
 */

public class Enter implements BaseAction {

	@Override
	/**
	 * strs[0]	-	指令
	 * strs[1]	-	要敲回车的xpath
	 */
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return enter(context, strs[1]);
	}

	/**
	 * 在指定xpath位置点击回车
	 * 
	 * @param context			程序的上下文引用
	 * @param xpath				点击回车的地方
	 * @return	context			程序的上下文引用
	 */
	public Map<String, Object> enter(Map<String, Object> context, String xpath) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		try {
			WebElement element = FindWebElement.getWebElement(context, driver, xpath);
			element.sendKeys("\n");
			
			logger.info("在【" + xpath + "】敲回车");
		} catch (Exception e) {
			Assert.fail("enter的时候，出现异常：【" + xpath + "】", e);
		}
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(Enter.class);
}
