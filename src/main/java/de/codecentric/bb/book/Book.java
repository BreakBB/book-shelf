package de.codecentric.bb.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String isbn;
    private String title;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private Long coverId;

    public void updateData(Book updatedBook) {
        this.isbn = updatedBook.isbn;
        this.title = updatedBook.title;
        this.author = updatedBook.author;
        this.releaseDate = updatedBook.releaseDate;
    }
}
