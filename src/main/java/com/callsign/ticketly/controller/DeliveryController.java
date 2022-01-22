package com.callsign.ticketly.controller;

import com.callsign.ticketly.dto.BaseResponseDto;
import com.callsign.ticketly.dto.DeliveryIn;
import com.callsign.ticketly.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @PostMapping("")
    public @ResponseBody
    ResponseEntity<BaseResponseDto<HashMap<String, String>>> storeDelivery(@RequestBody DeliveryIn deliveryIn) throws Exception {
        String deliveryID = deliveryService.storeDelivery(deliveryIn);
        HashMap<String, String> response = new HashMap<>();
        response.put("id", deliveryID);
        return BaseResponseDto.created("created", response);
    }

}
