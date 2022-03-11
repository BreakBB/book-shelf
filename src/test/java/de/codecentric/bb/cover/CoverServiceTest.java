package de.codecentric.bb.cover;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoverServiceTest {
    private static final String VALID_ISBN = "3551551685";
    private static final String INVALID_ISBN = "9783423139557";
    public static final byte[] VALID_IMAGE = new byte[1001];
    public static final byte[] INVALID_IMAGE = new byte[1];

    @Mock
    private CoverRepository repository;

    private CoverService coverService;

    @Captor
    private ArgumentCaptor<Cover> coverCaptor;

    @BeforeEach
    void setUp() {
        coverService = new CoverService(repository);
    }

    @Test
    void shouldFindExistingCoverByIsbn() {
        Cover testCover = new Cover(10L, VALID_ISBN, VALID_IMAGE);
        when(repository.findCoverByIsbn(VALID_ISBN)).thenReturn(testCover);

        Cover coverResult = coverService.getCoverByIsbn(VALID_ISBN);

        assertThat(coverResult, is(testCover));
    }

    @Test
    @Disabled
    void shouldThrowForMalformedUrl() {
        // TODO: No idea how to test the IOException possibly thrown from fetchCoverFromUrl without PowerMock
        assertThrows(CoverNotFoundException.class, () -> coverService.getCoverByIsbn(INVALID_ISBN));
    }

    @Test
    void shouldReturnNullForIsbnWithoutCover() {
        when(repository.findCoverByIsbn(INVALID_ISBN)).thenReturn(null);

        Cover cover = coverService.getCoverByIsbn(INVALID_ISBN);

        assertThat(cover, is(nullValue()));
    }

    @Test
    void shouldCreateCoverWhenNoneIsFound() throws IOException {
        MockMultipartFile file = new MockMultipartFile("testImage.jpg", "some pixels".getBytes());

        coverService.setCover(VALID_ISBN, file);

        verify(repository).save(coverCaptor.capture());
        Cover cover = coverCaptor.getValue();
        assertThat(cover.getImage(), is(file.getBytes()));
        assertThat(cover.getIsbn(), is(VALID_ISBN));
    }

    @Test
    void shouldUpdateCoverImageWhenCoverAlreadyExists() throws IOException {
        Cover testCover = Cover.builder().isbn(VALID_ISBN).image("some old cover".getBytes()).build();
        when(repository.findCoverByIsbn(VALID_ISBN)).thenReturn(testCover);
        MockMultipartFile file = new MockMultipartFile("testImage.jpg", "some pixels".getBytes());

        coverService.setCover(VALID_ISBN, file);

        verify(repository).save(coverCaptor.capture());
        Cover cover = coverCaptor.getValue();
        assertThat(cover.getImage(), is(file.getBytes()));
        assertThat(cover.getIsbn(), is(VALID_ISBN));
    }

    @Test
    void shouldThrowCoverUploadFailedExceptionForCorruptedImages() {
        MockMultipartFile file = new MockMultipartFile("testImage.jpg", null);

        assertThrows(CoverUploadFailedException.class, () -> coverService.setCover(VALID_ISBN, file));
    }
}