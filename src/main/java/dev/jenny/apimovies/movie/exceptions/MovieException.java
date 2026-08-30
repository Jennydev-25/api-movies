package dev.jenny.apimovies.movie.exceptions;

public class MovieException extends RuntimeException {
    public MovieException(String message) {
        super(message);
    }
}