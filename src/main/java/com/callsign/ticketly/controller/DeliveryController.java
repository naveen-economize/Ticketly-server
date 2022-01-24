package com.callsign.ticketly.controller;

import com.callsign.ticketly.dto.BaseResponseDto;
import com.callsign.ticketly.dto.DeliveryIn;
import com.callsign.ticketly.dto.DeliveryPatchIn;
import com.callsign.ticketly.entity.Delivery;
import com.callsign.ticketly.service.DeliveryService;
import com.callsign.ticketly.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v1/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/hello")
    public @ResponseBody HashMap<String, String> healthCheck() {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "success");
        return response;
    }

    @PostMapping()
    public @ResponseBody
    ResponseEntity<BaseResponseDto<HashMap<String, String>>> storeDelivery(@RequestBody DeliveryIn deliveryIn) throws Exception {
        Delivery delivery = deliveryService.storeDelivery(deliveryIn);
        HashMap<String, String> response = new HashMap<>();
        response.put("id", delivery.getId());
        return BaseResponseDto.created("created", response);
    }

    @PatchMapping
    public @ResponseBody
    ResponseEntity<BaseResponseDto<HashMap<String, String>>> patchDelivery(@RequestBody DeliveryPatchIn deliveryPatchIn) throws Exception{
        deliveryService.patchDelivery(deliveryPatchIn);
        return BaseResponseDto.success("updated successfully", Optional.empty());
    }

    @GetMapping
    public @ResponseBody
    ResponseEntity<BaseResponseDto<Delivery>> getDelivery(@RequestParam("id") String deliveryID) throws Exception {
        return BaseResponseDto.success("success", Optional.of(deliveryService.getDelivery(deliveryID)));
    }
}
