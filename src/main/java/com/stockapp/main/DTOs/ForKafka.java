package com.stockapp.main.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ForKafka {

	private final Map<String,AggregatesResult> grupo = new HashMap<String,AggregatesResult>();
	
	public void setData(String key, AggregatesResult value) {
		grupo.put(key, value);
	}
	
	public Map<String,AggregatesResult> getMap () {
		return grupo;
	}
	
	public boolean isEmpty() {
		return grupo.isEmpty();
	}
}
