package com.uade.grupo4.backend_ecommerce.controller.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private long id;
    private String username;
    private String email;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String role;
}
