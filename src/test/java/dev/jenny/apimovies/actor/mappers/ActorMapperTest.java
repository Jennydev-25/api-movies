package dev.jenny.apimovies.actor.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;

import dev.jenny.apimovies.actor.ActorEntity;
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
}