package com.work.util;

import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesUtil {

	static Logger	log				= Logger.getLogger(PropertiesUtil.class);
	
	final static String PROPERTY_FILE_NAME = "config.properties";

	public static String getPorpertyValue(String Key) {

		String value = "";
		Properties props = new Properties();
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//InputStream inputStream = loader.getResourceAsStream("config.properties");
		//loader.getResourceAsStream(PROPERTY_FILE_NAME)
		
		try {
			//props.load(new FileInputStream(PROPERTY_FILE_NAME));
			props.load( loader.getResourceAsStream(PROPERTY_FILE_NAME) );
			value = props.getProperty(Key).trim();
			
			return (String)value;
		}catch (Exception e) {
			System.out.println("PropertiesUtil:"+e.getMessage());
			log.error("[EXCEPTION]", e);
			return "";
		}
	}
	
}
