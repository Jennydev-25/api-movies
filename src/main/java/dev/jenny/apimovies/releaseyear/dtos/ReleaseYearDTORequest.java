package dev.jenny.apimovies.releaseyear.dtos;

import jakarta.validation.constraints.NotNull;

public record ReleaseYearDTORequest(
        @NotNull(message = "Release year cannot be null") Integer releaseYear) {
}