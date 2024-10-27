package com.emanuelpontes.crmtest.api.customer.event;

import org.springframework.context.ApplicationEvent;

import com.emanuelpontes.crmtest.api.customer.entity.Customer;

import lombok.Data;

@Data
public class CustomerEvent extends ApplicationEvent {
    private Customer customer;

    public CustomerEvent(Object source, final Customer customer) {
        super(source);
        this.customer = customer;
    }
    
}
