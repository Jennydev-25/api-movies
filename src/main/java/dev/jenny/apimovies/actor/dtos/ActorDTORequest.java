package dev.jenny.apimovies.actor.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record ActorDTORequest(
        @NotBlank(message = "Name cannot be empty") @NotNull(message = "Name cannot be null") String name,
        @NotBlank(message = "Nationality cannot be empty") @NotNull(message = "Nationality cannot be null") String nationality,
        @NotNull(message = "Birth date cannot be null") @Past(message = "Birth date must be in the past") LocalDate birthDate) {
}