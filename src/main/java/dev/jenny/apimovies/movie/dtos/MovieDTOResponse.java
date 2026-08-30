package dev.jenny.apimovies.movie.dtos;

import java.util.Set;

public record MovieDTOResponse(Long id, String title, Set<String> genreNames, Integer releaseYear,
        Set<String> actorNames) {
}