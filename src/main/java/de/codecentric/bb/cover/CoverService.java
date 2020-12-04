package de.codecentric.bb.cover;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoverService {

    private final CoverRepository repository;
    private static final String OPEN_LIBRARY_COVER_URL_TEMPLATE = "http://covers.openlibrary.org/b/isbn/%s-M.jpg";

    @Autowired
    public CoverService(CoverRepository repository) {
        this.repository = repository;
    }

    public Cover getCoverByIsbn(String isbn) {
        Cover cover = repository.findCoverByIsbn(isbn);

        if (cover == null) {
            cover = searchCoverInOpenLibrary(isbn);
        }

        if (!cover.hasValidData()) {
            log.info("Could not find valid cover for '{}'. Found cover has only {} bytes", isbn, cover.getImageSize());
            throw new CoverTooSmallException(isbn);
        }

        log.info("Saving new cover for isbn '{}' with image size of {} bytes", isbn, cover.getImageSize());
        return repository.save(cover);
    }

    private Cover searchCoverInOpenLibrary(String isbn) {
        Cover cover = new Cover();
        String coverUrl = String.format(OPEN_LIBRARY_COVER_URL_TEMPLATE, isbn);

        try {
            BufferedImage image = fetchCoverFromUrl(coverUrl);
            cover.setImage(image);
        } catch (IOException e) {
            log.warn("IOException while fetching cover for '{}'", isbn);
            throw new CoverNotFoundException(isbn);
        }
        return cover;
    }

    private BufferedImage fetchCoverFromUrl(String coverUrl) throws IOException {
        return ImageIO.read(new URL(coverUrl));
    }
}
