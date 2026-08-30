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
}