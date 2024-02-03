package com.stockapp.main.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SSEController {

	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	@GetMapping("/emitter")
	SseEmitter getEmitter() {
		
		System.out.println("ADENTRO DE SSE!!!");
        System.out.println("TAMANIO DE ARRAY DE EMITERS: "+emitters.size());
		
		SseEmitter emitter = new SseEmitter(-1L);
        
        
        // Add the emitter to the list for tracking
        emitters.add(emitter);

        // Set a timeout handler for the emitter
        emitter.onTimeout(() -> {
            // Remove the emitter from the list when the connection times out
            System.out.println("TIMEOUT DE EMITTER");
            emitters.remove(emitter);
        });

        // Set a completion handler for the emitter
        emitter.onCompletion(() -> {
            // Remove the emitter from the list when the connection is completed
            System.out.println("COMPLETION DEL EMITTER");
            emitters.remove(emitter);
        });

        // Logic to manage the connection and send events
        //sendEventsInBackground(emitter);

        
        return emitter;
		
	}
 
	
	@GetMapping("/generardatos/emitter1")
    public ResponseEntity<String> evento1() {
        
		//OLD 25 DE ENERO 2023: [1674622800000, 140.89, 142.43, 138.81, 141.86, 65799348]
		
		//NEW 25 DE ENERO 2024: [1706158800000, 195.22, 196.2675, 193.1125, 194.17, 54822128]
		
		// wednesday 24 de enero: 1674536400000
        
        for (SseEmitter emitter : emitters) {
            try {
            	List<Long> arrDatos = List.of(1674536400000L, 100L, 102L, 98L, 101L, 60800355L);
                emitter.send(SseEmitter.event().data(arrDatos));
                
            } catch (Exception e) {
                // Handle exceptions (e.g., closed connections)
                System.out.println("EXCEPCIONNNNNN EN EVENTO 1");
                emitters.remove(emitter);
            }
        }
        
        return ResponseEntity.ok("Todo salio perfecto en la generacion de datos del Emitter 1");
    }
}
