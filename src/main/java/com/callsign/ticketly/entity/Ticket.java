package com.callsign.ticketly.entity;

import com.callsign.ticketly.constants.TicketPriority;
import com.callsign.ticketly.constants.TicketStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @Column(name = "ticket_status")
    private TicketStatus ticketStatus;
    @Column(name = "ticket_priority")
    private TicketPriority ticketPriority;
    private Integer weights;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;
    @OneToOne(mappedBy = "ticket")
    Delivery delivery;
}
