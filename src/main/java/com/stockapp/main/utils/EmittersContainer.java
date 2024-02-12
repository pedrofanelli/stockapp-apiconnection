package com.stockapp.main.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.stockapp.main.DTOs.AggregatesResult;

@Component
public class EmittersContainer {

	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	private final List<AggregatesResult> aggResultsArr = new CopyOnWriteArrayList<>();
	
	private final List<AggregatesResult> aggResultsArrBuilding = new CopyOnWriteArrayList<>();

	private AtomicInteger intAtomico = new AtomicInteger(-1);
	
	/**
	 * @return the emitters
	 */
	public List<SseEmitter> getEmitters() {
		return emitters;
	}
	
	public void setEmitter(SseEmitter emitter) {
		emitters.add(emitter);
	}
	
	public void removeEmitter(SseEmitter emitter) {
		emitters.remove(emitter);
	}

	/**
	 * @return the aggresultsarr
	 */
	public List<AggregatesResult> getAggResultsArr() {
		return aggResultsArr;
	}
	
	public AggregatesResult getAggResults(int i) {
		return aggResultsArr.get(i);
	}

	public void setAllAggResult(List<AggregatesResult> agg) {
		aggResultsArr.addAll(agg);
	}
	
	/**
	 * @return the intAtomico
	 */
	public AtomicInteger getIntAtomico() {
		return intAtomico;
	}

	/**
	 * @param intAtomico the intAtomico to set
	 */
	public void setIntAtomico(AtomicInteger intAtomico) {
		intAtomico = intAtomico;
	}

	public List<AggregatesResult> getAggresultsarrbuilding() {
		return aggResultsArrBuilding;
	}
	
	public void setAggresultsarrbuilding(AggregatesResult agg) {
		aggResultsArrBuilding.add(agg);
	}
	
	
}
