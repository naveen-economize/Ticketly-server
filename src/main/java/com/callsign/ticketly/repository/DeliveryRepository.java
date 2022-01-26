package com.callsign.ticketly.repository;

import com.callsign.ticketly.entity.Delivery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, String> {
    // select * from delivery where delivery_status != '3' and expected_delivery_time < time.now();
    @Query("SELECT d FROM Delivery d where d.deliveryStatus != '3' and d.expectedDeliveryTime < ?1")
    List<Delivery> getExpiredDeliveries(LocalDateTime time);
}
