package de.codecentric.bb.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.handleLoginRequest(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @GetMapping("/checkToken")
    public boolean isValidAccessToken() {
        // This endpoint is required to return something useful, when trying to verify if the access_token is still valid.
        // The Keycloak configuration checks this endpoint before reaching this code and returns 401 if the token is not valid.
        return true;
    }
}
