package com.stockapp.main.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.net.ssl.SSLException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.stockapp.main.DTOs.ForKafka;
import com.stockapp.main.utils.EmitterTickerManager;
import com.stockapp.main.utils.EmittersContainer;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ProjectConfig {

	@Value("${apikey.value}")
	private String apikey;
	
	//@Autowired
	//private EmittersContainer emittersContainer;
	
	@Autowired
	private EmitterTickerManager emitterManager;
	
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
    
     
    /**
     * Tendria que tener Emitters de un cierto ticker, y resultados de un cierto ticker
     * Podría crear una clase que centralice a todos
     * Esa clase tendra todos los arrays de emiters/resultados de cada ticker
     * Podría hacer depender todo con parámetros que sea el nombre del ticker.
     * Primero necesito un listado de tickers validos, y luego, desde el controller que recibe
     * el pedido del front, analizará el parámetro, y lo usará para crear los arrays en esta 
     * clase general
     * @return
     */
    
    /*
    @Bean
	Supplier<AggregatesResult> producerBinding() {
		return () -> {
			
			System.out.println("PRIMER PASO EN KAFKA PRODUCER");
			AggregatesResult emptyData = new AggregatesResult();
			emptyData.setAllToEmpty();
			if (emittersContainer.getAggResultsArr().isEmpty()) {
				System.out.println("ES NULL EL CONTENIDO, ENVIO NULL");
				
				return emptyData;
			} else {
				System.out.println("NO ES NULL");
				int index = emittersContainer.getIntAtomico().incrementAndGet();
				if (index < emittersContainer.getAggResultsArr().size()) {
					AggregatesResult data = emittersContainer.getAggResults(index);
					emittersContainer.setAggresultsarrbuilding(data);
					return data;
				} else {
					return emptyData;
				}
			}	
			
		};
	}

	*/
    
    @Bean
	Supplier<ForKafka> producerBinding() {
		return () -> {
			
			try {
			
				ForKafka emptyData = new ForKafka();
				if (!emitterManager.areAnyEmitterTicker()) {				
					return emptyData;
				} else {
					ForKafka data = emitterManager.loopAndAddNewItemData();
					return data;
				}	
			
			} catch (Exception e) {
				System.out.println("Ha ocurrido un error en Kafka Producer: "+e.getMessage());
				return new ForKafka();
			}
			
			
		};
	}
    
    /*
    
    @Bean
    Consumer<AggregatesResult> consumerBinding() {
		//return s -> System.out.println("Data Consumed :: " + s);
    	
    	return agg -> {
    		
    		if (agg != null && !agg.isAllEmpty()) {
    			System.out.println("ES DISTINTO DE NULL EN CONSUMERRRRR");
    			emittersContainer.getEmitters().forEach(emitter -> {
        			try {
    					emitter.send(SseEmitter.event().data(agg));
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					//e.printStackTrace();
    					System.out.println(e.getMessage());
    				}
        		});
    			
    		} else {
    			System.out.println("es null en consumerrrrr");
    		}
    		
    	};
	}
    
    */
    
    
    @Bean
    Consumer<ForKafka> consumerBinding() {
		//return s -> System.out.println("Data Consumed :: " + s);
    	
    	return forKafka -> {
    		
    		if (forKafka != null && !forKafka.isEmpty()) {
    			
    			Set<String> keys = forKafka.keySet();
    			
    			keys.forEach(key -> {
    				
    				try {
    					
    					AggregatesResult agg = forKafka.getAgg(key);
        				
        				emitterManager.sendDataToEmitters(key, agg);
    					
    				} catch (Exception e) {
    					System.out.println("Error mandando data al emitter en Kafka Consumer: "+e.getMessage());
    				}
    				
    			});
    			
    		} else {
    			System.out.println("Se ha recibido un objeto vacío en Kafka Consumer");
    		}
    		
    	};
	}
    
    
}
