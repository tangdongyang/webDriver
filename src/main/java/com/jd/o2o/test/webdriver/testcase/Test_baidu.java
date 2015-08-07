package com.jd.o2o.test.webdriver.testcase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jd.o2o.test.webdriver.baseTest.BaseTest;
import com.jd.o2o.test.webdriver.util.Constants;

public class Test_baidu extends BaseTest {
	
	private final static String TEST_CASE_NAME = "test1//testBaidu.xls";
	private final static String SHEET_NAME = "test_baidu";
	
	@BeforeClass
	public void prepare() {
		core.readFile(TEST_CASE_NAME, SHEET_NAME, Constants.FLAG_PREPARE);
	}
	
	@Test
	public void test() {
		core.readFile(TEST_CASE_NAME, SHEET_NAME, Constants.FLAG_TEST);
	}
	
	@AfterClass
	public void clear() {
		core.readFile(TEST_CASE_NAME, SHEET_NAME, Constants.FLAG_CLEAR);
	}
}
