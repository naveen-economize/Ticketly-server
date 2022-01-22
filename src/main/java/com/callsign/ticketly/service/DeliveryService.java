package com.callsign.ticketly.service;

import com.callsign.ticketly.constants.DeliveryStatus;
import com.callsign.ticketly.dto.DeliveryIn;
import com.callsign.ticketly.entity.Customer;
import com.callsign.ticketly.entity.Delivery;
import com.callsign.ticketly.entity.Restaurant;
import com.callsign.ticketly.repository.CustomerRepository;
import com.callsign.ticketly.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public String storeDelivery(DeliveryIn deliveryDto) throws Exception {
        Delivery delivery = new Delivery();
        delivery.setCurrentDistanceFromDestinationInMetres(deliveryDto.getCurrentDistanceFromDestinationInMetres());
        delivery.setExpectedDeliveryTime(deliveryDto.getExpectedDeliveryTime());
        delivery.setCustomerID(deliveryDto.getCustomerID());
        delivery.setRestaurantID(deliveryDto.getRestaurantID());
        delivery.setDeliveryStatus(DeliveryStatus.received);
        Delivery responseDelivery = deliveryRepository.save(delivery);
        return responseDelivery.getId();
    }
}
