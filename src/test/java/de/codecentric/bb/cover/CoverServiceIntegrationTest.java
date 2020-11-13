package de.codecentric.bb.cover;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CoverServiceIntegrationTest {

    private static final String VALID_ISBN = "3551551685";
    private static final String INVALID_ISBN = "9783423139557";
    private CoverService coverService;

    @BeforeEach
    void setUp() {
        coverService = new CoverService();
    }

    @Nested
    class getCoverFromIsbn {

        @Test
        void shouldReturnCorrectCover() {
            byte[] coverBytes = coverService.getCoverByIsbn(VALID_ISBN);

            assertThat(coverBytes.length, is(135445));
        }

        @Test
        @Disabled
        void shouldThrowForMalformedUrl() {
            // TODO: No idea how to test this without PowerMock
            assertThrows(CoverNotFoundException.class, () -> coverService.getCoverByIsbn(null));
        }

        @Test
        void shouldThrowForIsbnWithoutCover() {
            assertThrows(CoverTooSmallException.class, () -> coverService.getCoverByIsbn(INVALID_ISBN));
        }
    }
}