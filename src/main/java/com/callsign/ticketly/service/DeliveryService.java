package com.callsign.ticketly.service;

import com.callsign.ticketly.constants.DeliveryStatus;
import com.callsign.ticketly.dto.DeliveryIn;
import com.callsign.ticketly.dto.DeliveryPatchIn;
import com.callsign.ticketly.entity.Customer;
import com.callsign.ticketly.entity.Delivery;
import com.callsign.ticketly.entity.Restaurant;
import com.callsign.ticketly.repository.CustomerRepository;
import com.callsign.ticketly.repository.DeliveryRepository;
import com.callsign.ticketly.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private RedisService redisService;
    @Autowired
    TicketService ticketService;
    private int counter = 0;

    public Delivery getDelivery(String deliveryID) throws Exception {
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(deliveryID);
        if(deliveryOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid deliveryID");
        }
        return deliveryOptional.get();
    }

    public Delivery storeDelivery(DeliveryIn deliveryDto) throws Exception {
        Optional<Customer> customerOptional = customerRepository.findById(deliveryDto.getCustomerID());
        if(customerOptional.isEmpty()){
            throw new DataIntegrityViolationException("Invalid customerID");
        }
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(deliveryDto.getRestaurantID());
        if(restaurantOptional.isEmpty()){
            throw new DataIntegrityViolationException("Invalid restaurantID");
        }
        if(deliveryDto.getCurrentDistanceFromDestinationInMetres() <= 0) {
            throw new IllegalArgumentException("Invalid current distance from destination in metres");
        }
        if(deliveryDto.getExpectedDeliveryTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Invalid expected delivery time");
        }
        Delivery delivery = new Delivery();
        delivery.setCurrentDistanceFromDestinationInMetres(deliveryDto.getCurrentDistanceFromDestinationInMetres());
        delivery.setExpectedDeliveryTime(deliveryDto.getExpectedDeliveryTime());
        delivery.setCustomerID(deliveryDto.getCustomerID());
        delivery.setRestaurantID(deliveryDto.getRestaurantID());
        delivery.setDeliveryStatus(DeliveryStatus.received);
        return deliveryRepository.save(delivery);
    }

    public Delivery patchDelivery(DeliveryPatchIn deliveryPatchIn) throws Exception {
        if(deliveryPatchIn.getCurrentDistanceFromDestinationInMetres() <= 0) {
            throw new IllegalArgumentException("Invalid current distance from destination in metres");
        }
        Optional<Delivery> deliveryOptional = deliveryRepository.findById(deliveryPatchIn.getDeliveryID());
        if(deliveryOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid deliveryID");
        }
        Delivery delivery = deliveryOptional.get();
        delivery.setCurrentDistanceFromDestinationInMetres(deliveryPatchIn.getCurrentDistanceFromDestinationInMetres());
        boolean hasDeliveryStatusChanged = false;
        if( deliveryPatchIn.getDeliveryStatus() != null && !delivery.getDeliveryStatus().equals(deliveryPatchIn.getDeliveryStatus())) {
            hasDeliveryStatusChanged = true;
            delivery.setDeliveryStatus(deliveryPatchIn.getDeliveryStatus());
        }
        delivery = deliveryRepository.save(delivery);
        DeliveryStatus deliveryStatus = delivery.getDeliveryStatus();
        if(deliveryStatus == DeliveryStatus.picked || deliveryStatus == DeliveryStatus.preparing) {
            ticketService.createTicketIfNeededForPreparingAndPickedDeliveryStatus(delivery, hasDeliveryStatusChanged);
        }
        return delivery;
    }

//    @Scheduled(fixedDelay = 1000)
//    public void treatDeliveries() {
//        new Thread(()->{
//            redisService.lock();
//            System.out.println("The task1 is executed");
//            try {
//                redisService.queuePush("Queue", ++counter);
//                HashMap<String, String> hm = new HashMap<>();
//                hm.put("name", "naveen");
//                redisService.setValue("String", hm);
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            redisService.unlock();
//        }).start();
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void treatDeliveries1() {
//        new Thread(()->{
//            //System.out.println("Task2 Trying to obtain log....");
//            redisService.lock();
//            System.out.println("The task2 is executed");
//            try {
//                redisService.queuePush("Queue", ++counter);
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            redisService.unlock();
//        }).start();
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void fetchFromQueue() {
//        Object value = redisService.queuePop("Queue");
//        Object result = redisService.getValue("String");
//        if (result != null) {
//            System.out.println("Testing get with expiry : "+result);
//        }
//        if(value != null) {
//            System.out.println("Received from Queue : "+value);
//        }
//    }
}
