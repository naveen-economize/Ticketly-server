package com.callsign.ticketly.repository;

import com.callsign.ticketly.entity.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, String> {

}
