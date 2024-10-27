package com.emanuelpontes.crmtest.api.customer.event;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class CustomerDeleteEvent extends ApplicationEvent{
    private Long id;
    
    public CustomerDeleteEvent(Object source, Long id) {
        super(source);
        this.id = id;
    }
    
}
