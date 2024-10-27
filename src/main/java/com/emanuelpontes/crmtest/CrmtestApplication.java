package com.emanuelpontes.crmtest;

import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class CrmtestApplication {

	public static Logger log = LogManager.getLogger();
	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		final int size = 16 * 1024 * 1024;
	    final ExchangeStrategies strategies = ExchangeStrategies.builder()
	        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
	        .build();
	    return builder
	        .exchangeStrategies(strategies)
	        .build();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CrmtestApplication.class, args);
	}

	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}
}
