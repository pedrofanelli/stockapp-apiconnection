package com.stockapp.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.stockapp.main.DTOs.OpenClose;

import reactor.core.publisher.Mono;

@RestController
public class MainController {

	@Autowired
    WebClient webClient;
	
	@Value("${test.test}")
	private String test;
    
    @GetMapping("/testing")
    public String test() {
    	
    	System.out.println(test);
    	
    	/**
         * La idea es crear DTOs para cada respuesta, entonces el objeto es creado de forma automática y podemos manejarlo al obtenerlo.
         * Si sucediera algun error en el pedido, caería en el onStatus() y el DTO no se veria afectado
         * Si hubieran errores en la deserialización, en el .bodyToMono(), se ataja con try-catch
         */
    	
    	/*
    	Mono<OpenClose> response = webClient
				.get()
				//.uri("/v2/aggs/ticker/AAPL/range/1/day/2023-01-09/2023-01-09?adjusted=true&sort=desc&limit=120")
                                //.uri("/v3/reference/tickers?active=true")
                                .uri("/v1/open-close/AAPL/2024-01-23?adjusted=true")
                                //.uri("/v2/reference/news?ticker=AAPL&order=desc&limit=3&sort=published_utc")
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError(), clientResponse -> handleClientError(clientResponse))
                                .onStatus(status -> status.is5xxServerError(), serverResponse -> handleServerError(serverResponse))
                                //.onStatus(HttpStatus::isError, clientResponse -> handleAnyError(clientResponse))
				.bodyToMono(OpenClose.class) //throws JsonProcessingException si pifiamos en el procesamiento en mi DTO
                                .log();
                                
        */
        /*
        response.subscribe(dto -> {
            System.out.println("RESPUESTA! : "+dto);
            System.out.println("Inner property COUNT: "+dto.getCount());
            NewsResult result1 = dto.getResults()[0];
            System.out.println("NEWS 1 - TITLE: "+result1.getTitle());
            System.out.println("NEWS 1 - AUTHOR: "+result1.getAuthor());
            System.out.println("NEWS 1 - DESCRIPTION: "+result1.getDescription());
            System.out.println("NEWS 1 - IMG URL: "+result1.getImage_url());
            System.out.println("NEWS 1 - article URL: "+result1.getArticle_url());
                    },
                            err -> System.out.println("ERROR!!!! ::: "+err.getMessage())); //despues aca
    	*/
    	/*
    	response.subscribe(
    			dto -> {
    				System.out.println("RESPUESTA! : "+dto);
    			},
    			err -> System.out.println("ERROR!!!! ::: "+err.getMessage())); //despues aca);
    	*/
        return "Testing WebClient with API!";
    }
    
    
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
        
        /*
            private Mono<? extends Throwable> handleAnyError(ClientResponse clientResponse) {
                // Handle any error here
                return Mono.error(new CustomException(clientResponse.statusCode()));
            }
        */
}
