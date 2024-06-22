package org.example.financemanager.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.financemanager.domain.User;
import org.example.financemanager.domain.UserRoles;
import org.example.financemanager.dto.AuthResponseDto;
import org.example.financemanager.dto.LoginRequest;
import org.example.financemanager.dto.RegisterRequest;
import org.example.financemanager.exception.ApiExceptionNotFound;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplements implements AuthenticationService{
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendingUserEmail;

    @Override
    public void register(RegisterRequest registerRequest) {
        var user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isEmailVerified(false)
                .userRoles(UserRoles.ROLE_USER)
                .build();
        userService.saveUser(user);
    }

    @Override
    public String sendVerificationMessageOnEmailWhenRegister(RegisterRequest registerRequest) {
        register(registerRequest);
        String subject = "Confirmation of registration";
        String text = "Follow this link to confirm your registration: http://localhost:4200/api/auth/email-confirm?token=" + jwtService.generateTempToken(registerRequest.getUsername());
        return sendVerificationMessageOnEmail(registerRequest.getUsername(), registerRequest.getEmail(), subject, text);
    }

    @Override
    public AuthResponseDto authenticate(LoginRequest loginRequest) {
        userService.throwExceptionIfEmailNotVerified(loginRequest.getUsername());
        User user = userService.getUserByUsername(loginRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        String access_token = jwtService.generateAccessToken(user);
        String refresh_token = jwtService.generateRefreshToken(user);
        return AuthResponseDto.builder()
                .accessToken(access_token)
                .refreshToken(refresh_token)
                .build();


    }

    @Override
    public String sendVerificationMessageOnEmail(String username, String email, String subject, String text) {
        if (userService.isUserExist(username)){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sendingUserEmail);
            message.setTo(email);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
            return "The letter has been sent to your email. Please check your email...";
        }else {
            throw new ApiExceptionNotFound("User is not found");
        }
    }

    @Override
    public AuthResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response){
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            throw new RuntimeException();
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new RuntimeException();
        }
        var user = this.userService.getUserByUsername(userEmail);
        if (!jwtService.validateToken(refreshToken, user)) {
            throw new RuntimeException();
        }
        var accessToken = jwtService.generateAccessToken(user);
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
