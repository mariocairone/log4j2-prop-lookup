package com.mariocairone.log4j2.lookup;

import static org.junit.Assert.assertNotNull;

import java.util.Properties;

import org.junit.Test;

import com.mariocairone.log4j2.lookup.services.PropertiesLoader;
import com.mariocairone.log4j2.lookup.services.PropertiesLoaderFactory;

public class PropertiesLoaderTest {

	@Test
	public void loadYamlPropertiesTest() throws Exception {
		PropertiesLoader loader = PropertiesLoaderFactory.createPropertiesLoader("test.yaml");	
		Properties prop = loader.getProperties();
		System.out.println(prop);
		assertNotNull(prop.get("logstash.url"));
	}

	@Test
	public void loadPropertiesFileTest() throws Exception {
		PropertiesLoader loader = PropertiesLoaderFactory.createPropertiesLoader("test.properties");	
		Properties prop = loader.getProperties();
		System.out.println(prop);
		assertNotNull(prop.get("logstash.url"));
		
	}
	
}
