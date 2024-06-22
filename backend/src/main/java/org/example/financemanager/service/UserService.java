package org.example.financemanager.service;

import org.example.financemanager.domain.User;
import org.example.financemanager.dto.AuthResponseDto;

public interface UserService {
    void saveUser(User user);

    User getUserByUsername(String username);

    Boolean isUserExist(String username);

    void throwExceptionIfEmailNotVerified(String username);

    AuthResponseDto updateUserEmailConfirmation(String token);
}
