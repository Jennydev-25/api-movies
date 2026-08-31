package dev.jenny.apimovies.globals;

import java.util.HashMap;
import java.util.Map;

import dev.jenny.apimovies.actor.exceptions.ActorException;
import dev.jenny.apimovies.actor.exceptions.ActorExceptionNotFound;
import dev.jenny.apimovies.genre.exceptions.GenreException;
import dev.jenny.apimovies.genre.exceptions.GenreExceptionNotFound;
import dev.jenny.apimovies.movie.exceptions.MovieException;
import dev.jenny.apimovies.movie.exceptions.MovieExceptionNotFound;
import dev.jenny.apimovies.releaseyear.exceptions.ReleaseYearException;
import dev.jenny.apimovies.releaseyear.exceptions.ReleaseYearExceptionNotFound;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenreExceptionNotFound.class)
    public ResponseEntity<String> handleGenreNotFound(GenreExceptionNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(GenreException.class)
    public ResponseEntity<String> handleGenreException(GenreException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ReleaseYearExceptionNotFound.class)
    public ResponseEntity<String> handleReleaseYearNotFound(ReleaseYearExceptionNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ReleaseYearException.class)
    public ResponseEntity<String> handleReleaseYearException(ReleaseYearException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ActorExceptionNotFound.class)
    public ResponseEntity<String> handleActorNotFound(ActorExceptionNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ActorException.class)
    public ResponseEntity<String> handleActorException(ActorException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(MovieExceptionNotFound.class)
    public ResponseEntity<String> handleMovieNotFound(MovieExceptionNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(MovieException.class)
    public ResponseEntity<String> handleMovieException(MovieException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}