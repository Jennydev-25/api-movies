package dev.jenny.apimovies.releaseyear.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTORequest;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;
import org.junit.jupiter.api.Test;

class ReleaseYearMapperTest {

    @Test
    void testToDTO() {
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 1994);

        ReleaseYearDTOResponse dto = ReleaseYearMapper.toDTO(releaseYear);

        assertThat(dto.id(), is(equalTo(1L)));
        assertThat(dto.releaseYear(), is(equalTo(1994)));
    }
}