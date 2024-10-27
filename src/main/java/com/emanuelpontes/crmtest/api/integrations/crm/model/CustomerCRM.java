package com.emanuelpontes.crmtest.api.integrations.crm.model;

import com.emanuelpontes.crmtest.api.customer.entity.Customer;
import com.emanuelpontes.crmtest.global.model.db.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCRM {
    
    private Long customerId;
    private String fullName;           
    private String contactEmail;       
    private String primaryPhone;       
    private String location;     
    
    public CustomerCRM(Customer customer) {
        this.customerId = customer.getId();
        this.fullName = customer.getFirstName() + " " + customer.getLastName();
        this.contactEmail = customer.getEmail();
        this.primaryPhone = customer.getPhoneNumber();
        this.location = customer.getAddress().toString();
    }
}

