package de.codecentric.bb.login;

import lombok.extern.slf4j.Slf4j;
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
        log.info("Trying to login user: {}", loginRequest.getUsername());

        return loginService.handleLoginRequest(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
