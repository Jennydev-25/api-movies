package dev.jenny.apimovies.actor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

    @InjectMocks
    private ActorServiceImpl service;

    @Mock
    private ActorRepository repository;

    @Test
    void testGetEntities() {
        List<ActorEntity> mock = List.of(
                new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4)),
                new ActorEntity(2L, "Scarlett Johansson", "American", LocalDate.of(1984, 11, 22)));
        when(repository.findAll()).thenReturn(mock);
        List<ActorDTOResponse> actors = service.getEntities();
        assertThat(actors.size(), is(equalTo(2)));
        assertThat(actors.get(0).name(), is(equalTo("Robert Downey Jr.")));
    }

    @Test
    void testGetById() {
        ActorEntity actorMock = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        when(repository.findById(1L)).thenReturn(Optional.of(actorMock));
        ActorDTOResponse actor = service.getById(1L);
        assertThat(actor.id(), is(equalTo(1L)));
        assertThat(actor.name(), is(equalTo("Robert Downey Jr.")));
    }
}