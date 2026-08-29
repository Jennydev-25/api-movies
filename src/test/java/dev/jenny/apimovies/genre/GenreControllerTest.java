package dev.jenny.apimovies.genre;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;
import dev.jenny.apimovies.genre.exceptions.GenreExceptionNotFound;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InterfaceGenreService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testIndex_ShouldReturnGenres() throws Exception {
        GenreDTOResponse dto = new GenreDTOResponse(1L, "Terror");
        List<GenreDTOResponse> genres = List.of(dto);
        String json = mapper.writeValueAsString(genres);

        when(service.getEntities()).thenReturn(genres);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/genres"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
        assertThat(response.getContentAsString(), containsString("Terror"));
    }

    @Test
    void testGetById_ShouldReturnGenre() throws Exception {
        GenreDTOResponse dto = new GenreDTOResponse(1L, "Terror");
        String json = mapper.writeValueAsString(dto);

        when(service.getById(1L)).thenReturn(dto);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/genres/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
    }

    @Test
    void testGetById_ShouldReturnNotFound_WhenGenreDoesNotExist() throws Exception {
        String errorMessage = "Genre not found. Id 99 does not exist.";

        when(service.getById(99L)).thenThrow(new GenreExceptionNotFound(errorMessage));

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/genres/99"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(404)));
        assertThat(response.getContentAsString(), is(equalTo(errorMessage)));
    }
}