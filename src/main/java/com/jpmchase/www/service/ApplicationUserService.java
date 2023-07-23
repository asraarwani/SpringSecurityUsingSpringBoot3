package com.jpmchase.www.service;

import com.jpmchase.www.entity.User;
import com.jpmchase.www.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUser(User applicationUser) {
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        userDetailsRepository.save(applicationUser);
        return "Success";
    }
}
