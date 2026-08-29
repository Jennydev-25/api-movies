package dev.jenny.apimovies.genre;

import java.util.List;

public interface InterfaceGenreService {

    List<GenreEntity> getEntities();

    GenreEntity getById(Long id);
}