package com.bolt.authenticator.dto;

import com.bolt.authenticator.model.User;
import jakarta.validation.constraints.Email;

public record UserRegisterRequest(

        String name,

        @Email
        String email,
        String password) {

        public User toModel() {
                return new User(name, email, password);
        }
}
