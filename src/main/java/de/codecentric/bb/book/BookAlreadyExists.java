package de.codecentric.bb.book;

public class BookAlreadyExists extends RuntimeException {

    public BookAlreadyExists(String isbn) {
        super("Book with isbn '" + isbn + "' already exists.");
    }
}
