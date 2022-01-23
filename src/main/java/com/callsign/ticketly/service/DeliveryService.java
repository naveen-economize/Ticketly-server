package com.callsign.ticketly.service;

import com.callsign.ticketly.constants.DeliveryStatus;
import com.callsign.ticketly.dto.DeliveryIn;
import com.callsign.ticketly.entity.Customer;
import com.callsign.ticketly.entity.Delivery;
import com.callsign.ticketly.entity.Restaurant;
import com.callsign.ticketly.repository.CustomerRepository;
import com.callsign.ticketly.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.locks.Lock;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RedisLockRegistry redisLockRegistry;

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

    @Scheduled(fixedDelay = 1000)
    public void treatDeliveries() {
        new Thread(()->{
            System.out.println("Task1 Trying to obtain log....");
            Lock lock =redisLockRegistry.obtain("test");
            lock.lock();
            System.out.println("The task1 is executed");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }).start();
    }

    @Scheduled(fixedDelay = 1000)
    public void treatDeliveries1() {
        new Thread(()->{
            System.out.println("Task2 Trying to obtain log....");
            Lock lock =redisLockRegistry.obtain("test");
            lock.lock();
            System.out.println("The task2 is executed");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }).start();
    }
}
