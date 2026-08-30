package dev.jenny.apimovies.genre;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

import dev.jenny.apimovies.genre.dtos.GenreDTORequest;
import dev.jenny.apimovies.genre.dtos.GenreDTOResponse;
import dev.jenny.apimovies.genre.exceptions.GenreException;
import dev.jenny.apimovies.genre.exceptions.GenreExceptionNotFound;
import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
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

    @MockitoBean
    private InterfaceGenericEditService<GenreDTORequest, GenreDTOResponse> editService;

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

    @Test
    void testStore_ShouldReturnCreated() throws Exception {
        GenreDTORequest dtoRequest = new GenreDTORequest("Terror");
        GenreDTOResponse dtoResponse = new GenreDTOResponse(1L, "Terror");
        String requestJson = mapper.writeValueAsString(dtoRequest);
        String responseJson = mapper.writeValueAsString(dtoResponse);

        when(editService.storeEntity(dtoRequest)).thenReturn(dtoResponse);

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(201)));
        assertThat(response.getContentAsString(), is(equalTo(responseJson)));
    }

    @Test
    void testStore_ShouldReturnConflict_WhenGenreAlreadyExists() throws Exception {
        GenreDTORequest dtoRequest = new GenreDTORequest("Terror");
        String requestJson = mapper.writeValueAsString(dtoRequest);

        when(editService.storeEntity(dtoRequest)).thenReturn(null);

        mockMvc.perform(post("/api/v1/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testStore_ShouldReturnBadRequest_WhenNameIsBlank() throws Exception {
        String invalidJson = "{\"name\":\"\"}";

        mockMvc.perform(post("/api/v1/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_ShouldReturnOk_WhenGenreIsUpdated() throws Exception {
        GenreDTORequest dtoRequest = new GenreDTORequest("Suspense");
        GenreDTOResponse dtoResponse = new GenreDTOResponse(1L, "Suspense");
        String requestJson = mapper.writeValueAsString(dtoRequest);
        String responseJson = mapper.writeValueAsString(dtoResponse);

        when(editService.updateEntity(1L, dtoRequest)).thenReturn(dtoResponse);

        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(responseJson)));
    }

    @ParameterizedTest
    @MethodSource("updateErrorScenarios")
    void testUpdate_ShouldHandleErrors(RuntimeException exception, int expectedStatus) throws Exception {
        GenreDTORequest dtoRequest = new GenreDTORequest("Terror");
        String requestJson = mapper.writeValueAsString(dtoRequest);

        if (exception != null) {
            when(editService.updateEntity(1L, dtoRequest)).thenThrow(exception);
        } else {
            when(editService.updateEntity(1L, dtoRequest)).thenReturn(null);
        }

        mockMvc.perform(put("/api/v1/genres/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is(expectedStatus));
    }

    private static Stream<Arguments> updateErrorScenarios() {
        return Stream.of(
                Arguments.of(new GenreExceptionNotFound("Genre not found. Id 1 does not exist."), 404),
                Arguments.of(null, 409));
    }

    @ParameterizedTest
    @MethodSource("exceptionScenarios")
    void testGetById_ShouldHandleUnexpectedExceptions(Exception exception, int expectedStatus) throws Exception {
        when(service.getById(1L)).thenThrow(exception);

        mockMvc.perform(get("/api/v1/genres/1"))
                .andExpect(status().is(expectedStatus));
    }

    private static Stream<Arguments> exceptionScenarios() {
        return Stream.of(
                Arguments.of(new GenreException("Invalid genre operation"), 400),
                Arguments.of(new RuntimeException("Unexpected error"), 500));
    }
}