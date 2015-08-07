package com.jd.o2o.test.webdriver.base;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.util.Constants;

/**
 * 切换handler（在不同的窗口操作）
 * 
 * @author tangdongyang
 */
public class SwitchHandler implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 窗口title
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		String currentWindow = driver.getWindowHandle();
        //得到所有窗口的句柄
        Set<String> handles = driver.getWindowHandles();

        Iterator<String> it = handles.iterator();
        WebDriver newDriver = null;
        while(it.hasNext()){
	       if(currentWindow == it.next()) {
	    	   continue;
	       }
	       newDriver = driver.switchTo().window(it.next());
        }
		
		logger.info("切换窗口到【" + newDriver.getTitle() + "】");
		
		context.put(Constants.DRIVER, newDriver);
		return context;
	}

//	/**
//	 * strs[0] - 指令
//	 * 
//	 * strs.length == 1	//切换回之前的窗口（需要用list保持么？）
//	 * 
//	 * strs.length == 2	//切换到新窗口
//	 * strs[1] - 新窗口标志
//	 */
//	@Override
//	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
//		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
//		if(strs[1].equalsIgnoreCase(Constants.ROOT_WINDOW)) {	//如果参数为根窗口，表示回到根窗口去
//			driver.switchTo().window((String) context.get(Constants.ROOT_WINDOW));
//			return context;
//		}
//		
//		String currentWindow = driver.getWindowHandle();	//当前窗口
//		
//		//从context中获取根窗口
//		 String rootWindow =  (String) context.get(Constants.ROOT_WINDOW);
//		if(null == rootWindow) {
//			context.put(Constants.ROOT_WINDOW, currentWindow);	//将根窗口保存在context中
//		}
//		
//		Set<String> allWindows = driver.getWindowHandles();	//获取所有的窗口
//		
//		Iterator<String> iter = allWindows.iterator();
//		while(iter.hasNext()) {
//			String curWindowString = iter.next();
//			
//			if(currentWindow == curWindowString) {
//				continue;
//			}
//			
//			WebDriver window = driver.switchTo().window(curWindowString);
//			context.put(strs[1], window);	//将跳转到的新窗口，用变量保存起来
//		}
//		
//		logger.info("切换窗口到【" + strs[1] + "】");
//		
//		return context;
//	}

	private final static Logger logger = LoggerFactory.getLogger(SwitchHandler.class);
}
