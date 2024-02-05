package de.htw.auth.service;

import de.htw.auth.dto.UserRequest;
import de.htw.auth.dto.UserResponse;
import de.htw.auth.exception.BadRequestException;
import de.htw.auth.exception.UnauthorizedException;
import de.htw.auth.model.Token;
import de.htw.auth.model.User;
import de.htw.auth.repository.TokenRepository;
import de.htw.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public String generateToken(String email, String password) {
        if(!userRepository.existsUserByEmailAndPassword(email, password)) {
            throw new UnauthorizedException();
        }
        String token = getToken();
        tokenRepository.save(new Token(token, email));
        return token;
    }

    public Boolean checkToken(String token) {
        return tokenRepository.existsById(token);
    }

    public String getEmailByToken(String tokenString) {
        Token token = tokenRepository.findById(tokenString)
                .orElseThrow(UnauthorizedException::new);
        return token.getEmail();
    }

    public ResponseEntity<String> verifyUser(UserRequest userRequest) {
        String token = generateToken(userRequest.getEmail(), userRequest.getPassword());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(token);
    }

    private String getToken() {
        final SecureRandom secureRandom = new SecureRandom(); //threadsafe
        final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
    public ResponseEntity<UserResponse> createUser(UserRequest userRequest) {
        if(userRequest.getEmail() == null || userRequest.getPassword() == null || userRequest.getRole() == null) {
            throw new BadRequestException("Need email, password, role, first and last name");
        }
        User user = userBuilder(userRequest);
        if(userRepository.existsUserByEmail(user.getEmail())) {
            throw new BadRequestException("Account with email already exists!");
        }
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    public boolean verifyAdmin(UserRequest userRequest) {
        return userRequest.getRole().contains("ADMIN");
    }

    private User userBuilder(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .build();
    }
}
