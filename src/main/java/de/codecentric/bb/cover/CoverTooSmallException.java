package de.codecentric.bb.cover;

public class CoverTooSmallException extends RuntimeException {

    public CoverTooSmallException(String isbn) {
        super("Cover fetched for book with isbn '" + isbn + "' is too small and most likely invalid.");
    }
}
