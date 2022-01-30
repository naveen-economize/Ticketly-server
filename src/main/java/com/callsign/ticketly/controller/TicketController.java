package com.callsign.ticketly.controller;

import com.callsign.ticketly.constants.TicketStatus;
import com.callsign.ticketly.dto.BaseResponseDto;
import com.callsign.ticketly.entity.Ticket;
import com.callsign.ticketly.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v1/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/search")
    public @ResponseBody
    ResponseEntity<BaseResponseDto<List<Ticket>>> searchTickets(@RequestParam("status")List<TicketStatus> ticketStatuses) {
        return BaseResponseDto.success("success", Optional.of(ticketService.searchTickets(ticketStatuses)));
    };

}
