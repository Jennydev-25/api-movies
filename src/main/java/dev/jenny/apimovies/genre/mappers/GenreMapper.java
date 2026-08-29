package dev.jenny.apimovies.genre.mappers;

import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.genre.dtos.GenreDTORequest;
import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;

public class GenreMapper {

    private GenreMapper() {
    }

    public static GenreDTOResponse toDTO(GenreEntity entity) {
        return new GenreDTOResponse(entity.getId(), entity.getName());
    }

    public static GenreEntity toEntity(GenreDTORequest dtoRequest) {
        GenreEntity genre = new GenreEntity();
        genre.setName(dtoRequest.name());
        return genre;
    }
}