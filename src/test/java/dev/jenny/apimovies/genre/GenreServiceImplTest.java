package dev.jenny.apimovies.genre;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.List;

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

        List<GenreEntity> genres = service.getEntities();

        assertThat(genres.size(), is(equalTo(2)));
        assertThat(genres.get(0).getName(), is(equalTo("Terror")));
    }
}