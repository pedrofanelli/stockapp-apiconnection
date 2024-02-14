package com.stockapp.main.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmitterTickerFactory {

	// constructor will not be necesary
	
	@Autowired
	EmitterTickerContainer emitterTickerContainer;
	
	public EmitterTicker createEmitterTicker(String ticker) {
		
		// check if the ticker already exists
		
	}
	
}
