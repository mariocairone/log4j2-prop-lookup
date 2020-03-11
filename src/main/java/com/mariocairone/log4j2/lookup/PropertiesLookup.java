package com.mariocairone.log4j2.lookup;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractConfigurationAwareLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.apache.logging.log4j.status.StatusLogger;

import com.mariocairone.log4j2.lookup.services.PropertiesLoader;
import com.mariocairone.log4j2.lookup.services.PropertiesLoaderFactory;

@Plugin(name = "prop", category = "Lookup")
public class PropertiesLookup extends AbstractConfigurationAwareLookup implements StrLookup {

	private static final String LOG4J2_FILES_PROPERTY_NAME = "prop.files";
	private static final String LOG4J2_DIR_PROPERTY_NAME = "prop.dir";

	public static final Logger LOGGER = StatusLogger.getLogger();

	private Properties properties;

	private boolean loaded;

	public PropertiesLookup() {
		this.loaded = false;
		this.properties = new Properties();
	}

	private void load() {
		if (configuration != null) {
			if (!loaded) {
				StrLookup resolver = configuration.getStrSubstitutor().getVariableResolver();
				final String dir = resolver.lookup(LOG4J2_DIR_PROPERTY_NAME);
				String files = resolver.lookup(LOG4J2_FILES_PROPERTY_NAME);
				
				LOGGER.debug("Properties File: " + files);
				Arrays.asList(files.split(",")).forEach(fileName -> {
					String filePath = getFilePath(dir,fileName);
					PropertiesLoader loader = PropertiesLoaderFactory.createPropertiesLoader(filePath);
					this.properties = loader.getProperties();
				});
				loaded = true;
			}
		}
	}

	@Override
	public String lookup(String key) {
		return getProperties().getProperty(key);
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return lookup(key);
	}

	public Properties getProperties() {
		if (!loaded)
			load();

		return properties;
	}
	
	private String getFilePath(String dir,String fileName) {
		String filePath = fileName;
		
		if (dir != null)
			filePath = Paths.get(dir, fileName).toString();

		return filePath;
	}

}