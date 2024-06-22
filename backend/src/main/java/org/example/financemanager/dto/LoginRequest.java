package org.example.financemanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[!@#$%^&*()-_+=]).{8,}$", message = "Password is not valid. Do it stronger. (length - 8, 1 number, 1 - upper case letter)")
    private String password;
}
