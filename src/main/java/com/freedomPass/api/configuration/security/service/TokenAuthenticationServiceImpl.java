/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.security.service;

import com.freedomPass.api.configuration.spirng.JWTConfiguration;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private JWTConfiguration jwtConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void addAuthentication(HttpServletResponse res, Authentication authentication, boolean rememberMe) {
        int expiryDaysToken = 1;
        int expiryDaysRefresh = 2;
        if (rememberMe) {
            expiryDaysToken = 30;
            expiryDaysRefresh = 60;
        }
        String username = authentication.getName();
        String token = tokenHandler.build(username, expiryDaysToken);
        String refreshToken = tokenHandler.build(username, expiryDaysRefresh);
        res.addHeader(jwtConfig.getTokenHeader(), jwtConfig.getTokenprefix() + " " + token);
        res.addHeader(jwtConfig.getRefreshTokenHeader(), jwtConfig.getTokenprefix() + " " + refreshToken);
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getTokenHeader());
        if (token != null && token.startsWith(jwtConfig.getTokenprefix())) {
            String username = null;
            try {
                username = tokenHandler.parse(token);
            } catch (ExpiredJwtException e) {
            } catch (UnsupportedJwtException e) {
            } catch (MalformedJwtException e) {
            } catch (SignatureException e) {
            } catch (IllegalArgumentException e) {
            }

            if (username != null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                return authentication;
            } else {
                return null;
            }

        }
        return null;
    }
}
