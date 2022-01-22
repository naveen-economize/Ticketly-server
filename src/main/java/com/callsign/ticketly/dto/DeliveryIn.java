package com.callsign.ticketly.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryIn {

    private String customerID;
    private String restaurantID;
    private LocalDateTime expectedDeliveryTime;
    private Integer currentDistanceFromDestinationInMetres;
}
