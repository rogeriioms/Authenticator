package com.bolt.authenticator.service;

import com.bolt.authenticator.config.security.TokenService;
import com.bolt.authenticator.dto.UserAuthenticationRequest;
import com.bolt.authenticator.dto.UserAuthenticationResponse;
import com.bolt.authenticator.model.User;
import com.bolt.authenticator.repository.UserRepository;
import com.bolt.authenticator.util.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("This email already exists!");
        } else {

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String randomCode = RandomString.generateRandomString(8);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            emailService.sendVerificationLink(user.getEmail(), randomCode);
            User savedUser = userRepository.save(user);
        }
    }

    public UserAuthenticationResponse loginUser(UserAuthenticationRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        if (!user.isEnabled()) {
            throw new IllegalStateException("A conta ainda não foi ativada via e-mail.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        String accessToken = tokenService.generateToken(user);

        return new UserAuthenticationResponse(accessToken, "https://appcliente.com/dashboard");
    }


    public boolean verifyUser(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode).orElse(null);

        if (user == null || user.isEnabled()) {
            return false;
        }

        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }
}
