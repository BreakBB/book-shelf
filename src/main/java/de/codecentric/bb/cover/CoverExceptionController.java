package de.codecentric.bb.cover;

import de.codecentric.bb.book.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
public abstract class CoverExceptionController {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(CoverNotFoundException.class)
    @ResponseBody
    public String handleCoverNotFoundException(Exception ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(CoverTooSmallException.class)
    @ResponseBody
    public String handleCoverTooSmallException(Exception ex) {
        return ex.getMessage();
    }
}
