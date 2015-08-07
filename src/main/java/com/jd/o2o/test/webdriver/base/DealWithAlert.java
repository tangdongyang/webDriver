package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.util.Constants;

/**
 * 处理javascript的alert或confirm弹出框
 * 
 * @author tangdongyang
 */
public class DealWithAlert implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 操作项，有3种方式：
	 * (yes-确定，cancel-取消，比较弹出框)
	 * <1>yes - 点击确定
	 * <2>cancel - 点击取消
	 * <3>字符串：用该字符串与弹出框的内容比较(相等-通过，不相等-失败)
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		return dealWithAlert(context, strs[1]);
	}
	
	/**
	 * 处理弹出框
	 * @param context
	 * @param action 执行动作（yes-点击确定,cancel-点击取消）
	 * @return
	 */
	public Map<String, Object> dealWithAlert(Map<String, Object> context,
			String action) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		Alert alert = driver.switchTo().alert();
		
		if ("yes".equals(action)) {
			alert.accept(); //点击确定按钮s
		} else if ("cancel".equals(action)) {
			alert.dismiss(); //点击取消按钮
		} else {
			Assert.assertEquals(alert.getText(), action);
		}
		
		context.put(Constants.DRIVER, driver);
		logger.info("dealWithAlert," + action + "," + "【success】");
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(DealWithAlert.class);
}
