package dev.jenny.apimovies.genre.mappers;

import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;

public class GenreMapper {

    private GenreMapper() {
    }

    public static GenreDTOResponse toDTO(GenreEntity entity) {
        return new GenreDTOResponse(entity.getId(), entity.getName());
    }
}