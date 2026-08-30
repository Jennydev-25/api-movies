package dev.jenny.apimovies.movie.mappers;

import dev.jenny.apimovies.movie.MovieEntity;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;

import java.util.stream.Collectors;

public class MovieMapper {

    private MovieMapper() {
    }

    public static MovieDTOResponse toDTO(MovieEntity entity) {
        return new MovieDTOResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getGenres().stream().map(genre -> genre.getName()).collect(Collectors.toSet()),
                entity.getReleaseYear().getReleaseYear(),
                entity.getActors().stream().map(actor -> actor.getName()).collect(Collectors.toSet()));
    }
}