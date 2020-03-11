package com.mariocairone.log4j2.lookup.services;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public abstract class AbstractPropertiesLoader implements PropertiesLoader {

	protected static final Logger LOGGER = StatusLogger.getLogger();

	protected Properties properties = new Properties();
	
	protected boolean loaded = false;
	
	protected String fileName;
	
	abstract public void load();
	
	public Properties getProperties() {
		if(!loaded)
			load();
		
		return properties;
	}
	
	protected InputStream getFileAsStream(String fileName) {
		return this.getClass().getClassLoader().getResourceAsStream(fileName);
	}
	

}
