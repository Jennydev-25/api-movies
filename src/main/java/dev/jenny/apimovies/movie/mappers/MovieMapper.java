package dev.jenny.apimovies.movie.mappers;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.movie.MovieEntity;
import dev.jenny.apimovies.movie.dtos.MovieDTORequest;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class MovieMapper {

    private MovieMapper() {
    }

    public static MovieDTOResponse toDTO(MovieEntity entity) {
        return new MovieDTOResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getGenres().stream().map(genre -> genre.getName()).collect(Collectors.toSet()),
                entity.getReleaseYear().getReleaseYear(),
                entity.getActors().stream().map(actor -> actor.getName()).collect(Collectors.toSet()));
    }

    public static MovieEntity toEntity(MovieDTORequest dto, Set<GenreEntity> genres, ReleaseYearEntity releaseYear,
            Set<ActorEntity> actors) {
        MovieEntity movie = new MovieEntity();
        movie.setTitle(dto.title());
        movie.setGenres(genres);
        movie.setReleaseYear(releaseYear);
        movie.setActors(actors);
        return movie;
    }
}