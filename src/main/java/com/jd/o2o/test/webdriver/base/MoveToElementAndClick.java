package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.helper.FindWebElement;
import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;

/**
 * 移动到某个元素，会显示隐藏的控件，然后点击显示出来的控件
 * 
 * @author tangdongyang
 */
public class MoveToElementAndClick implements BaseAction {
	
	@Override
	/**
	 * strs[0]	-	指令
	 * strs[1]	-	移动到元素的xpath
	 * strs[2]	-	要点击元素的xpath
	 */
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return moveToElementAndClick(context, strs[1], strs[2]);
	}
	
	/**
	 * 移动到某个元素，会显示其隐藏的控件，然后点击其中一个显示出来的控件
	 * 
	 * @param context			程序的上下文引用
	 * @param moveXpath	要移动到元素的xpath
	 * @param clickXpath		要点击元素的xpath
	 * @return context			程序的上下文引用
	 */
	public Map<String, Object> moveToElementAndClick(Map<String, Object> context, 
			String moveXpath, String clickXpath) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		//要移动到的元素
		WebElement moveElement = FindWebElement.getWebElement(context, driver, moveXpath);
		Actions builder = new Actions(driver);
		Action action = builder.moveToElement(moveElement).build();
		action.perform();
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		//要点击的元素
		WebElement clickElement = FindWebElement.getWebElement(context, driver, clickXpath);
		clickElement.click();
		
		logger.info("移动到元素【" + moveXpath + "】，点击元素【" + clickXpath + "】,【sucess】");
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(MoveToElementAndClick.class);
}
