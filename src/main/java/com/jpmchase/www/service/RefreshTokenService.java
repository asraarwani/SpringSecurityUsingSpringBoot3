package com.jpmchase.www.service;

import com.jpmchase.www.entity.RefreshToken;
import com.jpmchase.www.entity.User;
import com.jpmchase.www.repository.RefreshTokenRepository;
import com.jpmchase.www.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Transactional
    public RefreshToken getRefreshToken(String userName) {
        Optional<User> optionalUser = userDetailsRepository.findByUserName(userName);
        if (optionalUser.isPresent()) {
            //In case user token exists and user tries to create a new token, we just delete the old token
            refreshTokenRepository.deleteByUser(optionalUser.get());
            refreshTokenRepository.flush();
            System.out.println("Old token deleted for user");
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
