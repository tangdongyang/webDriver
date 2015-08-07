package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.helper.FindWebElement;
import com.jd.o2o.test.webdriver.util.Constants;

/**
 * 检查文本或控件是否出现
 * 
 * @author tangdongyang
 */
public class Check implements BaseAction {
	
	private final static Logger logger = LoggerFactory.getLogger(Check.class);

	/**
	 * strs[0] - 命令
	 * strs[1] - 控件/文字的xpath
	 * strs[2] - 期望显示的值，如果是检查文字，需要填写此项。如果是检查控件，此项填写null
	 * strs[3] - true（应该显示）/fasle（不应该显示）
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		if (strs.length >= Constants.NUMBER_4) { //检查文字
			if ("".equals(strs[2]) || null == strs[2] || "null".equals(strs[2])) { //检查控件
				checkXpathIsExist(context, strs[1], Boolean.valueOf(strs[3]));
			} else { //检查文字
				checkTextOnXpath(context, strs[1], strs[2], Boolean.valueOf(strs[3]));
			}
		} else { //检查控件
			checkXpathIsExist(context, strs[1], Boolean.valueOf(strs[3]));
		}
		
		return context;
	}

	/**
	 * 检查控件上显示的文本是否是期望的
	 * 
	 * @param context		程序的上下文引用
	 * @param xpath			控件的xpath
	 * @param value			控件上的值
	 * @param flag			true-出现/false-不出现
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> checkTextOnXpath(Map<String, Object> context, 
			String xpath, String value, boolean flag) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		if (flag) { //出现
			WebElement element = FindWebElement.getWebElement(context, driver, xpath);
			String actual = element.getText();	//获取控件上的文本
			Assert.assertEquals(actual, value);
			logger.info("check," + xpath + ":" + value + "，期望：出现，实际：出现，【success】");
		} else { //当不出现时，代表的是其对应的文字控件路径也不出现
			checkXpathIsExist(context, xpath, flag);
		}
		
		return context;
	}

	/**
	 * 检查控件是否存在
	 * 
	 * @param context		程序的上下文引用
	 * @param xpath			要检查的xpath
	 * @param flag			true-应该存在/false-应该不存在
	 */
	public Map<String, Object> checkXpathIsExist(Map<String, Object> context, String xpath, boolean flag) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		boolean isExist = true; //判断元素是否存在
		
		if(flag) {
			//flag为true，控件应该存在
			FindWebElement.getWebElement(context, driver, xpath);	//能找到，表示出现了
			logger.info("check," + xpath + ",【success】");
		} else {
			//flag为false，控件应该不存在
			try {
				FindWebElement.getWebElement(context, driver, xpath);	//能找到，表示出现，应该不出现的
				isExist = false; 
			} catch (Exception e) {
				logger.info("check," + xpath + "，期望：不出现，实际：不出现，【success】");
			}
		}
		
		if (!isExist) {
			logger.error("check," + xpath + "，期望：不出现，实际：出现，【fail】");
			throw new AssertionError();
		}
		
		return context;
	}
}
