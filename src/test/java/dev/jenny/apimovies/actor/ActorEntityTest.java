package dev.jenny.apimovies.actor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ActorEntityTest {

    @Test
    void testActorEntity_InitializationWithAllFields() {
        ActorEntity actor = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        assertThat(actor, is(instanceOf(ActorEntity.class)));
        assertThat(actor.getClass().getDeclaredFields().length, is(equalTo(4)));
    }

    @Test
    void testActorEntity() {
        ActorEntity actor = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        assertThat(actor.getId(), is(equalTo(1L)));
        assertThat(actor.getName(), is(equalTo("Robert Downey Jr.")));
        assertThat(actor.getNationality(), is(equalTo("American")));
        assertThat(actor.getBirthDate(), is(equalTo(LocalDate.of(1965, 4, 4))));
    }

    @Test
    void testActorEntity_Builder() {
        ActorEntity actor = ActorEntity.builder()
                .id(1L)
                .name("Robert Downey Jr.")
                .nationality("American")
                .birthDate(LocalDate.of(1965, 4, 4))
                .build();
        assertThat(actor, instanceOf(ActorEntity.class));
        assertThat(actor.getId(), is(equalTo(1L)));
        assertThat(actor.getName(), is(equalTo("Robert Downey Jr.")));
        assertThat(actor.getNationality(), is(equalTo("American")));
        assertThat(actor.getBirthDate(), is(equalTo(LocalDate.of(1965, 4, 4))));
    }
}