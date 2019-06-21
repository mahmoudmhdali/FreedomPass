/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedomPass.api.configuration.security.service.TokenAuthenticationService;
import com.freedomPass.api.engine.SettingsEngine;
import com.freedomPass.project.helpermodel.ResponseBuilder;
import com.freedomPass.project.helpermodel.ResponseCode;
import com.freedomPass.project.helpermodel.UserProfileCredentials;
import com.freedomPass.project.service.UserAttemptService;
import com.freedomPass.project.service.UserService;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    SettingsEngine settingsEngine;

    @Autowired
    UserAttemptService userAttemp;

    @Autowired
    UserService userService;

    private boolean rememberMe;

    private String username;

    public LoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // Retrieve username and password from the http request and save them in an Account object.
        UserProfileCredentials userProfile = new ObjectMapper().readValue(request.getInputStream(), UserProfileCredentials.class);
        rememberMe = userProfile.isRememberMe();
        username = userProfile.getEmail();
        // Verify the correctness of login details...
        // If correct, successfulAuthentication() method is executed.		
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userProfile.getEmail(), userProfile.getPassword())
        );

        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication auth) throws IOException, ServletException {
        // Pass authenticated user data to the tokenAuthenticationService in order to add a JWT to the http response.
        userAttemp.resetAttempts(username);
        tokenAuthenticationService.addAuthentication(response, auth, rememberMe);
        SecurityContextHolder.getContext().setAuthentication(auth);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("users/view/?email=" + auth.getName());
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        if (exception instanceof org.springframework.security.authentication.BadCredentialsException || exception instanceof org.springframework.security.authentication.InternalAuthenticationServiceException) {
            userAttemp.updateAttempts(username);
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            ResponseBuilder.getInstance()
                                    .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                                    .setHttpResponseEntityResultDescription("Invalid username and/or password.")
                                    .getHttpResponseEntity())
            );
        } else if (exception instanceof org.springframework.security.authentication.LockedException) {
            long accountLockedDuration = (long) settingsEngine.getFirstLevelSetting("LOCK_ACCOUNT_DURATION");
            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(userAttemp.getAccountUnLockedDate(username));
            calendar.add(Calendar.MINUTE, (int) (accountLockedDuration));
            Date accountLockedTill = calendar.getTime();
            long secondsBetween = (accountLockedTill.getTime() - now.getTime()) / 1000;
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            ResponseBuilder.getInstance()
                                    .setHttpResponseEntityResultCode(ResponseCode.ALERT)
                                    .setHttpResponseEntityResultDescription("Account is locked.")
                                    .addHttpResponseEntityData("remaining", secondsBetween)
                                    .addHttpResponseEntityData("lockedDeuration", accountLockedDuration)
                                    .getHttpResponseEntity())
            );
        } else if (exception.getMessage().contains("JDBCConnectionException")) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            ResponseBuilder.getInstance()
                                    .setHttpResponseEntityResultCode(ResponseCode.EXCEPTION_OCCURED)
                                    .setHttpResponseEntityResultDescription("Connection lost with the data source. Contact your service provider for help")
                                    .getHttpResponseEntity())
            );
        } else if (exception instanceof org.springframework.security.authentication.DisabledException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            ResponseBuilder.getInstance()
                                    .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                                    .setHttpResponseEntityResultDescription("Please check your email to activate your account")
                                    .getHttpResponseEntity())
            );
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(
                    new ObjectMapper().writeValueAsString(
                            ResponseBuilder.getInstance()
                                    .setHttpResponseEntityResultCode(ResponseCode.UNAUTHORIZED_USER_ACTION)
                                    .setHttpResponseEntityResultDescription("Error Occured.")
                                    .getHttpResponseEntity())
            );
        }
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
