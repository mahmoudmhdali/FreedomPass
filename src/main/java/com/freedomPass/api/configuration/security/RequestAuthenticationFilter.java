/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.security;

import com.freedomPass.api.configuration.security.service.TokenAuthenticationService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Delegates authentication to the TokenAuthenticationService
        Authentication authentication = tokenAuthenticationService.getAuthentication(request);

        // Apply the authentication to the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Go on processing the request
        filterChain.doFilter(request, response);

        // Clears the context from authentication
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
