package dev.jenny.apimovies.genre;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;
import dev.jenny.apimovies.genre.exceptions.GenreExceptionNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @InjectMocks
    private GenreServiceImpl service;

    @Mock
    private GenreRepository repository;

    @Test
    void testGetEntities() {
        List<GenreEntity> mock = List.of(new GenreEntity(1L, "Terror"), new GenreEntity(2L, "Comedia"));
        when(repository.findAll()).thenReturn(mock);

        List<GenreDTOResponse> genres = service.getEntities();

        assertThat(genres.size(), is(equalTo(2)));
        assertThat(genres.get(0).name(), is(equalTo("Terror")));
    }

    @Test
    void testGetById() {
        GenreEntity genreMock = new GenreEntity(1L, "Terror");

        when(repository.findById(1L)).thenReturn(Optional.of(genreMock));
        GenreDTOResponse genre = service.getById(1L);

        assertThat(genre.id(), is(equalTo(1L)));
        assertThat(genre.name(), is(equalTo("Terror")));
    }

    @Test
    void testGetById_NotFound_ShouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        GenreExceptionNotFound exception = assertThrows(GenreExceptionNotFound.class, () -> service.getById(1L));

        assertThat(exception.getMessage(), is(equalTo("Genre not found. Id 1 does not exist.")));
    }
}