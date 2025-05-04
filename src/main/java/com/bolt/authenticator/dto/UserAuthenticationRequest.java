package com.bolt.authenticator.dto;

import jakarta.validation.constraints.Email;

public record UserAuthenticationRequest(

        @Email
        String email,
        String password) {
}
