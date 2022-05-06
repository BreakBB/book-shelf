package de.codecentric.bb.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LoginService {

    private final String keycloakLoginUrl;
    private final RestTemplate restTemplate;

    public LoginService(@Value("${login.url}") String keycloakLoginUrl, RestTemplate restTemplate) {
        this.keycloakLoginUrl = keycloakLoginUrl;
        this.restTemplate = restTemplate;
    }

    public TokenResponse handleLoginRequest(String username, String password) {
        log.info("Trying to login user: {}", username);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "spring-book-shelf");
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);

        try {
            ResponseEntity<TokenResponse> response =
                restTemplate.postForEntity(keycloakLoginUrl, new HttpEntity<>(map, headers), TokenResponse.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.warn("Failed to login user {}", username);
                throw new RuntimeException("Unexpected response code: " + response.getStatusCode());
            }

            log.info("Successfully logged in user {}", username);
            return response.getBody();
        } catch (HttpClientErrorException exception) {
            log.warn("Failed to login user {}", username, exception);
            throw new UnauthorizedException();
        }
    }

    public TokenResponse handleRefreshTokenRequest(String refreshToken) {
        log.info("Trying to retrieve new access token by refresh token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "spring-book-shelf");
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", refreshToken);

        ResponseEntity<TokenResponse> response;
        try {
            response = restTemplate.postForEntity(keycloakLoginUrl, new HttpEntity<>(map, headers), TokenResponse.class);
        } catch (HttpClientErrorException exception) {
            throw new InvalidRefreshTokenException();
        }

        log.info("Successfully refreshed access token");
        return response.getBody();

    }
}
