package com.bolt.authenticator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender mailSender;

    private final String VERIFICATION_BASE_URL = "http://localhost:8080/api/v1/auth/verify-email?code=";


    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendVerificationLink(String toEmail, String verificationCode) {
        String verificationLink = VERIFICATION_BASE_URL + verificationCode;
        String subject = "Confirmação de Cadastro";
        String messageBody = "Olá!\n\n"
                + "Por favor, clique no link abaixo para ativar sua conta:\n"
                + verificationLink + "\n\n"
                + "Se você não se cadastrou, ignore este e-mail.\n\n"
                + "Obrigado!";

        sendEmail(toEmail, subject, messageBody);
    }
}