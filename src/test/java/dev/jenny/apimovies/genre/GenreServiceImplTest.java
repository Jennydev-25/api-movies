package dev.jenny.apimovies.genre;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import dev.jenny.apimovies.genre.dtos.GenreDTORequest;
import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;
import dev.jenny.apimovies.genre.exceptions.GenreExceptionNotFound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

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

    @Test
    void testStoreEntity_ShouldSaveAndReturnGenre() {
        GenreDTORequest dtoRequest = new GenreDTORequest("Terror");
        GenreEntity genreSaved = new GenreEntity(1L, "Terror");

        when(repository.save(any(GenreEntity.class))).thenReturn(genreSaved);

        GenreDTOResponse genre = service.storeEntity(dtoRequest);

        assertThat(genre.id(), is(equalTo(1L)));
        assertThat(genre.name(), is(equalTo("Terror")));
    }

    @Test
    void testStoreEntity_ShouldReturnNull_WhenGenreAlreadyExists() {
        GenreDTORequest dtoRequest = new GenreDTORequest("Terror");
        GenreEntity existingGenre = new GenreEntity(1L, "Terror");

        when(repository.findAll(ArgumentMatchers.<Example<GenreEntity>>any())).thenReturn(List.of(existingGenre));

        GenreDTOResponse genre = service.storeEntity(dtoRequest);

        assertThat(genre, is(equalTo(null)));
    }
}