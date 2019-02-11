/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.security.service;

import com.freedomPass.api.configuration.spirng.JWTConfiguration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenHandler {

    @Autowired
    private JWTConfiguration jwtConfig;

    //Build a JWT Token and adding the username as a subject to this token
    public String build(String username, int expireAfterInDays) {
        Calendar calendarInstance = Calendar.getInstance();
        calendarInstance.setTime(new Date(System.currentTimeMillis() + jwtConfig.getExpirytime()));
        calendarInstance.add(Calendar.DAY_OF_MONTH, expireAfterInDays);
        Date now = new Date();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(calendarInstance.getTime())
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretkey())
                .compact();

    }

    // Parse the JWT token and retreive the username from it
    public String parse(String token) {

        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecretkey())
                .parseClaimsJws(token.replace(jwtConfig.getTokenprefix(), ""))
                .getBody()
                .getSubject();
    }

}
