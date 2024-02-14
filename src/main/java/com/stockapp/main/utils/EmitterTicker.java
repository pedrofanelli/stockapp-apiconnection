package com.stockapp.main.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.stockapp.main.DTOs.AggregatesResult;

public class EmitterTicker {

	private final String tickerNameId;
	
	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	private final List<AggregatesResult> aggResultsArr = new CopyOnWriteArrayList<>();
	
	private final List<AggregatesResult> aggResultsArrBuilding = new CopyOnWriteArrayList<>();

	private AtomicInteger elementIndex = new AtomicInteger(-1);
	
	public EmitterTicker(String ticker) {
		this.tickerNameId = ticker;
	}
	
	public String getTickerNameId() {
		return this.tickerNameId;
	}
	public void setEmitter(SseEmitter emitter) {
		emitters.add(emitter);
	}
	public void removeEmitter(SseEmitter emitter) {
		emitters.remove(emitter);
	}
	public SseEmitter getEmitter(int i) throws Exception {
		if (this.emitters.size() - 1 < i) {
			throw new Exception("errror");
		}
		return this.emitters.get(i);
	}
	
}
