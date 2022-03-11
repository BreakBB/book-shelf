package de.codecentric.bb.book;

import de.codecentric.bb.cover.Cover;
import de.codecentric.bb.cover.CoverService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CoverService coverService;

    @Autowired
    public BookService(BookRepository bookRepository, CoverService coverService) {
        this.bookRepository = bookRepository;
        this.coverService = coverService;
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
        if (bookRepository.findBookByIsbn(newBook.getIsbn()) != null) {
            throw new BookAlreadyExists(newBook.getIsbn());
        }

        Cover cover = coverService.getCoverByIsbn(newBook.getIsbn());
        if (cover != null) {
            newBook.setHasCover(cover.getId() != null);
        }

        return bookRepository.save(newBook);
    }

    public long deleteByIsbn(String isbn) {
        long deletedBookAmount = bookRepository.deleteByIsbn(isbn);
        log.info("Deleted {} books with isbn '{}'", deletedBookAmount, isbn);
        return deletedBookAmount;
    }

    public Book updateBook(String isbn, Book updatedBook) {
        Book currentBook = bookRepository.findBookByIsbn(isbn);
        currentBook.updateData(updatedBook);
        bookRepository.save(currentBook);
        return currentBook;
    }
}
