package com.emanuelpontes.crmtest.api.customer.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.emanuelpontes.crmtest.api.customer.event.CustomerEvent;
import com.emanuelpontes.crmtest.api.integrations.crm.model.CustomerCRM;
import com.emanuelpontes.crmtest.api.integrations.crm.service.CustomerCrmService;
import com.emanuelpontes.crmtest.api.integrations.facade.ICrmIntegration;

@Component
public class CustomerEventHandler {

    @Autowired
    private ICrmIntegration crmService;
    
    @Async
	@EventListener
    public void handleCustomerEvent(CustomerEvent event) {
        crmService.patchCustomer(new CustomerCRM(event.getCustomer()));
    }
}
