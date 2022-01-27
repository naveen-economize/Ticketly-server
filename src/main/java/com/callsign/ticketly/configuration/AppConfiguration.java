package com.callsign.ticketly.configuration;

import com.callsign.ticketly.authentication.JwtRequestFilter;
import com.callsign.ticketly.authentication.JwtTokenUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
