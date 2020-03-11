package com.mariocairone.log4j2.lookup.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PropertiesLoaderFactory {

	private static final Set<String> YAML_TYPES = new HashSet<>(Arrays.asList("YAML","YML"));
	
	public static PropertiesLoader createPropertiesLoader(String fileName) {
		String extension = getFileExtension(fileName).orElse("");

		if(YAML_TYPES.contains(extension.toUpperCase())) {
			return new YamlPropertiesLoader(fileName);
		}
		return new PropertiesFileLoader(fileName);
	}
	
	private static Optional<String> getFileExtension(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1).toUpperCase());
	}
}
