package de.codecentric.bb.cover;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoverService {

    private static final String OPEN_LIBRARY_COVER_URL_TEMPLATE = "http://covers.openlibrary.org/b/isbn/%s-M.jpg";
    private static final int IMAGE_SIZE_THRESHOLD = 1000;

    public byte[] getCoverByIsbn(String isbn) {
        String coverUrl = String.format(OPEN_LIBRARY_COVER_URL_TEMPLATE, isbn);
        ByteArrayOutputStream outputStream;

        try {
            BufferedImage image = fetchCoverFromUrl(coverUrl);
            outputStream = getImageStream(image);
        } catch (IOException e) {
            log.warn("IOException while fetching cover for '{}'", isbn);
            throw new CoverNotFoundException(isbn);
        }

        if (outputStream.size() < IMAGE_SIZE_THRESHOLD) {
            log.info("Could not find valid cover for '{}'", isbn);
            throw new CoverTooSmallException(isbn);
        }

        log.info("Stream size is {}", outputStream.size());
        return outputStream.toByteArray();
    }

    private ByteArrayOutputStream getImageStream(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream;
    }

    private BufferedImage fetchCoverFromUrl(String coverUrl) throws IOException {
        return ImageIO.read(new URL(coverUrl));
    }
}
