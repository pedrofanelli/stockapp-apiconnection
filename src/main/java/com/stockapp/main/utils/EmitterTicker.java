package com.stockapp.main.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.stockapp.main.DTOs.AggregatesResult;

public class EmitterTicker {

	private final String tickerNameId;
	
	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	private final List<AggregatesResult> totalAggResultsArr = new CopyOnWriteArrayList<>();
	
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
	public int getElementIndex() {
		return this.elementIndex.get();
	}
	public void setIntAtomico(int val) {
		this.elementIndex.set(val);
	}
	
	public int getTotalArrAggResultSize() {
		return this.totalAggResultsArr.size();
	}
	public AggregatesResult getTotalArrAggResultItem(int i) {
		return totalAggResultsArr.get(i);
	}
	public void setAllTotalArrAggResult(List<AggregatesResult> agg) {
		totalAggResultsArr.addAll(agg);
	}
	
	
	public int getArrBuildingAggResultSize() {
		return this.aggResultsArrBuilding.size();
	}
	public AggregatesResult getArrBuildingAggResultItem(int i) {
		return this.aggResultsArrBuilding.get(i);
	}
	
	public void setArrBuildingAggResultItem(AggregatesResult agg) {
		this.aggResultsArrBuilding.add(agg);
	}
	
}
