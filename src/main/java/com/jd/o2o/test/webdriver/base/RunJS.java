package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;

/**
 * 执行javascript代码
 * 
 * @author tangdongyang
 */
public class RunJS implements BaseAction {

	@Override
	/**
	 * strs[0]	-	指令
	 * strs[1]	-	javascript代码
	 * strs[2]	-	保存返回的元素变量名【可选】
	 */
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		if(strs.length > 2 && !"".equals(strs[2]) && null != strs[2]) {
			//2个参数的用法
			context = runJSWithVar(context, strs[1], strs[2]);
		} else {
			//1个参数的用法
			runJS(context, strs[1]);
		}
		
		return context;
	}

	/**
	 * 执行javascript代码
	 * 
	 * @param context		程序的上下文引用
	 * @param jsString		要执行的javascript代码
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> runJS(Map<String, Object> context, String jsString) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		try {
			JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
			jsDriver.executeScript(jsString);
			
			logger.info("执行javascript代码【" + jsString + "】成功");
		} catch (Exception e) {
			Assert.fail("runJS的时候，出现异常：【" + jsString + "】", e);
		}
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	/**
	 * 执行javascript代码，并将返回的element保存在var中
	 * 
	 * @param context		程序的上下文引用
	 * @param jsString		要执行的javascript代码，js代码中必须含有return语句
	 * @param var				保存的变量名称
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> runJSWithVar(Map<String, Object> context, String jsString, String var) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		try {
			JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
			WebElement element = (WebElement) jsDriver.executeScript(jsString);
			
			context.put(var, element);
			
			logger.info("执行javascript代码【" + jsString + "】成功，返回值保存在【" + var + "】中");
		} catch (Exception e) {
			Assert.fail("runJS的时候，出现异常：【" + jsString + "】-【" + var + "】", e);
		}
		
		Utils.wait(Constants.SLEEP_TIMES);
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(RunJS.class);
}
