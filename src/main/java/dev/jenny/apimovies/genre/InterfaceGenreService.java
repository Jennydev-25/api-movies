package dev.jenny.apimovies.genre;

import java.util.List;

import dev.jenny.apimovies.genre.dtos.GenreDTO;

public interface InterfaceGenreService {

    List<GenreDTO> getEntities();

    GenreDTO getById(Long id);
}