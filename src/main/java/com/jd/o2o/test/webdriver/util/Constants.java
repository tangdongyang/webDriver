package com.jd.o2o.test.webdriver.util;

/**
 * 公用静态常量定义类
 * @author cdliujun1
 *
 */
public class Constants {
	/**
	 * 状态位:准备环境
	 */
	public final static String FLAG_PREPARE 	= "prepare";
	
	/**
	 * 状态位:执行测试
	 */
	public final static String FLAG_TEST 			= "test";
	
	/**
	 * 状态位：清除环境
	 */
	public final static String FLAG_CLEAR 		= "clear";
	
	/** 
	 * 状态位:执行所有（包括准备环境-执行测试-清除环境）
	 */
	public final static String FLAG_ALL 			= "all";
	
	/**
	 * 等待次数
	 */
	public final static int WAIT_TIMES 	= 5;
	
	/**
	 * 等待时间（毫秒）
	 */
	public final static int SLEEP_TIMES = 800;
	
	/**
	 * 寻找元素的分隔符
	 */
	public final static String SPLIT_STR = "#";
	
	/**
	 * 寻找元素的方式:css
	 */
	public final static String BY_CSS 		= "css";
	
	/**
	 * 寻找元素的方式:class
	 */
	public final static String BY_CLASS 	= "class";
	
	/**
	 * 寻找元素的方式:id
	 */
	public final static String BY_ID = "id";
	
	/**
	 * 寻找元素的方式:name
	 */
	public final static String BY_NAME = "name";
	
	/**
	 * 寻找元素的方式:tag
	 */
	public final static String BY_TAG 		= "tag";
	
	/**
	 * 寻找元素的方式:xpath
	 */
	public final static String BY_XPATH 	= "xpath";
	
	/**
	 * 根框架,frame
	 */
	public final static String ROOT_FRAME 	= "rootFrame";
	
	/**
	 * 根窗口,window
	 */
	public final static String ROOT_WINDOW 	= "rootWindow";
	
	/**
	 * driver
	 */
	public final static String DRIVER		= "driver";
	
	/**
	 * 建模文件的变量
	 */
	public final static String MODELING_MAP		= "modelingMap";
	
	/**
	 * null
	 */
	public final static String STR_NULL = "NULL";
	
	/**
	 * 表示放在context中的变量
	 */
	public final static String CTX 			= "ctx:";
	
	/**
	 * 表示放在xml建模文件中的变量
	 */
	public final static String XML 			= "xml:";
	
	/**
	 * cookie(构造请求)
	 */
	public final static String COOKIE = "cookie";
	
	/**
	 * session(构造请求)
	 */
	public final static String SESSION = "session";
	
	/**
	 * client(构造请求)
	 */
	public final static String CLIENT = "client";
	
	/**
	 * param(存放action，driver，modeling，testcase的总目录)
	 */
	public final static String PARAM_DIR 		= 	"param";
	
	/**
	 * test-output(存放测试报告的路径)
	 */
	public final static String PARAM_TEST_OUTPUT 		= 	"test-output";
	
	/**
	 * errorPictures(存放错误截图的路径)
	 */
	public final static String PARAM_ERRORPICTURES 		= 	"errorPictures";
	
	/**
	 * action(存放action文件的目录)
	 */
	public final static String ACTION_DIR		= 	"action";
	
	/**
	 * driver(存放driver的目录)
	 */
	public final static String DRIVER_DIR 	= "driver";
	
	/**
	 * modeling(存放建模文件的目录)
	 */
	public final static String MODELING_DIR	=	"modeling";
	
	/**
	 * testcase(存放用例的目录)
	 */
	public final static String TESTCASE_DIR		= 	"testcase";
	
	/**
	 * testfile(存放测试文件的目录)
	 */
	public final static String TESTFILE_DIR		= 	"testfile";
	
	/**
	 * 字符串：true
	 */
	public final static String FLAG_TRUE = "true";
	
	/**
	 * 字符串：false
	 */
	public final static String FLAG_FALSE = "false";
	
	/**
	 * 数字：2
	 */
	public final static int NUMBER_2 = 2;
	
	/**
	 * 数字：3
	 */
	public final static int NUMBER_3 = 3;
	
	/**
	 * 数字：4
	 */
	public final static int NUMBER_4 = 4;
	
	/**
	 * 数字：5
	 */
	public final static int NUMBER_5 = 5;
	
	/**
	 * 数字：1
	 */
	public final static int NUMBER_1 = 1;
	
	/**
	 * 数字：0
	 */
	public final static int NUMBER_0 = 0;
}
