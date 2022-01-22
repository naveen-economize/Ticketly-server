package com.callsign.ticketly.entity;

import com.callsign.ticketly.constants.CustomerType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;

@Data
@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @Column(name = "customer_type")
    private CustomerType customerType;
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;

    public Customer() {}

    public Customer(String customerID) {
        this.id = customerID;
    }
}
