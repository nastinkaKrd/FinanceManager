package org.example.financemanager.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Boolean isEmailVerified;
    private String userRoles;
}
