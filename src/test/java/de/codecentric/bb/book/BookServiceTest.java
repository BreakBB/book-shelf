package de.codecentric.bb.book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
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

    private final String ISBN = "isbn";

    Book testBook = new Book(10L, ISBN, "title", "author", LocalDateTime.now());

    @Test
    void getAllBooks() {
        when(repository.findAll()).thenReturn(List.of(testBook));

        List<Book> allBooks = bookService.findAllBooks();

        verify(repository).findAll();
        assertThat(allBooks.size(), is(1));
    }

    @Nested
    class FindByIsbn {
        @Test
        void shouldReturnABookForAValidIsbn() {
            when(repository.findBookByIsbn(ISBN)).thenReturn(testBook);

            Book bookResult = bookService.findByIsbn(ISBN);

            verify(repository).findBookByIsbn(ISBN);
            assertThat(bookResult, is(testBook));
        }

        @Test
        void shouldNotReturnABookForInvalidIsbn() {
            when(repository.findBookByIsbn("invalid")).thenReturn(null);

            assertThrows(BookNotFoundException.class, () -> bookService.findByIsbn("invalid"));
        }
    }

    @Test
    void addBook() {
        when(repository.save(testBook)).thenReturn(testBook);

        Book bookResult = bookService.addBook(testBook);

        verify(repository).save(testBook);
        assertThat(bookResult, is(testBook));
    }
}