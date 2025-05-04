package com.bolt.authenticator.controller;

import com.bolt.authenticator.dto.MessageResponse;
import com.bolt.authenticator.dto.UserAuthenticationRequest;
import com.bolt.authenticator.dto.UserAuthenticationResponse;
import com.bolt.authenticator.dto.UserRegisterRequest;
import com.bolt.authenticator.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody @Valid UserRegisterRequest request) {
        authenticationService.registerUser(request.toModel());
        URI location = URI.create("/api/users");
        return ResponseEntity
                .created(location)
                .body(new MessageResponse("Usuário registrado. Verifique seu email para ativar a conta."));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<MessageResponse> verifyUserEmail(@RequestParam("code") String verificationCode) {
        boolean verified = authenticationService.verifyUser(verificationCode);

        if (verified) {
            return ResponseEntity.ok().body(new MessageResponse("Email verificado com sucesso."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("A conta já foi verificada ou o código é inválido."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationResponse> loginUser(@RequestBody @Valid UserAuthenticationRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(authenticationService.loginUser(request));
    }
}
