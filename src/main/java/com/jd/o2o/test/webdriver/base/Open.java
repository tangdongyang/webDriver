package com.jd.o2o.test.webdriver.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.helper.Browser;
import com.jd.o2o.test.webdriver.util.Constants;

/**
 * 打开浏览器
 * 
 * @author tangdongyang
 * 
 */
public class Open implements BaseAction {

	@Override
	/**
	 * strs[0] - 指令
	 * strs[1] - 浏览器类型(ie,ff,chrome)
	 * strs* strs[1] - 浏览器类型(ie,ff,chrome)
	 * strs[2] - 要打开的网址
	 * strs[3] - 执行机地址，如果不填则默认在本机执行
	 */
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) throws MalformedURLException {
		if (strs.length >= Constants.NUMBER_4) { //远程执行
			if ("".equals(strs[3]) || null == strs[3] || "127.0.0.1".equals(strs[3])
					|| "localhost".equals(strs[3])) { //本机执行
				return open(context, strs[1], strs[2]);
			} else { //远程执行
				return open(context, strs[1], strs[2], strs[3]); 
			}
		} else { //本机执行
			return open(context, strs[1], strs[2]);
		}
		
		
	}

	/**
	 * 
	 * @param context 程序的上下文引用
	 * @param type 打开的浏览器类型(ie、ff、chrome)
	 * @param url 打开的地址
	 * @param ip 执行机ip
	 * @return 程序的上下文引用
	 * @throws MalformedURLException 
	 */
	public Map<String, Object> open(Map<String, Object> context,
			String type, String url, String ip) throws MalformedURLException {
		String hubURL = "http://" + ip + ":4444/wd/hub";
		WebDriver driver = null;
		DesiredCapabilities capability = null;
		if ("ie".equalsIgnoreCase(type)) {
			capability = DesiredCapabilities.internetExplorer();
		} else if ("ff".equalsIgnoreCase(type)) {
			capability = DesiredCapabilities.firefox();
		} else if ("chrome".equalsIgnoreCase(type)) {
			capability = DesiredCapabilities.chrome();
		}
		
		driver = new RemoteWebDriver(new URL(hubURL), capability);
		driver.get(url);
		driver.manage().window().maximize();	//最大化浏览器
		logger.info("open," + type + "," + url + "," + ip + "【success】");

		context.put(Constants.DRIVER, driver);
		return context;
	}

	/**
	 * 本地执行:打开指定地址的浏览器
	 * 
	 * @param context		程序的上下文引用
	 * @param type			打开的浏览器类型(ie、ff、chrome)
	 * @param url				打开的地址
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> open(Map<String, Object> context, String type, String url) {
		WebDriver driver = null;
		
		if ("ie".equalsIgnoreCase(type)) {
			driver = Browser.getIE();
		} else if ("ff".equalsIgnoreCase(type)) {
			driver = Browser.getFF();
		} else if ("chrome".equalsIgnoreCase(type)) {
			driver = Browser.getChrome();
		}

		driver.get(url); // 打开指定的网址
		driver.manage().window().maximize();	//最大化浏览器
		
		context.put(Constants.DRIVER, driver);
		logger.info("open," + type + "," + url + "【success】");
		return context;
	}

	private final static Logger logger = LoggerFactory.getLogger(Open.class);
}
