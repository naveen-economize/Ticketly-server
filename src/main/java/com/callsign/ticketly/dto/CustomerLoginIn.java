package com.callsign.ticketly.dto;

import lombok.Data;

@Data
public class CustomerLoginIn {
    private String email;
    private String password;
}
