package com.jd.o2o.test.webdriver.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.jd.o2o.test.webdriver.base.Screenshot;
import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.ExcelUtils;
import com.jd.o2o.test.webdriver.util.Utils;


/**
 * 解析器，核心部分
 * 根据用例中的指令，执行相应的动作
 * 
 * @author tangdongyang
 *
 */
public class Core {
	/**
	 * logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(Core.class);
	
	/**
	 * 动作关键字map(action.xml内的关键字)
	 */
	private Map<String, String> actionsMap = new HashMap<String, String>();
	
	/**
	 * 建模map(modeling.xml内的关键字)
	 */
	private Map<String, String> modelingMap = new HashMap<String, String>();
	
	/**
	 * 参数传递上下文map
	 */
	private Map<String, Object> context = new HashMap<String, Object>();
	
	/**
	 * 存放测试所有内容的list
	 */
	private List<List<String>> lists = null; 
	
	/**
	 * 读取用例中的excel文件
	 * @param caseName	放用例的文件
	 * @param caseID	要执行的用例的sheet
	 * @param status	标志位
	 */
	@SuppressWarnings("unchecked")
	public void readFile(String caseName, String caseID, String status) {
		logger.info("==================================================================================" 
				+ caseID + ":" + status + 
				"==================================================================================");
		//先检查该文件是否存在
		String path = Utils.getPath(Constants.PARAM_DIR + File.separator + 
				Constants.TESTCASE_DIR + File.separator + caseName);
		if(!Utils.isFileExists(path)) {
			logger.error("文件【" + path + "】不存在！");
			return;
		}
		
		//将excel全部解析出来，保存为list
		if(null == lists) {
			lists = getExcelContent(path, caseID);
			if(null == lists) {
				logger.error("excel中没有内容，程序退出");
				return;
			}
		}
		
		//遍历lists，进行处理，从第二行开始（第一行为注释）
		for(int i=getPositionByStatus(lists, status); i<lists.size(); ++i) {
			List<String> cellList = lists.get(i);		//获取每一行的list
			
			if(null == cellList || "".equals(cellList.get(1)) || "".equals(cellList.get(2))) {
				//状态位和指令为空，跳过改行
				continue;
			}
			
			//判断状态位
			if(cellList.get(1).equalsIgnoreCase(status) || cellList.get(1).equalsIgnoreCase(Constants.FLAG_ALL)) {
				cellList.remove(0);	//去掉注释
				cellList.remove(0);	//去掉状态位
				
				//将list转换为数组
				String[] cellArray = new String[cellList.size()];       
				cellArray = cellList.toArray(cellArray);
				
				//将数组的内容，进行判断，看是不是以xml:开头的
				//如果是，则进行替换
				cellArray = isFromModelingMap(modelingMap, cellArray);
				
				Class<?> action;
				try {
					action = Class.forName(actionsMap.get(cellArray[0].trim()));
				} catch (ClassNotFoundException e) {
					logger.error("通过反射获取关键字实例：" + cellArray[0].trim() + "失败！", e);
					closeBrowser();	//关闭浏览器
					throw new AssertionError();
				} catch (NullPointerException e) {
					logger.error("关键字不存在：" + cellArray[0].trim(), e);
					closeBrowser();	//关闭浏览器
					throw new AssertionError();
				}
				
				try {
					this.setContext((Map<String, Object>) action.getMethod("execute",
							new Class[] {Map.class, String[].class })				//获取方法
							.invoke(action.newInstance(), new Object[] {context, cellArray }));
				} catch (IllegalArgumentException e) {
					logger.error("通过反射执行关键字实例：" + cellArray[0].trim() + "失败！", e);
					closeBrowser();	//关闭浏览器
					throw new AssertionError();
				} catch (SecurityException e) {
					dealWithException(cellArray,caseID, e);
					throw new AssertionError();
				} catch (IllegalAccessException e) {
					dealWithException(cellArray,caseID, e);
					throw new AssertionError();
				} catch (InvocationTargetException e) {
					dealWithException(cellArray,caseID, e);
					throw new AssertionError();
				} catch (NoSuchMethodException e) {
					dealWithException(cellArray,caseID, e);
					throw new AssertionError();
				} catch (InstantiationException e) {
					dealWithException(cellArray,caseID, e);
					throw new AssertionError();
				} catch (Exception e) {
					dealWithException(cellArray,caseID, e);
					throw new AssertionError();
				}
			} else {
				//状态位不一致，保留状态，说明发生了状态切换，直接退出程序
				return;
			}
		}
		
		closeBrowser();	//关闭浏览器
	}
	
