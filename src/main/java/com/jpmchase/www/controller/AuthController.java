package com.jpmchase.www.controller;

import com.jpmchase.www.dto.AuthRequest;
import com.jpmchase.www.dto.AuthResponse;
import com.jpmchase.www.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/authenticate")
    public AuthResponse generateJwtToken(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                authResponse = jwtTokenService.generateJwtToken(authRequest.getUserName());
            }
        } catch (Exception exception) {
            System.err.println("Exception : " + exception.getMessage());
            authResponse.setStatus(false);
            authResponse.setErrorMessage(String.format("Either username or password is incorrect."));
        }
        return authResponse;
    }
}
