package com.emanuelpontes.crmtest.api.integrations.crm.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerErrorException;

import com.emanuelpontes.crmtest.api.customer.entity.Customer;
import com.emanuelpontes.crmtest.api.customer.repository.CustomerRepository;
import com.emanuelpontes.crmtest.api.integrations.crm.model.CustomerCRM;
import com.emanuelpontes.crmtest.api.integrations.facade.ICrmIntegration;
import com.emanuelpontes.crmtest.config.WebClientConfig;
import com.emanuelpontes.crmtest.global.erros.AplicationException;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


@Service
public class CustomerCrmService implements ICrmIntegration{

    private final static Logger log = LoggerFactory.getLogger(CustomerCrmService.class);

    private final WebClient webClient;

    private LocalDateTime lastSyncTime = LocalDateTime.now().minusDays(1);

    @Autowired
    private CustomerRepository customerRepository;
   

    @Value("${crm.integration.base.url}")
    private String baseUrl;


    public CustomerCrmService() {
        this.webClient = WebClientConfig.createWebClient(baseUrl);
    }

    public void patchCustomer(CustomerCRM customer) {
        String url = String.format("%s/crm/customer", this.baseUrl);
        this.webClient
        .put()
        .uri(url)
        .body(BodyInserters.fromValue(customer))
        .retrieve()
        .bodyToMono(CustomerCRM.class)
        .onErrorResume(Mono::error)
        .retryWhen(WebClientConfig.retrySpec())
        .subscribe(
            c -> {
                log.info("Customer CRM created successfully id: {}", c.getCustomerId());
                Customer customerToUpdate = this.customerRepository.findById(c.getCustomerId()).orElse(null);
                if (null != customerToUpdate) {
                    this.customerRepository.save(customerToUpdate);
                }
                
            }, e -> {
                log.error(e.getMessage());
            });

    }

    @Override
    public void deleteCustomer(Long id) {
        String url = String.format("%s/crm/customer/{id}", this.baseUrl);
        this.webClient
        .delete()
        .uri(url,id)
        .retrieve()
        .toBodilessEntity()
        .onErrorResume(Mono::error)
        .retryWhen(WebClientConfig.retrySpec())
        .onErrorResume(RuntimeException.class, 
        		e -> {
        			log.error(e.getMessage());	
        			return Mono.empty();
        		})
        .subscribe(
            c -> {
                log.info("Customer CRM deleted successfully id: {}");
                
            }, e -> {
                log.error(e.getMessage());
            });
    }

    @Override
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void syncData() {
        log.info("Syncing data started at: {}", LocalDateTime.now());
        List<Customer> customers = this.customerRepository.findByUpdatedAtAfter(this.lastSyncTime);

        if (!CollectionUtils.isEmpty(customers)) {
             Flux.fromIterable(customers)
                    .flatMap(this::sendToSync)
                    .doOnComplete(() -> {
                        lastSyncTime = LocalDateTime.now();  // Atualiza a última data de sincronização
                        log.info("Sync completed.");
                    })
                    .subscribe(
                            c -> {
                                log.info("Customer syncronized: {}", c.getCustomerId());
                                Customer customerToUpdate = this.customerRepository.findById(c.getCustomerId()).orElse(null);
                                if (null != customerToUpdate) {
                                    customerToUpdate.setUpdatedAt(lastSyncTime);
                                    this.customerRepository.save(customerToUpdate);
                                }
                            },
                            e -> log.error("Error while syncing ", e)
                    );
        } else {
            log.info("No customers founded for syncing");
        }
    }

    private Mono<CustomerCRM> sendToSync(Customer customer) {
        String url = String.format("%s/crm/customer", this.baseUrl);
        return this.webClient
        .put()
        .uri(url)
        .body(BodyInserters.fromValue(new CustomerCRM(customer)))
        .retrieve()
        .bodyToMono(CustomerCRM.class)
        .doOnError(e -> log.error("Erro while syncing ", e));
    }
}
