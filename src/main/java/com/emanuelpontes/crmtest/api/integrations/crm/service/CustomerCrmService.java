package com.emanuelpontes.crmtest.api.integrations.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerErrorException;

import com.emanuelpontes.crmtest.api.integrations.crm.model.CustomerCRM;
import com.emanuelpontes.crmtest.api.integrations.facade.ICrmIntegration;
import com.emanuelpontes.crmtest.global.erros.AplicationException;
import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;


@Service
public class CustomerCrmService implements ICrmIntegration{

    private final static Logger log = LoggerFactory.getLogger(CustomerCrmService.class);

    @Autowired
	private WebClient webClient;

    @Value("${crm.integration.base.url}")
    private String baseUrl;


    public void patchCustomer(CustomerCRM customer) {
        String url = String.format("%s/crm/customer", this.baseUrl);
        this.webClient
        .put()
        .uri(url)
        .body(BodyInserters.fromValue(customer))
        .retrieve()
        .onStatus(t -> t.is4xxClientError(), clientResponse -> Mono.error(new AplicationException("Client error code: "+ clientResponse.statusCode())))
        .onStatus(t -> t.is5xxServerError(), clientResponse -> Mono.error(new AplicationException("Server error code: " + clientResponse.statusCode())))
        .bodyToMono(CustomerCRM.class)
        .subscribe(
            c -> {log.info("Customer CRM created successfully: {}", c.getCustomerId());});

    }
}
