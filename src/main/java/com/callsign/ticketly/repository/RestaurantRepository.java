package com.callsign.ticketly.repository;

import com.callsign.ticketly.entity.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {
}
