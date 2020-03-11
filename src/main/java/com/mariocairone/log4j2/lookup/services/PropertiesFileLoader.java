package com.mariocairone.log4j2.lookup.services;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public class PropertiesFileLoader extends AbstractPropertiesLoader  {

	public static final Logger LOGGER = StatusLogger.getLogger();
	
	public PropertiesFileLoader(String fileName) {
		this.fileName = fileName;
	}
	
	public void load() {
    	try {
			properties.load(getFileAsStream(fileName));
			LOGGER.debug("Loded Properties file");
		} catch (Exception e) {
			LOGGER.warn("Exception Initializing properties for plugin properties lookup");
		} finally {
			loaded = true;
		}
    	
	}

}
