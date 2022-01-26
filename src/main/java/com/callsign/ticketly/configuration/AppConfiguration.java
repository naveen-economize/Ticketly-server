package com.callsign.ticketly.configuration;

import com.callsign.ticketly.authentication.JwtRequestFilter;
import com.callsign.ticketly.authentication.JwtTokenUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    JwtRequestFilter getJwtRequestFilter()
    {
        return new JwtRequestFilter();
    }

    @Bean
    JwtTokenUtility getJwtTokenUtility()
    {
        return new JwtTokenUtility();
    }
}
