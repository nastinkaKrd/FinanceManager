package org.example.financemanager.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.financemanager.dto.AuthResponseDto;
import org.example.financemanager.dto.LoginRequest;
import org.example.financemanager.dto.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest registerRequest);

    String sendVerificationMessageOnEmailWhenRegister(RegisterRequest registerRequest);

    AuthResponseDto authenticate(LoginRequest loginRequest);

    String sendVerificationMessageOnEmail(String username, String email, String subject, String text);

    AuthResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
