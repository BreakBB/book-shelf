package de.codecentric.bb.cover;

import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/covers")
public class CoverController {
    private final CoverService coverService;

    @Autowired
    public CoverController(CoverService coverService) {
        this.coverService = coverService;
    }

    @GetMapping(value = "/{isbn}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCoverByIsbn(@PathVariable String isbn, HttpServletResponse response) {
        log.info("Finding cover for isbn: {}", isbn);
        Cover cover = coverService.getCoverByIsbn(isbn);
        if (cover != null) {
            return cover.getImage();
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return null;
    }
}
