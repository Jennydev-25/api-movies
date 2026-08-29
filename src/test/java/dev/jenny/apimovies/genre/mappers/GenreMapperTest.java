package dev.jenny.apimovies.genre.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.genre.dtos.GenreDTO;
import org.junit.jupiter.api.Test;

class GenreMapperTest {

    @Test
    void testToDTO() {
        GenreEntity genre = new GenreEntity(1L, "Terror");

        GenreDTO dto = GenreMapper.toDTO(genre);

        assertThat(dto.id(), is(equalTo(1L)));
        assertThat(dto.name(), is(equalTo("Terror")));
    }
}