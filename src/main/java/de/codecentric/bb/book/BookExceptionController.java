package de.codecentric.bb.book;

import de.codecentric.bb.cover.CoverNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public abstract class BookExceptionController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseBody
    public String handleBookNotFoundException(BookNotFoundException ex) {
        log.info(ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(BookAlreadyExists.class)
    @ResponseBody
    public String handleBookAlreadyExistsException(BookAlreadyExists ex) {
        log.info(ex.getMessage());
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(CoverNotFoundException.class)
    @ResponseBody
    public String handleCoverNotFoundException(CoverNotFoundException ex) {
        return ex.getMessage();
    }
}
