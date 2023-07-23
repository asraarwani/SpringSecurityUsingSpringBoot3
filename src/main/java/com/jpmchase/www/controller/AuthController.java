package com.jpmchase.www.controller;

import com.jpmchase.www.dto.AuthRequest;
import com.jpmchase.www.dto.AuthResponse;
import com.jpmchase.www.dto.RefreshTokenRequest;
import com.jpmchase.www.entity.RefreshToken;
import com.jpmchase.www.service.JwtTokenService;
import com.jpmchase.www.service.RefreshTokenService;
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

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/authenticate")
    public AuthResponse generateJwtToken(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = new AuthResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String jwtToken = jwtTokenService.generateJwtToken(authRequest.getUserName());
                RefreshToken refreshToken = refreshTokenService.getRefreshToken(authRequest.getUserName());
                authResponse = AuthResponse.builder()
                        .jwtToken(jwtToken)
                        .refreshToken(refreshToken.getToken())
                        .status(true)
                        .build();
            }
        } catch (Exception exception) {
            System.err.println("Exception : " + exception.getMessage());
            authResponse.setStatus(false);
            authResponse.setErrorMessage(String.format("Either username or password is incorrect."));
        }
        return authResponse;
    }

    @PostMapping("/refreshToken")
    public AuthResponse getRefreshToken(@RequestBody RefreshTokenRequest request) {
        AuthResponse authResponse = new AuthResponse();
        RefreshToken token = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        if (token != null) {
            String jwtToken = jwtTokenService.generateJwtToken(token.getUser().getUserName());
            authResponse = AuthResponse.builder()
                    .jwtToken(jwtToken)
                    .refreshToken(token.getToken())
                    .status(true)
                    .build();
        } else {
            authResponse.setStatus(false);
            authResponse.setErrorMessage("Refresh token you have provided is not valid.");
        }
        return authResponse;
    }
}
