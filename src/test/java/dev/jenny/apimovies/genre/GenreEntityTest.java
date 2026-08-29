package dev.jenny.apimovies.genre;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class GenreEntityTest {

    @Test
    void testGenreEntity_InitializationWithIdAndName() {
        GenreEntity genre = new GenreEntity(1L, "Ciencia ficción");

        assertThat(genre, is(instanceOf(GenreEntity.class)));
        assertThat(genre.getClass().getDeclaredFields().length, is(equalTo(2)));
    }

    @Test
    void testGenreEntity() {
        GenreEntity genre = new GenreEntity(1L, "Ciencia ficción");

        assertThat(genre.getId(), is(equalTo(1L)));
        assertThat(genre.getName(), is(equalTo("Ciencia ficción")));
    }

    @Test
    void testGenreEntity_Builder() {
        GenreEntity genre = GenreEntity.builder()
                .id(1L)
                .name("Terror")
                .build();

        assertThat(genre, instanceOf(GenreEntity.class));
        assertThat(genre.getId(), is(equalTo(1L)));
        assertThat(genre.getName(), is(equalTo("Terror")));
    }
}