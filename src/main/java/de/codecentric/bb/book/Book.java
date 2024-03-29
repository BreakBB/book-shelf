package de.codecentric.bb.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private String title;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private boolean hasCover;

    public void updateData(Book updatedBook) {
        this.isbn = updatedBook.isbn;
        this.title = updatedBook.title;
        this.author = updatedBook.author;
        this.releaseDate = updatedBook.releaseDate;
    }
}
