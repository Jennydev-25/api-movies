package dev.jenny.apimovies.movie;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;

import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import dev.jenny.apimovies.movie.dtos.MovieDTORequest;
import dev.jenny.apimovies.movie.dtos.MovieDTOResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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
}