package com.project.module.authentication.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;
}
