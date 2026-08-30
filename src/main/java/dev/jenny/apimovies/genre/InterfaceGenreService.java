package dev.jenny.apimovies.genre;

import java.util.List;

import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;

public interface InterfaceGenreService {

    List<GenreDTOResponse> getEntities();

    GenreDTOResponse getById(Long id);
}