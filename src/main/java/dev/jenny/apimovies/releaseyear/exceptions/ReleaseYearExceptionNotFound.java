package dev.jenny.apimovies.releaseyear.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Release year not found")
public class ReleaseYearExceptionNotFound extends ReleaseYearException {

    public ReleaseYearExceptionNotFound(String message) {
        super(message);
    }
}