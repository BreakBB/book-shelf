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
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class LoginService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${login.url}")
    private String keycloakLoginUrl;

    public TokenResponse handleLoginRequest(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "spring-book-shelf");
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);

        ResponseEntity<TokenResponse> response =
            restTemplate.postForEntity(keycloakLoginUrl, new HttpEntity<>(map, headers), TokenResponse.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Unexpected response code: " + response.getStatusCode());
        }

        return response.getBody();
    }
}