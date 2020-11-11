package de.codecentric.bb.book;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String isbn) {
        super("Book with isbn '" + isbn + "' could not be found.");
    }
}
