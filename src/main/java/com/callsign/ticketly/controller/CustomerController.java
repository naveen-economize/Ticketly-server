package com.callsign.ticketly.controller;

import com.callsign.ticketly.dto.BaseResponseDto;
import com.callsign.ticketly.dto.CustomerIn;
import com.callsign.ticketly.dto.CustomerLoginIn;
import com.callsign.ticketly.dto.CustomerLoginOut;
import com.callsign.ticketly.entity.Customer;
import com.callsign.ticketly.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/v1")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/signup")
    public @ResponseBody
    ResponseEntity<BaseResponseDto<HashMap<String, String>>> userSignUp(@RequestBody CustomerIn customerIn) throws Exception {
        Customer customer = customerService.userSignUp(customerIn);
        HashMap<String, String> response = new HashMap<>();
        response.put("id", customer.getId());
        return BaseResponseDto.created("created", response);
    }

    @PostMapping("/login")
    public @ResponseBody
    ResponseEntity<BaseResponseDto<CustomerLoginOut>> userLogin(@RequestBody CustomerLoginIn customerLoginIn) throws Exception {
        return BaseResponseDto.success("success", Optional.of(customerService.userLogin(customerLoginIn)));
    }
}
