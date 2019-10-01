package com.filipflorczyk.pinzbackend.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtValidationRequest {

    private String token;
}