	/**
	 * 处理异常
	 * @param strs
	 * @param e
	 */
	private void dealWithException(String[] strs, String caseId, Exception e) {
		//截图,保存在test-output/errorPicture/
		String saveString = Utils.getPath(Constants.PARAM_TEST_OUTPUT + File.separator + 
				Constants.PARAM_ERRORPICTURES + File.separator + caseId + ".jpg");
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		Screenshot screenshot = new Screenshot();
		try {
			screenshot.screenshot(driver, saveString);
		} catch (IOException e1) {
			logger.info("dealWithException:截图出现错误");
		}
		StringBuffer log = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			if (i != 0 ) {
				log.append("," + strs[i]);
			} else {
				log.append("," + strs[i]);
			}
		}
		log.append("【fail】");
		
		logger.error(log.toString(), e);
		closeBrowser();	//关闭浏览器
	}
	
	/**
	 * 是否采用了建模文件
	 * @param modelingMap
	 * @param cellArray
	 * @return
	 */
	private String[] isFromModelingMap(Map<String, String> modelingMap, String[] cellArray) {
		for(int i=1; i<cellArray.length; ++i) {//遍历数组中的每个值
			
			if(cellArray[i].startsWith(Constants.XML)) {//如果是以xml:开头
				
				String key = cellArray[i].substring(cellArray[i].indexOf(":") + 1);//取出xml:后面的变量名称
				String mapValue = modelingMap.get(key);//从建模map中，取出对应的值
				
				if(null != mapValue || !"".equals(mapValue)) {//取出了值
					
					cellArray[i] = mapValue;//将xml:开头的值替换为实际建模中的值
//					logger.info("从建模文件中取出值：key【" + key + "】-value【" + cellArray[i] + "】");
				} else {//取出的值为空，表示没有对应的key
					
					logger.error("从建模文件中，没有取出对应key【" + key + "】的值，请检查变量名称！");
					Assert.fail("从建模文件中，没有取出对应key【" + key + "】的值，请检查变量名称！");
				}
			} else if(Constants.STR_NULL.equals(cellArray[i])) {	//如果字段为NULL，将NULL转换为空（""）
				cellArray[i] = "";
			}
		}
		
		return cellArray;
	}
	
	/**
	 * 获取lists中，状态位的位置
	 * @param lists		装内容的list
	 * @param status	状态位
	 * @return			找到的位置
	 */
	private int getPositionByStatus(List<List<String>> lists, String status) {
		int position = 1;
		for(int i=1; i<lists.size(); i++) {
			List<String> cellList = lists.get(i);
			
			if(status.equals(cellList.get(1))) {
				position = i;
				break;
			}
		}
		
		return position;
	}
	
	/**
	 * 将excel解析出来，放在list中
	 * @param path	  需要解析的excel
	 * @return	解析后的list
	 */
	private List<List<String>> getExcelContent(String path, String caseID) {
		List<List<String>> lists = new ArrayList<List<String>>(); 
		InputStream fileIn = null;
		Workbook wbIn = null;
		
		try {
			try {
				fileIn = new FileInputStream(new File(path));
				wbIn = ExcelUtils.create(fileIn);
				Sheet sheet = wbIn.getSheet(caseID);
				
				for(Row row : sheet) {
					Iterator<Cell> cellIter = row.cellIterator();
					List<String> listCell = changeCellToList(cellIter);	//将每一个cellIter解析成数组格式返回
					if(null != listCell) {
						lists.add(listCell);		//将每一行的list加入大的list中
					}
				}
			} finally {
				fileIn.close();
			}
		} catch (NullPointerException e) {
			logger.error("excel中指定的sheet不存在:" + path + ":【" + caseID + "】", e);
			throw new AssertionError();
		} catch (IOException e) {
			logger.error("读取excel出错！" + path + ":【" + caseID + "】", e);
			throw new AssertionError();
		} catch (InvalidFormatException e) {
			logger.error("读取excel出错！" + path + ":【" + caseID + "】", e);
			throw new AssertionError();
		}
		
		return lists;
	}

	/**
	 * 关闭浏览器
	 */
	private void closeBrowser() {
		WebDriver driver = (WebDriver) context.get(Constants.DRIVER);
		
		if(null != driver) {
			driver.quit();
			driver = null;
			
			context.put(Constants.DRIVER, driver);
			logger.info("关闭浏览器成功");
		}
	}

	/**
	 * 解析excel中的每一行为数组
	 * @param cellIter 每一行的迭代器
	 * @return 解析好后的list
	 */
	private List<String> changeCellToList(Iterator<Cell> cellIter) {
		//将解析出来的内容放在list中
		List<String> lists = new ArrayList<String>();
		
		while(cellIter.hasNext()) {
			String value = cellIter.next().toString();
			lists.add(value);
		}
		
		//lists的大小，必须大于等于3，才算正常（注释选填、状态位、指令）
		if(lists.size() < 3) {
			return null;
		}
		
		return lists;
	}
	
	public Core(Map<String, String> actionsMap, Map<String, String> modelingMap) {
		this.actionsMap = actionsMap;
		this.modelingMap = modelingMap;
	}
	
	public void setContext(Map<String, Object> context) {
		this.context = context;
	}
}
