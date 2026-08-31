package dev.jenny.apimovies.movie.builder;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.movie.MovieEntity;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import java.util.Set;

public interface IMovieBuilder {
    public MovieEntityBuilder id(Long id);

    public MovieEntityBuilder title(String title);

    public MovieEntityBuilder genres(Set<GenreEntity> genres);

    public MovieEntityBuilder releaseYear(ReleaseYearEntity releaseYear);

    public MovieEntityBuilder actors(Set<ActorEntity> actors);

    public MovieEntity build();
}