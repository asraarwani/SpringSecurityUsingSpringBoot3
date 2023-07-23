package com.jpmchase.www.service;

import com.jpmchase.www.entity.RefreshToken;
import com.jpmchase.www.entity.User;
import com.jpmchase.www.repository.RefreshTokenRepository;
import com.jpmchase.www.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public RefreshToken getRefreshToken(String userName) {
        Optional<User> optionalUser = userDetailsRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(optionalUser.get())
                    .token(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(1000 * 60 * 60))
                    .build();
            return refreshTokenRepository.save(refreshToken);
        } else {
            return RefreshToken.builder().build();
        }
    }

    public RefreshToken validateRefreshToken(String token) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByToken(token);
        if (optionalRefreshToken.isPresent()) {
            if (optionalRefreshToken.get().getExpiry().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.delete(optionalRefreshToken.get());
                System.out.println("Refresh token has expired. Please login back.");
            } else {
                return optionalRefreshToken.get();
            }
        } else {
            System.out.println("Token provided is not valid.");
        }
        return null;
    }
}
