package de.codecentric.bb.book;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book findByIsbn(String isbn) {
        Book bookResult = bookRepository.findBookByIsbn(isbn);
        if (bookResult == null) {
            throw new BookNotFoundException(isbn);
        }

        return bookResult;
    }

    public Book addBook(Book newBook) {
        return bookRepository.save(newBook);
    }

    public long deleteByIsbn(String isbn) {
        long deletedBookAmount = bookRepository.deleteByIsbn(isbn);
        log.info("Deleted {} books with isbn '{}'", deletedBookAmount, isbn);
        return deletedBookAmount;
    }
}
