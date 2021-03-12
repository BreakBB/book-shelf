package de.codecentric.bb.book;

import de.codecentric.bb.cover.CoverService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/books")
public class BookController extends BookExceptionController {

    private final BookService bookService;
    private final CoverService coverService;

    @Autowired
    public BookController(BookService bookService, CoverService coverService) {
        this.bookService = bookService;
        this.coverService = coverService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        log.info("Finding all books");
        List<Book> books = bookService.findAllBooks();
        log.info("Found {} books", books.size());
        return books;
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        log.info("Finding book with isbn: {}", isbn);
        return bookService.findByIsbn(isbn);
    }

    @GetMapping(value = "/{isbn}/cover", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCoverByIsbn(@PathVariable String isbn) {
        log.info("Finding cover for book with isbn: {}", isbn);
        return coverService.getCoverByIsbn(isbn).getImage();
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
