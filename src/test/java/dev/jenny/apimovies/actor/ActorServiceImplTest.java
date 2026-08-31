package dev.jenny.apimovies.actor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import dev.jenny.apimovies.actor.dtos.ActorDTORequest;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;
import dev.jenny.apimovies.actor.exceptions.ActorExceptionNotFound;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

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

    @Test
    void testGetById_NotFound_ShouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        ActorExceptionNotFound exception = assertThrows(ActorExceptionNotFound.class,
                () -> service.getById(1L));
        assertThat(exception.getMessage(), is(equalTo("Actor not found. Id 1 does not exist.")));
    }

    @Test
    void testStoreEntity_ShouldSaveAndReturnActor() {
        ActorDTORequest dtoRequest = new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        ActorEntity actorSaved = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        when(repository.save(any(ActorEntity.class))).thenReturn(actorSaved);
        ActorDTOResponse actor = service.storeEntity(dtoRequest);
        assertThat(actor.id(), is(equalTo(1L)));
        assertThat(actor.name(), is(equalTo("Robert Downey Jr.")));
    }

    @Test
    void testStoreEntity_ShouldReturnNull_WhenActorAlreadyExists() {
        ActorDTORequest dtoRequest = new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        ActorEntity existingActor = new ActorEntity(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));

        when(repository.findAll(ArgumentMatchers.<Example<ActorEntity>>any())).thenReturn(List.of(existingActor));

        ActorDTOResponse actor = service.storeEntity(dtoRequest);

        assertThat(actor, is(equalTo(null)));
    }

    @Test
    void testUpdateEntity_ShouldUpdateAndReturnActor() {
        ActorDTORequest dtoRequest = new ActorDTORequest("Robert Downey Jr.", "British", LocalDate.of(1965, 4, 4));
        ActorEntity actorUpdated = new ActorEntity(1L, "Robert Downey Jr.", "British", LocalDate.of(1965, 4, 4));

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(ActorEntity.class))).thenReturn(actorUpdated);

        ActorDTOResponse actor = service.updateEntity(1L, dtoRequest);

        assertThat(actor.id(), is(equalTo(1L)));
        assertThat(actor.nationality(), is(equalTo("British")));
    }

    @Test
    void testUpdateEntity_NotFound_ShouldThrowException() {
        when(repository.existsById(1L)).thenReturn(false);

        ActorExceptionNotFound exception = assertThrows(ActorExceptionNotFound.class,
                () -> service.updateEntity(1L,
                        new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4))));

        assertThat(exception.getMessage(), is(equalTo("Actor not found. Id 1 does not exist.")));
    }

    @Test
    void testUpdateEntity_ShouldReturnNull_WhenActorAlreadyExists() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsByNameAndBirthDateAndIdNot("Robert Downey Jr.", LocalDate.of(1965, 4, 4), 1L))
                .thenReturn(true);

        ActorDTOResponse actor = service.updateEntity(1L,
                new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4)));

        assertThat(actor, is(equalTo(null)));
    }
}