package com.stockapp.main.DTOs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ForKafka {

	private final Map<String,AggregatesResult> grupo = new HashMap<String,AggregatesResult>();
	
	public void setData(String key, AggregatesResult value) {
		grupo.put(key, value);
	}
	
	public Set<String> keySet() {
        return grupo.keySet();
    }
	
	public AggregatesResult getAgg(String key) {
		return grupo.get(key);
	}
	
	public boolean isEmpty() {
		return grupo.isEmpty();
	}
}
