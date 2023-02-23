package com.framework.pie.quartz.utils;

import java.util.HashMap;
import java.util.Map;

public class SfMap {
	private static Map<String, String> cacheMap;

	public static Object getCache(String key, Object defaultValue) {
		Object obj = getCacheMap().get(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, String value) {
		getCacheMap().put(key, value);
	}

	public static void removeCache(String key) {
		getCacheMap().remove(key);
	}

	public static String getCache(String key){
		return getCacheMap().get(key);
	}

	public static HashMap<String, String> getCacheMap() {
		if (cacheMap == null) {
			cacheMap = new HashMap<String, String>();
		}
		return (HashMap<String, String>) cacheMap;
	}
}
