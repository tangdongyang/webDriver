package com.jd.o2o.test.webdriver.helper;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.util.Constants;

public class FindWebElement {
	/**
	 * 获取指定的元素
	 * 
	 * @param context
	 *            程序的上下文引用
	 * @param driver
	 *            浏览器驱动
	 * @param str
	 *            要查找的路径
	 * @return 找到的元素
	 */
	public static WebElement getWebElement(Map<String, Object> context,
			WebDriver driver, String str) {

		WebElement element = null;
		
		if (str.startsWith(Constants.CTX)) {
			//判断是不是用context中的变量获取element
			String var = str.substring(str.indexOf(":") + 1);
			element = (WebElement) context.get(var);
			
			if(null == element) {
				//通过变量获取的元素值为null，表示变量不正确
				logger.error("通过context变量【" + str + "】,没有获取到需要的元素，请检查变量名称！");
				Assert.fail("通过context变量【" + str + "】,没有获取到需要的元素，请检查变量名称！");
			} 
		} else {
			element = getWebElement(driver, str);
		}
		
		return element;
	}

	/**
	 * 获取指定的元素
	 * 
	 * @param driver
	 *            浏览器驱动
	 * @param str
	 *            要查找的路径
	 * @return 找到的元素
	 */
	private static WebElement getWebElement(WebDriver driver, String str) {
		String[] strs = str.split(Constants.SPLIT_STR);

		if (strs.length == 2) { // 有#分隔，表示采用其他的方式查找
			if (Constants.BY_CLASS.equalsIgnoreCase(strs[0])) {
				// 按className的方式查找

				return getByClassName(driver, strs[1]);
			} else if (Constants.BY_CSS.equalsIgnoreCase(strs[0])) {
				// 按cssSelector的方式查找

				return getByCssSelector(driver, strs[1]);
			} else if (Constants.BY_ID.equalsIgnoreCase(strs[0])) {
				// 按id的方式查找

				return getByID(driver, strs[1]);
			} else if (Constants.BY_NAME.equalsIgnoreCase(strs[0])) {
				// 按name的方式查找

				return getByName(driver, strs[1]);
			} else if (Constants.BY_TAG.equalsIgnoreCase(strs[0])) {
				// 按tagName的方式查找

				return getByTagName(driver, strs[1]);
			} else if (Constants.BY_XPATH.equalsIgnoreCase(strs[0])) {
				// 按xpath的方式查找

				return getByXpath(driver, strs[1]);
			} else {
				// 使用的其他方式，找不到
				logger.error("找不到【" + strs[0] + "】方式查找元素");
			}
		}
		
		// 默认采用xpath查找
		return getByXpath(driver, str);
	}

	/**
	 * 以className的方式查找元素
	 * 
	 * @param driver
	 *            驱动
	 * @param className
	 *            class名称
	 * @return 找到的WebElement
	 */
	private static WebElement getByClassName(WebDriver driver,
			final String className) {
		WebElement element = null;

		element = (new WebDriverWait(driver, Constants.WAIT_TIMES))
				.until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver d) {
						WebElement we = null;
						try {
							we = d.findElements(By.className(className)).get(0);
						} catch (Exception e) {
							logger.error("无法找到：【" + className + "】");
						}
						return we;
					}
				});

		return element;
	}

	/**
	 * 以css的方式查找
	 * 
	 * @param driver
	 * @param cssSelector
	 * @return
	 */
	private static WebElement getByCssSelector(WebDriver driver,
			final String cssSelector) {
		WebElement element = null;

		element = (new WebDriverWait(driver, Constants.WAIT_TIMES))
				.until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver d) {
						WebElement we = null;
						try {
							we = d.findElements(By.cssSelector(cssSelector)).get(0);
						} catch (Exception e) {
							logger.error("无法找到：【" + cssSelector + "】");
						}
						return we;
					}
				});

		return element;
	}

	/**
	 * 以id的方式查找
	 * 
	 * @param driver
	 * @param id
	 * @return
	 */
	private static WebElement getByID(WebDriver driver, final String id) {
		WebElement element = null;

		element = (new WebDriverWait(driver, Constants.WAIT_TIMES))
				.until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver d) {
						WebElement we = null;
						try {
							we = d.findElements(By.id(id)).get(0);
						} catch (Exception e) {
							logger.error("无法找到：【" + id + "】");
						}
						return we;
					}
				});

		return element;
	}

	/**
	 * 以name的方式查找
	 * 
	 * @param driver
	 * @param name
	 * @return
	 */
	private static WebElement getByName(WebDriver driver, final String name) {
		WebElement element = null;

		element = (new WebDriverWait(driver, Constants.WAIT_TIMES))
				.until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver d) {
						WebElement we = null;
						try {
							we = d.findElements(By.name(name)).get(0);
						} catch (Exception e) {
							logger.error("无法找到：【" + name + "】");
						}
						return we;
					}
				});

		return element;
	}

	/**
	 * 以tagName的方式查找
	 * 
	 * @param driver
	 * @param tagName
	 * @return
	 */
	private static WebElement getByTagName(WebDriver driver,
			final String tagName) {
		WebElement element = null;

		element = (new WebDriverWait(driver, Constants.WAIT_TIMES))
				.until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver d) {
						WebElement we = null;
						try {
							we = d.findElements(By.tagName(tagName)).get(0);
						} catch (Exception e) {
							logger.error("无法找到：【" + tagName + "】");
						}
						return we;
					}
				});

		return element;
	}

	/**
	 * 以xpath的方式查找
	 * 
	 * @param driver
	 * @param xpath
	 * @return
	 */
	private static WebElement getByXpath(WebDriver driver, final String xpath) {
		WebElement element = null;

		element = (new WebDriverWait(driver, Constants.WAIT_TIMES))
				.until(new ExpectedCondition<WebElement>() {

					public WebElement apply(WebDriver d) {
						WebElement we = null;
						try {
							we = d.findElements(By.xpath(xpath)).get(0);
						} catch (Exception e) {
							logger.error("无法找到：【" + xpath + "】");
						}
						
						return we;
					}
				});

		return element;
	}

	private final static Logger logger = LoggerFactory.getLogger(FindWebElement.class);

	private FindWebElement() {}
}
