package de.codecentric.bb.login;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    private static final String USERNAME = "someUsername";
    private static final String PASSWORD = "somePassword";
    private static final String REFRESH_TOKEN = "someRefreshToken";

    @Mock
    private RestTemplate restTemplate;

    private LoginService loginService;

    @Captor
    ArgumentCaptor<HttpEntity<MultiValueMap<String, String>>> captor;

    @BeforeEach
    void setUp() {
        loginService = new LoginService("/test", restTemplate);
    }

    @Nested
    class HandleLoginRequest {
        @Test
        void shouldLoginSuccessfully() {
            TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
            ResponseEntity<TokenResponse> responseEntity = ResponseEntity.ok(tokenResponse);
            when(restTemplate.postForEntity(eq("/test"), captor.capture(), eq(TokenResponse.class))).thenReturn(responseEntity);

            TokenResponse response = loginService.handleLoginRequest(USERNAME, PASSWORD);

            assertThat(response, is(tokenResponse));
            HttpEntity<MultiValueMap<String, String>> httpEntity = captor.getValue();
            assertThat(httpEntity.getHeaders().getContentType(), is(MediaType.APPLICATION_FORM_URLENCODED));

            MultiValueMap<String, String> body = httpEntity.getBody();
            assertThat(body, is(notNullValue()));
            assertThat(body.get("client_id").get(0), is("spring-book-shelf"));
            assertThat(body.get("grant_type").get(0), is("password"));
            assertThat(body.get("username").get(0), is(USERNAME));
            assertThat(body.get("password").get(0), is(PASSWORD));
        }

        @Test
        void shouldThrowRuntimeExceptionOnFailedLogin() {
            ResponseEntity<TokenResponse> responseEntity = ResponseEntity.badRequest().body(TokenResponse.builder().build());
            when(restTemplate.postForEntity(eq("/test"), any(HttpEntity.class), eq(TokenResponse.class))).thenReturn(responseEntity);

            assertThrows(RuntimeException.class, () -> loginService.handleLoginRequest(USERNAME, PASSWORD));
        }

        @Test
        void shouldThrowUnauthorizedExceptionOnHttpClientErrorExceptionByRestTemplate() {
            when(restTemplate.postForEntity(eq("/test"), any(HttpEntity.class), eq(TokenResponse.class))).thenThrow(
                new HttpClientErrorException(HttpStatus.UNAUTHORIZED));

            assertThrows(UnauthorizedException.class, () -> loginService.handleLoginRequest(USERNAME, PASSWORD));
        }
    }

    @Nested
    class HandleRefreshTokenRequest {
        @Test
        void shouldSuccessfullyRetrieveNewAccessToken() {
            TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
            ResponseEntity<TokenResponse> responseEntity = ResponseEntity.ok(tokenResponse);
            when(restTemplate.postForEntity(eq("/test"), captor.capture(), eq(TokenResponse.class))).thenReturn(responseEntity);

            TokenResponse response = loginService.handleRefreshTokenRequest(REFRESH_TOKEN);

            assertThat(response, is(tokenResponse));
            HttpEntity<MultiValueMap<String, String>> httpEntity = captor.getValue();
            assertThat(httpEntity.getHeaders().getContentType(), is(MediaType.APPLICATION_FORM_URLENCODED));

            MultiValueMap<String, String> body = httpEntity.getBody();
            assertThat(body, is(notNullValue()));
            assertThat(body.get("client_id").get(0), is("spring-book-shelf"));
            assertThat(body.get("grant_type").get(0), is("refresh_token"));
            assertThat(body.get("refresh_token").get(0), is(REFRESH_TOKEN));
        }

        @Test
        void shouldThrowRuntimeExceptionOnFailedRequest() {
            ResponseEntity<TokenResponse> responseEntity = ResponseEntity.badRequest().body(TokenResponse.builder().build());
            when(restTemplate.postForEntity(eq("/test"), any(HttpEntity.class), eq(TokenResponse.class))).thenReturn(responseEntity);

            assertThrows(RuntimeException.class, () -> loginService.handleRefreshTokenRequest(REFRESH_TOKEN));
        }
    }
}