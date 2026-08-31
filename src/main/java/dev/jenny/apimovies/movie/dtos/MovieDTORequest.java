package dev.jenny.apimovies.movie.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record MovieDTORequest(
        @NotBlank(message = "Title cannot be empty") @NotNull(message = "Title cannot be null") String title,
        @NotEmpty(message = "Genres cannot be empty") @NotNull(message = "Genres cannot be null") Set<Long> genreIds,
        @NotNull(message = "Release year cannot be null") Long releaseYearId,
        @NotEmpty(message = "Actors cannot be empty") @NotNull(message = "Actors cannot be null") Set<Long> actorIds) {
}