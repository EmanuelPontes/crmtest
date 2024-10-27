package com.emanuelpontes.crmtest.api.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.emanuelpontes.crmtest.api.customer.entity.Customer;
import com.emanuelpontes.crmtest.api.customer.event.CustomerDeleteEvent;
import com.emanuelpontes.crmtest.api.customer.event.CustomerEvent;
import com.emanuelpontes.crmtest.api.customer.repository.CustomerRepository;
import com.emanuelpontes.crmtest.global.service.ServiceCrudBase;

@Service
public class CustomerService extends ServiceCrudBase<CustomerRepository, Customer, Long> {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    @Override
    public void validateToDelete(Customer entidade) {
       return;
    }

    @Override
    public Customer prePersistency(Customer entity) {
        // TODO Auto-generated method stub
        return entity;
    }

    @Override
    public void triggerChangeEvent(Customer entity) {
       this.applicationEventPublisher.publishEvent(new CustomerEvent(this, entity));
    }

    @Override
    public void triggerDeleteEvent(Long id) {
        this.applicationEventPublisher.publishEvent(new CustomerDeleteEvent(this, id));
    }


    
}
