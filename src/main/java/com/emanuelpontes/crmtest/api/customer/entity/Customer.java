package com.emanuelpontes.crmtest.api.customer.entity;

import com.emanuelpontes.crmtest.global.model.db.BaseModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="customer")
public class Customer extends BaseModel<Long> {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    public Address address;
}