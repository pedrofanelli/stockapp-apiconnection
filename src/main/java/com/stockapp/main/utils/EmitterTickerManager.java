package com.stockapp.main.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stockapp.main.DTOs.AggregatesResult;
import com.stockapp.main.DTOs.ForKafka;

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
	
	public ForKafka loopAndAddNewItemData() throws Exception {
		if (container.getAlmacenSize() > 0) {
			ForKafka data = new ForKafka();
			Set<String> keys = container.keySet();
			
			keys.forEach(key -> {
				
				EmitterTicker emitterTicker = container.getEmitterTicker(key);
				int index = emitterTicker.getAndIncrementElementIndex();
				if (index < emitterTicker.getTotalArrAggResultSize()) {
					AggregatesResult agg = emitterTicker.getTotalArrAggResultItem(index);
					emitterTicker.setArrBuildingAggResultItem(agg);
					data.setData(key, agg);
				} else { //index superó tamaño de almacen total de data
					// no agregamos a DTO
				}
				
			});
			
			return data;
		} else {
			throw new Exception ("No hay datos para loopear :(");
		}
	}
	
}
