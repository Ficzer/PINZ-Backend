package com.filipflorczyk.pinzbackend.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
