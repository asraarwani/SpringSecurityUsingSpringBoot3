package com.jpmchase.www.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthRequest {

    private String userName;
    private String password;
}
