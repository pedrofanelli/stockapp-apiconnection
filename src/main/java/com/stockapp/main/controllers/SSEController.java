package com.stockapp.main.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.stockapp.main.DTOs.Aggregates;
import com.stockapp.main.DTOs.AggregatesResult;
import com.stockapp.main.utils.EmittersContainer;

import reactor.core.publisher.Mono;

@Controller
public class SSEController {

	private static final Logger logger = LoggerFactory.getLogger(SSEController.class);
	
	@Autowired
	EmittersContainer emittersContainer;
	
	//private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	    
    //private AtomicInteger intAtomico = new AtomicInteger(0);
    
    //private AggregatesResult[] arrResults;
	
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
	
	@GetMapping("/livegraph/{ticker}")
	public ResponseEntity<List<AggregatesResult>> graph(@PathVariable String ticker) {
		
		logger.info("el tickkkkker: "+ticker);
		
		List<AggregatesResult> lista = new ArrayList<>();
		
		if (emittersContainer.getAggResultsArr().isEmpty()) {
			
			logger.info("array contenedor VACIO!!!");
			
			Aggregates response = webClient
					.get()
					.uri("/v2/aggs/ticker/AAPL/range/1/day/2023-01-25/2024-01-25?adjusted=true&sort=asc")
					.retrieve()
                    .onStatus(status -> status.is4xxClientError(), clientResponse -> handleClientError(clientResponse))
                    .onStatus(status -> status.is5xxServerError(), serverResponse -> handleServerError(serverResponse))
                    //.onStatus(HttpStatus::isError, clientResponse -> handleAnyError(clientResponse))
                    .bodyToMono(Aggregates.class) //throws JsonProcessingException si pifiamos en el procesamiento en mi DTO
                    .block();
			
			
				
			List<AggregatesResult> listado = Arrays.asList(response.getResults());
			
			emittersContainer.setAllAggResult(listado);
			
			lista.add(listado.get(0));
							
		} else {
			
			logger.info("array contenedor CON DATAAAA!!!");
			
			lista = emittersContainer.getAggresultsarrbuilding();
			
		}
        
        
        //AggregatesResult inicial = EmittersContainer.getAggResults(this.intAtomico.intValue());
        
        //this.intAtomico.incrementAndGet();
        
        //model.addAttribute("listadito", lista);
        
        
        //System.out.println(lista.get(0));
        
        //model.addAttribute("listadito", response.getResults());
        //model.addAttribute("name", "Peterrrr");
		
		return ResponseEntity.ok(lista);
	}
	
	
	
	/**
	 * Tendría que generar CustomEmitters, dependiendo del ticker seleccionado, dando info live de ese ticker
	 * Es decir, habrá 1 tipo de emitter por ticker. Luego podrán haber varios emitters del mismo tipo, para varios clientes.
	 * Luego, Kafka va a loopear por 'almacenes de datos', habrá un almacen por ticker.
	 * Si esta vacio significa que ningun usuario pidio info live.
	 * Si tiene datos, devolverá el primero segun index, misma lógica de ahora.
	 * El objeto que devolverá será un hashMap, key=ticker value=agg
	 * El consumer recibirá la info en otro microservicio.
	 * Loopeará los emitters, en función del tipo de emitter (=ticker) proveerá datos nuevos o no.
	 * 
	 * 
	 * @return
	 */
	@GetMapping("/emitter")
	SseEmitter getEmitter() {
		
		System.out.println("ADENTRO DE SSE!!!");
        System.out.println("TAMANIO DE ARRAY DE EMITERS: "+emittersContainer.getEmitters().size());
		
		SseEmitter emitter = new SseEmitter(-1L);
        
        
        // Add the emitter to the list for tracking
        emittersContainer.setEmitter(emitter);

        // Set a timeout handler for the emitter
        emitter.onTimeout(() -> {
            // Remove the emitter from the list when the connection times out
            System.out.println("TIMEOUT DE EMITTER");
            emittersContainer.removeEmitter(emitter);
        });

        // Set a completion handler for the emitter
        emitter.onCompletion(() -> {
            // Remove the emitter from the list when the connection is completed
            System.out.println("COMPLETION DEL EMITTER");
            emittersContainer.removeEmitter(emitter);
        });

        // Logic to manage the connection and send events
        //sendEventsInBackground(emitter);

        
        return emitter;
		
	}
 
	
	/*
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
    */
}
