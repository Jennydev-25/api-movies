package dev.jenny.apimovies.genre.mappers;

import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.genre.dtos.GenreDTO;

public class GenreMapper {

    public static GenreDTO toDTO(GenreEntity entity) {
        return new GenreDTO(entity.getId(), entity.getName());
    }
}