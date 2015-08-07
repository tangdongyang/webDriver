package com.jd.o2o.test.webdriver.base;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.util.Constants;

/**
 * 等待指定的时间
 * 
 * @author tangdongyang
 */
public class Wait implements BaseAction {

	/**
	 * strs[0] - 指令
	 * strs[1] - 等待的时间，单位毫秒
	 */
	@Override
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) {
		
		return wait(context, strs[1]);
	}
	
	/**
	 * 等待指定的时间
	 * 
	 * @param context		程序的上下文引用
	 * @param time			需要等待的时间，单位毫秒
	 * @return	context		程序的上下文引用
	 */
	public Map<String, Object> wait(Map<String, Object> context, String time) {
		int sleepTime = 0;
		
		try {
			sleepTime = Integer.parseInt(time);
		} catch (Exception e) {
			if(time.contains(".")) {
				try {
					sleepTime = Integer.parseInt(time.substring(0, time.indexOf(".")));
				} catch (Exception e2) {
					logger.error("解析wait的时间出现问题 【 "+ time + " 】，采用默认时间【" + Constants.SLEEP_TIMES + "】", e2);
					sleepTime = Constants.SLEEP_TIMES;
				}
			}
		}
		
		try {
			logger.info("等待开始...");
			Thread.sleep(sleepTime);
			logger.info("等待结束...");
		} catch (Exception e) {
			Assert.fail("wait的时候，出现异常：【" + time + "】", e);
		}
		
		return context;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(Wait.class);
}
