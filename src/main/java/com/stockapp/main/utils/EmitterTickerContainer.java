package com.stockapp.main.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class EmitterTickerContainer {

	/**
	 * Contendra los listados con los EmitterTickers
	 */
	private final Map<String,EmitterTicker> almacen = new HashMap<String,EmitterTicker>();
	
	public boolean isEmitterTicker(String key) {
		return almacen.containsKey(key);
	}	
	public EmitterTicker getEmitterTicker(String key) {
		return almacen.get(key);
	}
	public void setEmitterTicker(String key, EmitterTicker et) {
		this.almacen.put(key, et);
	}
}
