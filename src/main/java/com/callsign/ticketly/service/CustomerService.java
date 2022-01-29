package com.callsign.ticketly.service;

import com.callsign.ticketly.authentication.JwtTokenUtility;
import com.callsign.ticketly.authentication.UserPrincipalCustom;
import com.callsign.ticketly.dto.CustomerIn;
import com.callsign.ticketly.dto.CustomerLoginIn;
import com.callsign.ticketly.dto.CustomerLoginOut;
import com.callsign.ticketly.entity.Customer;
import com.callsign.ticketly.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    public Customer userSignUp(CustomerIn customerIn) throws Exception{
        if(customerIn.getCustomerType() == null) {
            throw new IllegalArgumentException("Invalid CustomerType");
        }
        if(customerIn.getPassword() == null) {
            throw new IllegalArgumentException("Invalid password");
        }
        if(customerIn.getEmail() == null) {
            throw new IllegalArgumentException("Invalid Email");
        }
        Customer customerExists = customerRepository.findByEmail(customerIn.getEmail());
        if(customerExists != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        Customer customer = Customer.getCustomer(customerIn);
        String encodedPassword = new BCryptPasswordEncoder().encode(customerIn.getPassword());
        customer.setPassword(encodedPassword);
        return customerRepository.save(customer);
    }

    public CustomerLoginOut userLogin(CustomerLoginIn customerLoginIn) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerLoginIn.getEmail(), customerLoginIn.getPassword()));
        Customer customer = customerRepository.findByEmail(customerLoginIn.getEmail());
        if(customer == null) {
            throw new UsernameNotFoundException(customerLoginIn.getEmail());
        }
        final String token = jwtTokenUtility.generateToken(customer.getEmail());
        return customer.getCustomerLoginOut(token);
    }
}
