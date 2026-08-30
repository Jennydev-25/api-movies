package dev.jenny.apimovies.releaseyear;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReleaseYearServiceImplTest {

    @InjectMocks
    private ReleaseYearServiceImpl service;

    @Mock
    private ReleaseYearRepository repository;

    @Test
    void testGetEntities() {
        List<ReleaseYearEntity> mock = List.of(new ReleaseYearEntity(1L, 1994), new ReleaseYearEntity(2L, 2001));
        when(repository.findAll()).thenReturn(mock);

        List<ReleaseYearDTOResponse> releaseYears = service.getEntities();

        assertThat(releaseYears.size(), is(equalTo(2)));
        assertThat(releaseYears.get(0).releaseYear(), is(equalTo(1994)));
    }

    @Test
    void testGetById() {
        ReleaseYearEntity releaseYearMock = new ReleaseYearEntity(1L, 1994);

        when(repository.findById(1L)).thenReturn(Optional.of(releaseYearMock));
        ReleaseYearDTOResponse releaseYear = service.getById(1L);

        assertThat(releaseYear.id(), is(equalTo(1L)));
        assertThat(releaseYear.releaseYear(), is(equalTo(1994)));
    }
}
