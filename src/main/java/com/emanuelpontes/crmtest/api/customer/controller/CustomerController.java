package com.emanuelpontes.crmtest.api.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emanuelpontes.crmtest.api.customer.entity.Customer;
import com.emanuelpontes.crmtest.api.customer.service.CustomerService;
import com.emanuelpontes.crmtest.global.controller.ControllerCrudBase;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends ControllerCrudBase<CustomerService, Customer, Long>{
    
}
