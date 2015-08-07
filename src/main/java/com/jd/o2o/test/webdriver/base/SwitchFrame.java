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
 * 跳转到其他的frame
 * 
 * @author tangdongyang
 *
 */
public class SwitchFrame implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 跳向frame的xpath
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return switchFrame(context, strs[1]);
	}
	
	/**
	 * 跳向指定的iframe
	 * 
	 * @param context		程序的上下文引用
	 * @param xpath			跳向指定xpath的iframe（值为rootFrame表示进入页面默认的iframe）
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> switchFrame(Map<String, Object> context, String xpath) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		try {
			if(xpath.equalsIgnoreCase(Constants.ROOT_FRAME)) {
				driver.switchTo().defaultContent();
			} else {
				WebElement element = FindWebElement.getWebElement(context, driver, xpath);
				driver.switchTo().frame(element);
			}
			
			logger.info("frame转向【" + xpath + "】");
		} catch (Exception e) {
			Assert.fail("switchFrame的时候，出现异常：【" + xpath + "】", e);
		}
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(SwitchFrame.class);
}
