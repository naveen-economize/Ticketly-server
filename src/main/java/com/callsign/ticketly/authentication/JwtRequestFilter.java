package com.callsign.ticketly.authentication;

import com.callsign.ticketly.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private CustomerService userDetailsServiceCustom;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain) throws ServletException, IOException
    {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        String userEmail = null;
        String jwtToken = null;
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try{
                userEmail = jwtTokenUtility.getUserEmailFromToken(jwtToken);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsServiceCustom.loadUserByUsername(userEmail);

            //verify is the username in the token matches from the user details and check if token is not expired
            if(jwtTokenUtility.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userEmail, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(httpServletRequest, httpServletResponse);
    }
}