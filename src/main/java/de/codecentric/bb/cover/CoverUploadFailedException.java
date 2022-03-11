package de.codecentric.bb.cover;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CoverUploadFailedException extends RuntimeException {
    public CoverUploadFailedException(String isbn) {
        super("Failed to upload cover for book with isbn '" + isbn + "'.");
    }
}
