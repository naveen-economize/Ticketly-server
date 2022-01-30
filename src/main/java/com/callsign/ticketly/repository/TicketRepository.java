package com.callsign.ticketly.repository;

import com.callsign.ticketly.constants.TicketStatus;
import com.callsign.ticketly.entity.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    // select * from ticket where status in ('1','2') order by priority desc, weights desc;
    @Query("SELECT t FROM Ticket t where t.ticketStatus in ?1 ORDER BY t.ticketPriority DESC, t.weights DESC")
    List<Ticket> searchTickets(List<TicketStatus> ticketStatuses);
}
