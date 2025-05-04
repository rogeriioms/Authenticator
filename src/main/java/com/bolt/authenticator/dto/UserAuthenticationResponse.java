package com.bolt.authenticator.dto;

public record UserAuthenticationResponse(
        String acessToken,
        String redirectUrl) {
}
