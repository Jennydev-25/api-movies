package dev.jenny.apimovies.movie.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.Set;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.movie.MovieEntity;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import org.junit.jupiter.api.Test;

class MovieMapperTest {

    @Test
    void testToDTO() {
        GenreEntity genre = new GenreEntity(1L, "Drama");
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 2008);
        ActorEntity actor = new ActorEntity(1L, "Jack Scanlon", "English", LocalDate.of(1998, 8, 6));
        MovieEntity movie = new MovieEntity(1L, "El niño con el pijama de rayas", Set.of(genre), releaseYear,
                Set.of(actor));

        MovieDTOResponse dto = MovieMapper.toDTO(movie);

        assertThat(dto.id(), is(equalTo(1L)));
        assertThat(dto.title(), is(equalTo("El niño con el pijama de rayas")));
        assertThat(dto.genreNames(), is(equalTo(Set.of("Drama"))));
        assertThat(dto.releaseYear(), is(equalTo(2008)));
        assertThat(dto.actorNames(), is(equalTo(Set.of("Jack Scanlon"))));
    }
}