package com.callsign.ticketly.dto;

import com.callsign.ticketly.constants.CustomerType;
import lombok.Data;

@Data
public class CustomerLoginOut {
    private String Id;
    private String jwtToken;
    private CustomerType customerType;
    private String name;
    private String email;
    private String phoneNumber;
}
