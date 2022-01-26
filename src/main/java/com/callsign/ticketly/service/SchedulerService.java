package com.callsign.ticketly.service;

import com.callsign.ticketly.entity.Delivery;
import com.callsign.ticketly.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    RedisService redisService;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    TicketService ticketService;
    private final String expiredDeliveryQueue = "expired-delivery-queue";

    @Scheduled(fixedDelay = 1000 * 60)
    public void treatExpiredDeliveriesProducer() {
        new Thread(() -> {
            try{
                redisService.lock();
                List<Delivery> expiredDeliveries = deliveryRepository.getExpiredDeliveries(LocalDateTime.now());
                for(Delivery expiredDelivery : expiredDeliveries) {
                    Delivery deliveryFromRedis = (Delivery)redisService.getValue(expiredDelivery.getId());
                    // deliveryKey is null when it is processed for first time or when then redis key expires
                    // the delivery needs to be treated only when it is absent in redis to ensure that currently it is being treated by only one node.
                    if(deliveryFromRedis == null) {
                        redisService.queuePush(expiredDeliveryQueue, expiredDelivery);
                    }
                }
                redisService.unlock();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Scheduled(fixedDelay = 1000)
    public void treatExpiredDeliveriesConsumer() {
        new Thread(() -> {
            try{
                Delivery delivery = (Delivery) redisService.queuePop(expiredDeliveryQueue);
                if(delivery != null) {
                    redisService.setValue(delivery.getId(), delivery, 1000 * 300);
                    ticketService.createTicketIfNeededForExpiredDeliveryTime(delivery);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
