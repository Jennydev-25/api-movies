package dev.jenny.apimovies.genre.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GenreDTORequest(
        @NotBlank(message = "Name cannot be empty")
        @NotNull(message = "Name cannot be null")
        String name) {
}