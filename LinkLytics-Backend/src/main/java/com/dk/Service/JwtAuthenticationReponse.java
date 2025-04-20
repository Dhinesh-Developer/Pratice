package com.dk.Service;

import lombok.Data;

@Data
public class JwtAuthenticationReponse {

    private String token;

    public JwtAuthenticationReponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtAuthenticationReponse(String token) {
        this.token = token;
    }
}
