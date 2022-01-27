package com.callsign.ticketly.service;

import com.callsign.ticketly.authentication.UserPrincipalCustom;
import com.callsign.ticketly.entity.Customer;
import com.callsign.ticketly.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerCustomService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new UsernameNotFoundException(email);
        }
        return new UserPrincipalCustom(customer);
    }

}
