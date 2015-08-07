package com.jd.o2o.test.webdriver.helper;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;

public class Browser {
	
	public static WebDriver driver = null;
	
	/**
	 * ie浏览器
	 */
	public static WebDriver getIE() {
//		String driverPath = Utils.getPath(Constants.PARAM_DIR + File.separator + 
//				Constants.DRIVER_DIR + File.separator +  "IEDriverServer.exe");
//		
//		System.setProperty("webdriver.ie.driver", driverPath);
//	    DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
//	    ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	    
	    driver = new InternetExplorerDriver();
		
		return driver;
	}
	
	/**
	 * firefox浏览器
	 */
	public static WebDriver getFF() {
		driver = new FirefoxDriver();
		
		return driver;
	}
	
	/**
	 * 谷歌浏览器
	 */
	public static WebDriver getChrome() {
		String driverPath = Utils.getPath(Constants.PARAM_DIR + File.separator + 
				Constants.DRIVER_DIR + File.separator + "chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		
		return driver;
	}
}
