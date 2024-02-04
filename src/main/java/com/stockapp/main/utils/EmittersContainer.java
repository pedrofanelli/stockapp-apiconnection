package com.stockapp.main.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.stockapp.main.DTOs.AggregatesResult;

@Component
public class EmittersContainer {

	private static final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	private static final List<AggregatesResult> aggResultsArr = new CopyOnWriteArrayList<>();
	
	private static final List<AggregatesResult> aggResultsArrBuilding = new CopyOnWriteArrayList<>();

	private static AtomicInteger intAtomico = new AtomicInteger(-1);
	
	/**
	 * @return the emitters
	 */
	public static List<SseEmitter> getEmitters() {
		return emitters;
	}
	
	public static void setEmitter(SseEmitter emitter) {
		emitters.add(emitter);
	}
	
	public static void removeEmitter(SseEmitter emitter) {
		emitters.remove(emitter);
	}

	/**
	 * @return the aggresultsarr
	 */
	public static List<AggregatesResult> getAggResultsArr() {
		return aggResultsArr;
	}
	
	public static AggregatesResult getAggResults(int i) {
		return aggResultsArr.get(i);
	}

	public static void setAllAggResult(List<AggregatesResult> agg) {
		aggResultsArr.addAll(agg);
	}
	
	/**
	 * @return the intAtomico
	 */
	public static AtomicInteger getIntAtomico() {
		return intAtomico;
	}

	/**
	 * @param intAtomico the intAtomico to set
	 */
	public static void setIntAtomico(AtomicInteger intAtomico) {
		EmittersContainer.intAtomico = intAtomico;
	}

	public static List<AggregatesResult> getAggresultsarrbuilding() {
		return aggResultsArrBuilding;
	}
	
	public static void setAggresultsarrbuilding(AggregatesResult agg) {
		aggResultsArrBuilding.add(agg);
	}
	
	
}
