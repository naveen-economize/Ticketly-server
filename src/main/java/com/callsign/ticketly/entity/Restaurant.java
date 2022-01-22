package com.callsign.ticketly.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Restaurant implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String name;
    @Column(name = "mean_time_to_prepare_food")
    private Integer meanTimeToPrepareFood;
    private String location;

    public Restaurant() {}

    public Restaurant(String restaurantId) {
        this.id = restaurantId;
    }
}
