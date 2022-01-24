package com.callsign.ticketly.service;

import com.callsign.ticketly.constants.DeliveryStatus;
import com.callsign.ticketly.dto.DeliveryIn;
import com.callsign.ticketly.entity.Delivery;
import com.callsign.ticketly.repository.CustomerRepository;
import com.callsign.ticketly.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RedisService redisService;
    private int counter = 0;

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
