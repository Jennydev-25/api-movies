package dev.jenny.apimovies.movie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.jenny.apimovies.actor.ActorEntity;
import dev.jenny.apimovies.actor.ActorRepository;
import dev.jenny.apimovies.genre.GenreEntity;
import dev.jenny.apimovies.genre.GenreRepository;
import dev.jenny.apimovies.movie.dtos.MovieDTORequest;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;
import dev.jenny.apimovies.movie.exceptions.MovieExceptionNotFound;
import dev.jenny.apimovies.releaseyear.ReleaseYearEntity;
import dev.jenny.apimovies.releaseyear.ReleaseYearRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @InjectMocks
    private MovieServiceImpl service;

    @Mock
    private MovieRepository repository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private ReleaseYearRepository releaseYearRepository;

    @Mock
    private ActorRepository actorRepository;

    @Test
    void testGetEntities() {
        GenreEntity genre = new GenreEntity(1L, "Drama");
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 2008);
        ActorEntity actor = new ActorEntity(1L, "Jack Scanlon", "English", LocalDate.of(1998, 8, 6));
        List<MovieEntity> mock = List.of(
                new MovieEntity(1L, "El niño con el pijama de rayas", Set.of(genre), releaseYear, Set.of(actor)));

        when(repository.findAll()).thenReturn(mock);

        List<MovieDTOResponse> movies = service.getEntities();

        assertThat(movies.size(), is(equalTo(1)));
        assertThat(movies.get(0).title(), is(equalTo("El niño con el pijama de rayas")));
    }

    @Test
    void testGetById() {
        GenreEntity genre = new GenreEntity(1L, "Drama");
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 2008);
        ActorEntity actor = new ActorEntity(1L, "Jack Scanlon", "English", LocalDate.of(1998, 8, 6));
        MovieEntity movieMock = new MovieEntity(1L, "El niño con el pijama de rayas", Set.of(genre), releaseYear,
                Set.of(actor));

        when(repository.findById(1L)).thenReturn(Optional.of(movieMock));

        MovieDTOResponse movie = service.getById(1L);

        assertThat(movie.id(), is(equalTo(1L)));
        assertThat(movie.title(), is(equalTo("El niño con el pijama de rayas")));
    }

    @Test
    void testGetById_NotFound_ShouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MovieExceptionNotFound.class, () -> service.getById(1L));
    }

    @Test
    void testStoreEntity_ShouldSaveAndReturnMovie() {
        GenreEntity genre = new GenreEntity(1L, "Drama");
        ReleaseYearEntity releaseYear = new ReleaseYearEntity(1L, 2008);
        ActorEntity actor = new ActorEntity(1L, "Jack Scanlon", "English", LocalDate.of(1998, 8, 6));

        MovieDTORequest dtoRequest = new MovieDTORequest("El niño con el pijama de rayas", Set.of(1L), 1L,
                Set.of(1L));
        MovieEntity movieSaved = new MovieEntity(1L, "El niño con el pijama de rayas", Set.of(genre), releaseYear,
                Set.of(actor));

        when(releaseYearRepository.findById(1L)).thenReturn(Optional.of(releaseYear));
        when(genreRepository.findAllById(Set.of(1L))).thenReturn(List.of(genre));
        when(actorRepository.findAllById(Set.of(1L))).thenReturn(List.of(actor));
        when(repository.existsByTitleAndReleaseYear("El niño con el pijama de rayas", releaseYear)).thenReturn(false);
        when(repository.save(any(MovieEntity.class))).thenReturn(movieSaved);

        MovieDTOResponse movie = service.storeEntity(dtoRequest);

        assertThat(movie.id(), is(equalTo(1L)));
        assertThat(movie.title(), is(equalTo("El niño con el pijama de rayas")));
    }
}