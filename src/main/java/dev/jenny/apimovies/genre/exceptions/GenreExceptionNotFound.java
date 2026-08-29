package dev.jenny.apimovies.genre.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Genre not found")
public class GenreExceptionNotFound extends GenreException {

    public GenreExceptionNotFound(String message) {
        super(message);
    }

    public GenreExceptionNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}