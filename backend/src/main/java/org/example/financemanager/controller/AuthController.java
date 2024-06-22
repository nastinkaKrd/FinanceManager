package org.example.financemanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.financemanager.dto.AuthResponseDto;
import org.example.financemanager.dto.LoginRequest;
import org.example.financemanager.dto.RegisterRequest;
import org.example.financemanager.service.AuthenticationService;
import org.example.financemanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;


    @GetMapping("/get-user-details")
    public String getUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        if (userDetails == null){
            return "null";
        }
        return userDetails.getUsername();
    }


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authenticationService.sendVerificationMessageOnEmailWhenRegister(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequest loginRequest)  {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @GetMapping("/email-confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponseDto emailConfirmation(@PathVariable(name = "token") String token){
        return userService.updateUserEmailConfirmation(token);
    }

    @PostMapping("/refresh-token")
    public AuthResponseDto refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authenticationService.refreshToken(request, response);
    }
}
