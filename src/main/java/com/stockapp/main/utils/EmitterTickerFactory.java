package com.stockapp.main.utils;

import org.springframework.stereotype.Component;

@Component
public class EmitterTickerFactory {

	// constructor will not be necesary
	
	
	public EmitterTicker createEmitterTicker(String ticker) {
		
		return new EmitterTicker(ticker);
		
	}
	
	
	
}
