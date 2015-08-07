package com.jd.o2o.test.webdriver.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公用函数封装类
 * @author cdliujun1
 *
 */
public class Utils {
	/**
	 * logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	/**
	 * 等待指定的时间
	 * 
	 * @param time	等待的时间，单位毫秒
	 */
	public static void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据xml文件，读取出其中的数据
	 * @param map 		存放到指定的map
	 * @param xmlFile	xml文件名称
	 * @return	用数据构造好的map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> readXMLToMap(Map<String, String> map, String xmlFile) {
		//得到xml文件的路径
		String path = Utils.getPath(xmlFile);
		if(!Utils.isFileExists(path)) {
			logger.error("文件【" + path + "】不存在");
			return null;
		}
		
		SAXReader reader = new SAXReader();
		Document doc = null;
		
		try {
			doc = reader.read(new File(path));
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		}
		
		Element root = doc.getRootElement();
		
		Iterable<Element> iters = root.elements();
		for(Element element : iters) {
			map.put(element.attributeValue("name"), element.attributeValue("value"));
		}
		
		return map;
	}
	
	/**
	 * 根据传进来的文件路径，判断文件是否存在
	 * @param filename	文件名
	 * @return	true-存在/false-不存在
	 */
	public static boolean isFileExists(String filename) {
		boolean flag = false;
		
		File file = new File(filename);
		if(file.exists()) {
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 根据文件名，组合出文件的路径
	 * @param filename
	 * @return
	 */
	public static String getPath(String filename) {
		String path = null;
		
		path = System.getProperty("user.dir");
		path += File.separator + filename;
		
		return path;
	}
	
    /**
     * 对字符串进行转码
     * @param str 待转码的字符串
     * @return String 转码后的字符串
     */
    public static String encod(String str) {
        String encodURL = str;
        try {
            encodURL = java.net.URLEncoder.encode(str, "UTF-8"); //转码
        } catch (UnsupportedEncodingException e) {
            logger.warn("转码失败:" + str, e);
        }
        return encodURL;
    }
}
