package com.jd.o2o.test.webdriver.testcase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jd.o2o.test.webdriver.baseTest.BaseTest;
import com.jd.o2o.test.webdriver.util.Constants;

public class Test_LoginErp extends BaseTest {
	
	private final static String TEST_CASE_NAME = "testLoginErp//testLoginErp.xls";
	private final static String SHEET_NAME = "testLoginErp";
	
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
