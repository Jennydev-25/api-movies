package dev.jenny.apimovies.movie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.Set;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;

import org.junit.jupiter.api.Test;

class MovieEntityTest {

    @Test
    void testMovieEntity_InitializationWithAllFields() {
        GenreEntity genre = new GenreEntity(1L, "Drama");
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 2008);
        ActorEntity actor = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));

        MovieEntity movie = new MovieEntity(1L, "El niño con el pijama de rayas", Set.of(genre), releaseYear,
                Set.of(actor));

        assertThat(movie, is(instanceOf(MovieEntity.class)));
        assertThat(movie.getClass().getDeclaredFields().length, is(equalTo(5)));
    }

    @Test
    void testMovieEntity() {
        GenreEntity genre = new GenreEntity(1L, "Drama");
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 2008);
        ActorEntity actor = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));

        MovieEntity movie = new MovieEntity(1L, "El niño con el pijama de rayas", Set.of(genre), releaseYear,
                Set.of(actor));

        assertThat(movie.getId(), is(equalTo(1L)));
        assertThat(movie.getTitle(), is(equalTo("El niño con el pijama de rayas")));
        assertThat(movie.getGenres(), is(equalTo(Set.of(genre))));
        assertThat(movie.getReleaseYear(), is(equalTo(releaseYear)));
        assertThat(movie.getActors(), is(equalTo(Set.of(actor))));
    }

    @Test
    void testMovieEntity_Builder() {
        GenreEntity genre = new GenreEntity(1L, "Drama");
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 2008);
        ActorEntity actor = new ActorEntity(1L, "Jack Scanlon", "English", LocalDate.of(1998, 8, 6));

        MovieEntity movie = MovieEntity.builder()
                .id(1L)
                .title("El niño con el pijama de rayas")
                .genres(Set.of(genre))
                .releaseYear(releaseYear)
                .actors(Set.of(actor))
                .build();

        assertThat(movie, instanceOf(MovieEntity.class));
        assertThat(movie.getId(), is(equalTo(1L)));
        assertThat(movie.getTitle(), is(equalTo("El niño con el pijama de rayas")));
        assertThat(movie.getGenres(), is(equalTo(Set.of(genre))));
        assertThat(movie.getReleaseYear(), is(equalTo(releaseYear)));
        assertThat(movie.getActors(), is(equalTo(Set.of(actor))));
    }
}