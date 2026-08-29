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
}