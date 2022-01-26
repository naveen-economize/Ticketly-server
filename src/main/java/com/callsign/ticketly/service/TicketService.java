package com.callsign.ticketly.service;

import com.callsign.ticketly.constants.CustomerType;
import com.callsign.ticketly.constants.DeliveryStatus;
import com.callsign.ticketly.constants.TicketPriority;
import com.callsign.ticketly.constants.TicketStatus;
import com.callsign.ticketly.entity.Customer;
import com.callsign.ticketly.entity.Delivery;
import com.callsign.ticketly.entity.Restaurant;
import com.callsign.ticketly.entity.Ticket;
import com.callsign.ticketly.repository.DeliveryRepository;
import com.callsign.ticketly.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    DeliveryRepository deliveryRepository;

    public LocalDateTime calculateEstimatedDeliveryTime(Delivery delivery) {
        DeliveryStatus currentDeliveryStatus = delivery.getDeliveryStatus();
        LocalDateTime estimatedDeliveryTime = LocalDateTime.now();
        long timeToReachDestination = delivery.getCurrentDistanceFromDestinationInMetres() / 200;
        if(currentDeliveryStatus == DeliveryStatus.preparing) {
            Restaurant restaurant = delivery.getRestaurant();
            long meanTimeToPrepareFood = restaurant.getMeanTimeToPrepareFood();
            estimatedDeliveryTime = estimatedDeliveryTime.plusMinutes(meanTimeToPrepareFood).plusMinutes(timeToReachDestination);
        } else if (currentDeliveryStatus == DeliveryStatus.picked) {
            estimatedDeliveryTime = estimatedDeliveryTime.plusMinutes(timeToReachDestination);
        }
        return estimatedDeliveryTime;
    }

    public void createTicketIfNeededForExpiredDeliveryTime(Delivery delivery) {
        Optional<Ticket> ticketOptional = Optional.empty();
        if(delivery.getTicketID() != null) {
            ticketOptional = ticketRepository.findById(delivery.getTicketID());
        }
        Customer customer = delivery.getCustomer();
        TicketPriority ticketPriority = customer.getCustomerType().getHighPriority();
        int weight = ticketPriority.getWeight();
        if(ticketOptional.isEmpty()) {
            handleTicketCreation(delivery, ticketPriority, weight);
        } else {
            Ticket ticket = ticketOptional.get();
            ticket.setTicketStatus(TicketStatus.open);
            ticket.setUpdatedAt(LocalDateTime.now());
            ticket.setTicketPriority(ticketPriority);
            ticket.setWeights(ticket.getWeights() + weight);
            ticketRepository.save(ticket);
        }
    }

    public void createTicketIfNeededForPreparingAndPickedDeliveryStatus(Delivery delivery, Boolean hasDeliveryStatusChanged) {
        LocalDateTime expectedDeliveryTime = delivery.getExpectedDeliveryTime();
        LocalDateTime estimatedDeliveryTime = calculateEstimatedDeliveryTime(delivery);
        // Need to create ticket if EstimatedTimeOfDelivery is more than ExpectedDeliveryTime
        if(expectedDeliveryTime.isBefore(estimatedDeliveryTime)) {
            DeliveryStatus deliveryStatus = delivery.getDeliveryStatus();
            CustomerType customerType = delivery.getCustomer().getCustomerType();
            TicketPriority ticketPriority = null;
            int weight;
            if(deliveryStatus == DeliveryStatus.preparing) {
                ticketPriority = customerType.getLowPriority();
            }else /*DeliveryStatus.picked*/{
                ticketPriority = customerType.getMediumPriority();
            }
            weight = ticketPriority.getWeight();
            Ticket ticket = delivery.getTicket();
            if(ticket == null) {
                handleTicketCreation(delivery, ticketPriority, weight);
            } else {
                ticket.setTicketStatus(TicketStatus.open);
                ticket.setUpdatedAt(LocalDateTime.now());
                ticket.setTicketPriority(ticketPriority);
                if(hasDeliveryStatusChanged) {
                    ticket.setWeights(ticket.getWeights() + weight);
                }
                ticketRepository.save(ticket);
            }
        }
    }

    public void handleTicketCreation(Delivery delivery, TicketPriority ticketPriority, int weight) {
        Ticket ticket = createTicket(ticketPriority, weight);
        delivery.setTicketID(ticket.getId());
        deliveryRepository.save(delivery);
    }

    public Ticket createTicket(TicketPriority ticketPriority, int weight) {
        Ticket ticket = new Ticket();
        LocalDateTime now = LocalDateTime.now();
        ticket.setCreatedAt(now);
        ticket.setUpdatedAt(now);
        ticket.setTicketStatus(TicketStatus.open);
        ticket.setTicketPriority(ticketPriority);
        ticket.setWeights(weight);
        return ticketRepository.save(ticket);
    }

}
