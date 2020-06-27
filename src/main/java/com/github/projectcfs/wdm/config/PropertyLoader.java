package com.github.projectcfs.wdm.config;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

	private final Properties properties;

	public PropertyLoader() {
		try {
			properties = new Properties();
			properties.load(PropertyLoader.class.getResourceAsStream("/wdm.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String get(String propertyKey) {
		return properties.getProperty(propertyKey);
	}

}
