package org.example.financemanager.service;

import lombok.AllArgsConstructor;
import org.example.financemanager.domain.User;
import org.example.financemanager.dto.AuthResponseDto;
import org.example.financemanager.exception.ApiExceptionAlreadyReported;
import org.example.financemanager.exception.ApiExceptionNotFound;
import org.example.financemanager.exception.NotVerifiedEmailException;
import org.example.financemanager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImplements implements UserService{
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ApiExceptionNotFound("User is not found")
        );
    }

    @Override
    public AuthResponseDto updateUserEmailConfirmation(String token) {
        User user = getUserByUsername(jwtService.extractUsername(token));
        if (!user.getIsEmailVerified()){
            userRepository.updateUserEmailVerifyFieldByUserEmail(user.getEmail());
            String jwtToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return AuthResponseDto.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }else {
            throw new RuntimeException("Oops something went wrong, try again...");
        }
    }

    @Override
    public void throwExceptionIfEmailNotVerified(String username) {
        User user = getUserByUsername(username);
        if (!user.getIsEmailVerified()){
            throw new NotVerifiedEmailException(user.getEmail());
        }
    }

    @Override
    public void saveUser(User user) {
        if (isUserExist(user.getUsername())){
            throw  new ApiExceptionAlreadyReported("Account for this email exist. Try another account.");
        }else {
            userRepository.save(user);
        }
    }

    @Override
    public Boolean isUserExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
