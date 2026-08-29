package dev.jenny.apimovies.genre.builder;

import dev.jenny.apimovies.genre.GenreEntity;

public interface IGenreBuilder {

    public GenreEntityBuilder id(Long id);

    public GenreEntityBuilder name(String name);

    public GenreEntity build();
}