package com.emanuelpontes.crmtest.api.customer.entity;

import com.emanuelpontes.crmtest.global.model.db.BaseModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="address")
public class Address extends BaseModel<Long> {
    public String street;
    public String city;
    public String state;
    public String zipCode;


    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", this.street, this.city, this.state, this.zipCode);
    }

}
