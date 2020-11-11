package de.codecentric.bb.book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    BookService bookService;

    Book testBook = new Book(10L, "isbn", "title", "author", LocalDateTime.now());

    @Test
    void getAllBooks() {
        when(repository.findAll()).thenReturn(List.of(testBook));

        List<Book> allBooks = bookService.getAllBooks();

        verify(repository).findAll();
        assertThat(allBooks.size(), is(1));
    }

    @Test
    void addBook() {
        when(repository.save(testBook)).thenReturn(testBook);

        Book bookResult = bookService.addBook(testBook);

        verify(repository).save(testBook);
        assertThat(bookResult, is(testBook));
    }
}