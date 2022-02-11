package de.codecentric.bb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationIntegrationTest {

    @Test
    void contextLoads() {
    }

    @Test
    void applicationStarts() {
        Application.main(new String[] {});
    }
}