package de.htw.product.service;

import de.htw.product.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WebService {
    private final WebClient.Builder webClientBuilder;

    public void checkToken(String token) {
        boolean authorized = Boolean.TRUE.equals(webClientBuilder.build().get()
                .uri("http://auth-service/rest/auth")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block());
        if(!authorized) throw new UnauthorizedException();
    }

    public String getEmailByToken(String token) {
        return webClientBuilder.build().get()
                .uri("http://auth-service/rest/auth/token")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
