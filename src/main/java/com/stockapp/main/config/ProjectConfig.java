package com.stockapp.main.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.net.ssl.SSLException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.stockapp.main.DTOs.AggregatesResult;
import com.stockapp.main.utils.EmittersContainer;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ProjectConfig {

	@Value("${apikey.value}")
	private String apikey;
	
	/**
     * La API permite solo 5 pedidos por minuto - Esta debilidad vamos a aprovecharla
     * Crearemos una funcionalidad donde se lleve constantemente un control de la cantidad de pedidos a la api, en particular cuando el usuario interactua.
     * Primero, debemos llevar variables estaticas en toda la api, averiguar como hacerlo, quizas en la sesion, no se.
     * Segundo, podemos aprovechar el uso de Filtros, antes de ingresar a los microservicios. Que se haga un control y se devuelva Excepcion especial.
     * Tercero, podrian ser variables presentes en el Gateway, tener ahi la cantidad de pedidos que cada rest endpoint realiza, y hacer el calculo.
     * Cuarto, filtro en el Gateway parece algo bueno.
     * Quinto, podriamos usar REdis!!! Aunque no se si reconoce la idea de transacciones, si no lo hace, usamos directamente la base de datos.
     * Digo transacciones porque el tema de concurrencia es muy importante para esto, si tengo dos usuarios, mismo pedido, pero que sumados llegarian al limite,
     * debo manejarlo como transaccion de forma atomica. 
     * Aparentemente se podria con Redis, hay que probarlo. Creo que seria más rápido que usar una base de datos
     * Ademas nos permite usarla para mostrar lo que sabemos!
     * Aunque puede llegar a ser mas complejo que eso, porque necesito actualizacion en tiempo real
     * Estoy pensando en crear un microservicio expresamente para el control, le pego a su endpoint y veo si es factible o no
     * Mientras tanto, tiene que tener un funcionamiento constante, en loop, controlando y midiendo el tiempo, cada 1 segundo
     * Al cumplirse ciertos factores actualizar Redis o la base de datos, veremos
     * En principio con la base seria algo 'simple', ojo que podriamos ir por ese lado
     * Podriamos incluso manejar casos de concurrencia rememorando lo que vimos en Hibernate
     * 
     * @return
     * @throws SSLException 
     */
    @Bean
    WebClient createWebClient() throws SSLException {
        SslContext sslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build();
        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        return WebClient.builder()
                .baseUrl("https://api.polygon.io") //https://polygon.io/
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer "+apikey) 
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
    
    /*
    
    @Bean
	Supplier<AggregatesResult> producerBinding() {
		return () -> {
			
			if (EmittersContainer.getAggResultsArr().isEmpty()) {
				return null;
			} else {
				int index = EmittersContainer.getIntAtomico().incrementAndGet();
				if (index < EmittersContainer.getAggResultsArr().size()) {
					AggregatesResult data = EmittersContainer.getAggResults(index);
					EmittersContainer.setAggresultsarrbuilding(data);
					return data;
				} else {
					return null;
				}
			}
			
		};
	}

    @Bean
    Consumer<AggregatesResult> consumerBinding() {
		//return s -> System.out.println("Data Consumed :: " + s);
    	
    	return agg -> {
    		if (agg != null) {
    			
    			EmittersContainer.getEmitters().forEach(emitter -> {
        			try {
    					emitter.send(SseEmitter.event().data(agg));
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					//e.printStackTrace();
    					System.out.println(e.getMessage());
    				}
        		});
    			
    		}
    		
    		
    	};
    	
	}
    */
}
