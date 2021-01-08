package de.codecentric.bb.cover;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public byte[] getCoverByIsbn(@PathVariable String isbn) {
        log.info("Finding cover for isbn: {}", isbn);
        return coverService.getCoverByIsbn(isbn).getImage();
    }
}
