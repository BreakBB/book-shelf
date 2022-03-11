package de.codecentric.bb.book;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.codecentric.bb.cover.Cover;
import de.codecentric.bb.cover.CoverService;
import java.time.LocalDate;
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

    @Mock
    CoverService coverService;

    @InjectMocks
    BookService bookService;

    private final String ISBN = "isbn";

    Book testBook = new Book(10L, ISBN, "title", "author", LocalDate.now(), false);

    @Test
    void getAllBooks() {
        when(repository.findAll()).thenReturn(List.of(testBook));

        List<Book> allBooks = bookService.findAllBooks();

        verify(repository).findAll();
        assertThat(allBooks.size(), is(1));
    }

    @Test
    void deleteByIsbn() {
        when(repository.deleteByIsbn(ISBN)).thenReturn(15L);

        long deletedBooks = bookService.deleteByIsbn(ISBN);

        assertThat(deletedBooks, is(15L));
    }

    @Test
    void updateBook() {
        when(repository.findBookByIsbn(ISBN)).thenReturn(testBook);
        Book updatedBook = Book.builder().isbn(ISBN).author("def").build();

        Book book = bookService.updateBook(ISBN, updatedBook);

        verify(repository).save(testBook);
        assertThat(book.getIsbn(), is(ISBN));
        assertThat(book.getAuthor(), is("def"));
        assertThat(book.getTitle(), is(nullValue()));
        assertThat(book.getReleaseDate(), is(nullValue()));
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

    @Nested
    class AddBook {

        @Test
        void shouldSaveNewBook() {
            when(coverService.getCoverByIsbn(ISBN)).thenReturn(null);

            bookService.addBook(testBook);

            verify(repository).save(testBook);
        }

        @Test
        void shouldSetCoverId() {
            Cover testCover = new Cover(10L, ISBN, null);
            when(coverService.getCoverByIsbn(ISBN)).thenReturn(testCover);
            when(repository.save(testBook)).thenReturn(testBook);

            Book bookResult = bookService.addBook(testBook);

            assertThat(bookResult.isHasCover(), is(true));
        }

        @Test
        void shouldThrowBookAlreadyExistsExceptionWhenBookAlreadyExists() {
            when(repository.findBookByIsbn(ISBN)).thenReturn(testBook);

            assertThrows(BookAlreadyExists.class, () -> bookService.addBook(testBook));
        }
    }
}