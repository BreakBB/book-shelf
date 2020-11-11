package de.codecentric.bb.book;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/books")
public class BookController extends BookExceptionController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        log.info("Finding all books");
        return bookService.findAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        log.info("Finding book with isbn: {}", isbn);
        return bookService.findByIsbn(isbn);
    }

    @PostMapping
    public Book addNewBook(@RequestBody Book newBook) {
        log.info("Adding new book: {}", newBook);
        return bookService.addBook(newBook);
    }

    @DeleteMapping("/{isbn}")
    public long deleteBookByIsbn(@PathVariable String isbn) {
        log.info("Deleting book with isbn: {}", isbn);
        return bookService.deleteByIsbn(isbn);
    }
}
