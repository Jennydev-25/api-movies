package dev.jenny.apimovies.movie.builder;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.movie.MovieEntity;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import java.util.Set;

public class MovieEntityBuilder implements IMovieBuilder {

    private final MovieEntity entity;

    public MovieEntityBuilder() {
        entity = new MovieEntity();
    }

    @Override
    public MovieEntityBuilder id(Long id) {
        entity.setId(id);
        return this;
    }

    @Override
    public MovieEntityBuilder title(String title) {
        entity.setTitle(title);
        return this;
    }

    @Override
    public MovieEntityBuilder genres(Set<GenreEntity> genres) {
        entity.setGenres(genres);
        return this;
    }

    @Override
    public MovieEntityBuilder releaseYear(ReleaseYearEntity releaseYear) {
        entity.setReleaseYear(releaseYear);
        return this;
    }

    @Override
    public MovieEntityBuilder actors(Set<ActorEntity> actors) {
        entity.setActors(actors);
        return this;
    }

    @Override
    public MovieEntity build() {
        return entity;
    }
}