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

    @Test
    void testConstructor_ShouldBePrivate() throws Exception {
        Constructor<ReleaseYearMapper> constructor = ReleaseYearMapper.class.getDeclaredConstructor();

        assertThat(Modifier.isPrivate(constructor.getModifiers()), is(equalTo(true)));

        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void testToEntity() {
        ReleaseYearDTORequest dto = new ReleaseYearDTORequest(1994);

        ReleaseYearEntity releaseYear = ReleaseYearMapper.toEntity(dto);

        assertThat(releaseYear.getReleaseYear(), is(equalTo(1994)));
    }
}