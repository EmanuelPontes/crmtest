package com.emanuelpontes.crmtest.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.emanuelpontes.crmtest.global.erros.AplicationException;

import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class WebClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);

    // Filtro para logar requisições
    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> 
                values.forEach(value -> logger.info("{}: {}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    // Filtro para logar respostas
    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            logger.info("Response status: {}", clientResponse.statusCode());
            clientResponse.headers().asHttpHeaders().forEach((name, values) -> 
                values.forEach(value -> logger.info("{}: {}", name, value)));
            return Mono.just(clientResponse);
        });
    }

    public static ExchangeFilterFunction logError() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (clientResponse.statusCode().isError()) {
                return clientResponse.bodyToMono(String.class)
                        .defaultIfEmpty("No response body")
                        .flatMap(errorBody -> {
                            logger.error("Error status: {}, body: {}", clientResponse.statusCode(), errorBody);
                            return Mono.just(clientResponse);
                        });
            }
            return Mono.just(clientResponse);
        });
    }

    public static WebClient createWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .filter(logRequest())    
                .filter(logResponse())   
                .filter(logError())
                .build();
    }

    public static Retry retrySpec() {
        return Retry.backoff(3, Duration.ofSeconds(1))
                .filter(throwable -> throwable instanceof RuntimeException)  
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> 
                    new AplicationException("The number of retries have been exhausted"));
    }
}
