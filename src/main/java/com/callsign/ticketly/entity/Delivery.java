package com.callsign.ticketly.entity;

import com.callsign.ticketly.constants.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private DeliveryStatus deliveryStatus;
    @Column(name = "expected_delivery_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime expectedDeliveryTime;
    @Column(name = "current_distance_from_destination_metres")
    private Integer currentDistanceFromDestinationInMetres;
    @Column(name = "customer_id", nullable = false)
    private String customerID;
    @Column(name = "restaurant_id", nullable = false)
    private String restaurantID;
    @Column(name = "ticket_id")
    private String ticketID;
    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable=false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "restaurant_id", insertable = false, updatable=false)
    private Restaurant restaurant;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", insertable = false, updatable=false)
    private Ticket ticket;
}
