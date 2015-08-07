package com.jd.o2o.test.webdriver.baseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.o2o.test.webdriver.core.Core;
import com.jd.o2o.test.webdriver.util.Constants;
import com.jd.o2o.test.webdriver.util.Utils;


/**
 * testCase基础类
 * @author cdliujun1
 *
 */
public abstract class BaseTest {
	/**
	 * 用例文件解析器对象
	 */
	protected Core core = null;
	
	/**
	 * log
	 */
	public final static Logger logger = LoggerFactory.getLogger(BaseTest.class);
	
	/**
	 * 动作关键字map
	 */
	protected Map<String, String> actionsMap = new HashMap<String, String>();
	
	/**
	 * 建模的map
	 */
	protected Map<String, String> modelingMap = new HashMap<String, String>();
	
    /**
     * 准备数据.【注意】请在子类中对应方法前一定加上TestNG的标签@BeforeClass
     */
	public abstract void prepare();
	
    /**
     * 测试验证点.【注意】请在子类中对应方法前一定加上TestNG的标签@Test
     */
	public abstract void test();
	
    /**
     * 恢复环境.【注意】请在子类中对应方法前一定加上TestNG的标签@AfterClass(alwaysRun=true)
     */
	public abstract void clear();
	
	/**
	 * 构造函数，初始化数据
	 */
	public BaseTest() {
		//先读取系统的conf.properties文件，从文件中解析出需要加载哪些建模文件
		//如果该文件不存在，退出程序，报错
		String confPath = Utils.getPath(Constants.PARAM_DIR + File.separator + "conf.properties");
		if(!Utils.isFileExists(confPath)) {
			logger.error("文件【" + confPath + "】不存在！");
			return;
		}
		
		String actionFiles = null;
		String modelingFiles = null;
		
		InputStream in = null;
		try {
			try {
				in = new FileInputStream(new File(confPath));
				Properties prop = new Properties();
				prop.load(in);
				
				actionFiles = prop.getProperty("actionFile");
				modelingFiles = prop.getProperty("modelingFile");
			} finally {
				in.close();
			}
		} catch (Exception e) {
			logger.error("读取文件【" + confPath + "】出错！", e);
			throw new AssertionError();
		}
		
		initActionsMap(actionFiles);
		initModelingMap(modelingFiles);
		
		core = new Core(actionsMap, modelingMap);
	}
	
	/**
	 * 从modeling.xml文件里读取数据，初始化建模map
	 */
	private void initModelingMap(String modelingFiles) {
		String[] modelingFile = modelingFiles.split(",");
		
		for(String str : modelingFile) {
			modelingMap = Utils.readXMLToMap(modelingMap, 
					Constants.PARAM_DIR + File.separator + 
					Constants.MODELING_DIR + File.separator + 
					str.trim());
//			logger.info("初始化建模文件【" + str.trim() + "】成功");
		}
	}

	/**
	 * 从action.xml文件里读取数据，初始化动作关键字map
	 */
	private void initActionsMap(String actionFiles) {
		String[] actionFile = actionFiles.split(",");
		
		for(String str : actionFile) {
			actionsMap = Utils.readXMLToMap(actionsMap, 
					Constants.PARAM_DIR + File.separator + 
					Constants.ACTION_DIR + File.separator + 
					str.trim());
//			logger.info("初始化action文件【" + str.trim() + "】成功");
		}
	}
}
