package com.callsign.ticketly.dto;

import com.callsign.ticketly.constants.DeliveryStatus;
import lombok.Data;

@Data
public class DeliveryPatchIn {

    private String deliveryID;
    private int currentDistanceFromDestinationInMetres;
    private DeliveryStatus deliveryStatus;
}
