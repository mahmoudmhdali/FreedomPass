/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedomPass.api.configuration.security.service.TokenAuthenticationService;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandlerImpl implements LogoutHandler {
    
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication a) {
        try {
            
            response.setContentType("application/json;charset=UTF-8");

            // Delegates authentication to the TokenAuthenticationService
            Authentication authentication = tokenAuthenticationService.getAuthentication(request);
            
            if (authentication != null) {
                response.getWriter().write(
                        new ObjectMapper().writeValueAsString(
                                ResponseBuilder.getInstance()
                                .setHttpResponseEntityResultDescription("You are now logged out.")
                                .getHttpResponseEntity())
                );
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(
                        new ObjectMapper().writeValueAsString(
                                ResponseBuilder.getInstance()
                                .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                                .setHttpResponseEntityResultDescription("Access denied for this resource. Contact your service provider for more help")
                                .getHttpResponseEntity())
                );
            }
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException ex) {
        }
        
    }
    
}
