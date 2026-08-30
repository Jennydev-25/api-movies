package dev.jenny.apimovies.movie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import dev.jenny.apimovies.movie.dtos.MovieDTORequest;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;
import dev.jenny.apimovies.movie.exceptions.MovieExceptionNotFound;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InterfaceMovieService service;

    @MockitoBean
    private InterfaceGenericEditService<MovieDTORequest, MovieDTOResponse> editService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testIndex_ShouldReturnMovies() throws Exception {
        MovieDTOResponse dto = new MovieDTOResponse(1L, "El niño con el pijama de rayas", Set.of("Drama"), 2008,
                Set.of("Jack Scanlon"));
        List<MovieDTOResponse> movies = List.of(dto);
        String json = mapper.writeValueAsString(movies);

        when(service.getEntities()).thenReturn(movies);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
        assertThat(response.getContentAsString(), containsString("El niño con el pijama de rayas"));
    }

    @Test
    void testGetById_ShouldReturnMovie() throws Exception {
        MovieDTOResponse dto = new MovieDTOResponse(1L, "El niño con el pijama de rayas", Set.of("Drama"), 2008,
                Set.of("Jack Scanlon"));
        String json = mapper.writeValueAsString(dto);

        when(service.getById(1L)).thenReturn(dto);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/movies/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
    }

    @Test
    void testGetById_ShouldReturnNotFound_WhenMovieDoesNotExist() throws Exception {
        String errorMessage = "Movie not found. Id 99 does not exist.";
        when(service.getById(99L)).thenThrow(new MovieExceptionNotFound(errorMessage));

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/movies/99"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(404)));
        assertThat(response.getContentAsString(), is(equalTo(errorMessage)));
    }

    @Test
    void testStore_ShouldReturnCreated() throws Exception {
        MovieDTORequest dtoRequest = new MovieDTORequest("El niño con el pijama de rayas", Set.of(1L), 1L,
                Set.of(1L));
        MovieDTOResponse dtoResponse = new MovieDTOResponse(1L, "El niño con el pijama de rayas", Set.of("Drama"),
                2008, Set.of("Jack Scanlon"));
        String requestJson = mapper.writeValueAsString(dtoRequest);
        String responseJson = mapper.writeValueAsString(dtoResponse);

        when(editService.storeEntity(dtoRequest)).thenReturn(dtoResponse);

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(201)));
        assertThat(response.getContentAsString(), is(equalTo(responseJson)));
    }

    @Test
    void testStore_ShouldReturnConflict_WhenMovieAlreadyExists() throws Exception {
        MovieDTORequest dtoRequest = new MovieDTORequest("El niño con el pijama de rayas", Set.of(1L), 1L,
                Set.of(1L));
        String requestJson = mapper.writeValueAsString(dtoRequest);

        when(editService.storeEntity(dtoRequest)).thenReturn(null);

        mockMvc.perform(post("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testStore_ShouldReturnBadRequest_WhenTitleIsBlank() throws Exception {
        MovieDTORequest request = new MovieDTORequest("", Set.of(1L), 1L, Set.of(1L));

        mockMvc.perform(post("/api/v1/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}