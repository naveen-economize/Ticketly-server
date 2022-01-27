package com.callsign.ticketly.dto;

import com.callsign.ticketly.constants.CustomerType;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerIn implements Serializable {
    private CustomerType customerType;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
