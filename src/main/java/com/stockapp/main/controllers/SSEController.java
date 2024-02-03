package com.stockapp.main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.stockapp.main.DTOs.Aggregates;
import com.stockapp.main.DTOs.AggregatesResult;

import reactor.core.publisher.Mono;

@Controller
public class SSEController {

	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	    
    private AtomicInteger intAtomico = new AtomicInteger(0);
    
    private AggregatesResult[] arrResults;
	
	@Autowired
    WebClient webClient;
	
	private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        // Handle client error (4xx) here
        // primero entramos aca, es como que ataja el error de http, y devolvemos un Mono, atajado al subscribir
            System.out.println("DENTRO DE HANDLE CLIENT ERROR");
            return Mono.error(new Exception("SOY LA EXCEPCION de client: "+clientResponse.statusCode())); // podriamos lanzar excepcion Custom
            //return Mono.error(new CustomClientException(clientResponse.statusCode()));
        }

        private Mono<? extends Throwable> handleServerError(ClientResponse serverResponse) {
            // Handle server error (5xx) here
            System.out.println("DENTRO DE HANDLE server ERROR");
            return Mono.error(new Exception("SOY LA EXCEPCION de server: "+serverResponse.statusCode()));
        }
	
	@GetMapping("/livegraph")
	public String graph(Model model) {
		
		//[1643121000000,158.98,162.76,157.02,159.78,115798400]
        // DATE - OPEN - HIGH - LOW - CLOSE - VOLUME
        
        ArrayList<Aggregates> listado = new ArrayList<>();
        
        Aggregates response = webClient
								.get()
								.uri("/v2/aggs/ticker/AAPL/range/1/day/2023-01-25/2024-01-25?adjusted=true&sort=asc")
								.retrieve()
                                .onStatus(status -> status.is4xxClientError(), clientResponse -> handleClientError(clientResponse))
                                .onStatus(status -> status.is5xxServerError(), serverResponse -> handleServerError(serverResponse))
                                //.onStatus(HttpStatus::isError, clientResponse -> handleAnyError(clientResponse))
                                .bodyToMono(Aggregates.class) //throws JsonProcessingException si pifiamos en el procesamiento en mi DTO
                                .block();
        
        
        this.arrResults = response.getResults();
        
        AggregatesResult inicial = this.arrResults[this.intAtomico.intValue()];
        
        //this.intAtomico.incrementAndGet();
        
        model.addAttribute("listadito", List.of(inicial));
        
        
        
        
        //model.addAttribute("listadito", response.getResults());
        model.addAttribute("name", "Peterrrr");
		
		return "Graph";
	}
	
	
	
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
            	//List<Long> arrDatos = List.of(1674536400000L, 100L, 102L, 98L, 101L, 60800355L);
                //emitter.send(SseEmitter.event().data(arrDatos));
            	
            	int newIndex = this.intAtomico.incrementAndGet();
            	
            	if (newIndex >= this.arrResults.length) {
            		emitter.complete();
            	} else {
                	emitter.send(SseEmitter.event().data(this.arrResults[this.intAtomico.get()]));
            	}
            	
            	
                
            } catch (Exception e) {
                // Handle exceptions (e.g., closed connections)
                System.out.println("EXCEPCIONNNNNN EN EVENTO 1");
                emitters.remove(emitter);
            }
        }
        
        return ResponseEntity.ok("Todo salio perfecto en la generacion de datos del Emitter 1");
    }
}
