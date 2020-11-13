package de.codecentric.bb.cover;

public class CoverNotFoundException extends RuntimeException {

    public CoverNotFoundException(String isbn) {
        super("Cover for book with isbn '" + isbn + "' could not be found.");
    }
}
