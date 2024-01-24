package com.stockapp.main.config;

import javax.net.ssl.SSLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ProjectConfig {

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
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer W4pZlgIpUhW9DQLkaNlO_FHqm1zuDa75") //inmastockapp16
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
