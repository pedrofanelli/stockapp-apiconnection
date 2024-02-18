package com.stockapp.main.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmitterTickerManager {

	@Autowired
	EmitterTickerContainer container;
	
	@Autowired 
	EmitterTickerFactory factory;
	
	public boolean isTickerAlreadyCreated(String ticker) {
		return container.isEmitterTicker(ticker);
	}
	
	public EmitterTicker getEmitterTicker(String ticker) {
		return container.getEmitterTicker(ticker);
	}
	
	public void createTickerDataContainer(String ticker) throws Exception {
		try {
			EmitterTicker newone = factory.createEmitterTicker(ticker);
			container.setEmitterTicker(ticker, newone);
		} catch (Exception e) {
			throw new Exception("something went wrong with the creation :(");
		}
	}
	
	public boolean areAnyEmitterTicker() {
		return container.getAlmacenSize() > 0;
	}
	
}
