package com.jd.o2o.test.webdriver.base;

import java.util.Map;

/**
 * 所有action的基类
 * 
 * @author tangdongyang
 *
 */
public interface BaseAction {
	/**
	 * action入口函数
	 * @param context	上下文引用
	 * @param strs		从excel中读取的参数
	 * @throws Exception 
	 */
	public Map<String, Object> execute(Map<String, Object> context, String[] strs) throws Exception;
}
