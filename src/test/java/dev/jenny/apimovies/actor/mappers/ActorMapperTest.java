package dev.jenny.apimovies.actor.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.actor.dtos.ActorDTORequest;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;

import org.junit.jupiter.api.Test;

class ActorMapperTest {

    @Test
    void testToDTO() {
        ActorEntity actor = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        ActorDTOResponse dto = ActorMapper.toDTO(actor);
        assertThat(dto.id(), is(equalTo(1L)));
        assertThat(dto.name(), is(equalTo("Robert Downey Jr.")));
        assertThat(dto.nationality(), is(equalTo("American")));
        assertThat(dto.birthDate(), is(equalTo(LocalDate.of(1965, 4, 4))));
    }

    @Test
    void testConstructor_ShouldBePrivate() throws Exception {
        Constructor<ActorMapper> constructor = ActorMapper.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers()), is(equalTo(true)));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    void testToEntity() {
        ActorDTORequest dto = new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        ActorEntity actor = ActorMapper.toEntity(dto);
        assertThat(actor.getName(), is(equalTo("Robert Downey Jr.")));
        assertThat(actor.getNationality(), is(equalTo("American")));
        assertThat(actor.getBirthDate(), is(equalTo(LocalDate.of(1965, 4, 4))));
    }
}