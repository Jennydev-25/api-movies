package dev.jenny.apimovies.actor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import dev.jenny.apimovies.actor.dtos.ActorDTORequest;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;
import dev.jenny.apimovies.actor.exceptions.ActorException;
import dev.jenny.apimovies.actor.exceptions.ActorExceptionNotFound;
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

@WebMvcTest(controllers = ActorController.class)
class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InterfaceActorService service;

    @MockitoBean
    private InterfaceGenericEditService<ActorDTORequest, ActorDTOResponse> editService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testIndex_ShouldReturnActors() throws Exception {
        ActorDTOResponse dto = new ActorDTOResponse(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        List<ActorDTOResponse> actors = List.of(dto);
        String json = mapper.writeValueAsString(actors);

        when(service.getEntities()).thenReturn(actors);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/actors"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
        assertThat(response.getContentAsString(), containsString("Robert Downey Jr."));
    }

    @Test
    void testGetById_ShouldReturnActor() throws Exception {
        ActorDTOResponse dto = new ActorDTOResponse(1L, "Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        String json = mapper.writeValueAsString(dto);

        when(service.getById(1L)).thenReturn(dto);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/actors/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
    }

    @Test
    void testGetById_ShouldReturnNotFound_WhenActorDoesNotExist() throws Exception {
        String errorMessage = "Actor not found. Id 99 does not exist.";

        when(service.getById(99L)).thenThrow(new ActorExceptionNotFound(errorMessage));

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/actors/99"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(404)));
        assertThat(response.getContentAsString(), is(equalTo(errorMessage)));
    }

    @Test
    void testStore_ShouldReturnCreated() throws Exception {
        ActorDTORequest dtoRequest = new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        ActorDTOResponse dtoResponse = new ActorDTOResponse(1L, "Robert Downey Jr.", "American",
                LocalDate.of(1965, 4, 4));
        String requestJson = mapper.writeValueAsString(dtoRequest);
        String responseJson = mapper.writeValueAsString(dtoResponse);

        when(editService.storeEntity(dtoRequest)).thenReturn(dtoResponse);

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(201)));
        assertThat(response.getContentAsString(), is(equalTo(responseJson)));
    }

    @Test
    void testStore_ShouldReturnConflict_WhenActorAlreadyExists() throws Exception {
        ActorDTORequest dtoRequest = new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        String requestJson = mapper.writeValueAsString(dtoRequest);

        when(editService.storeEntity(dtoRequest)).thenReturn(null);

        mockMvc.perform(post("/api/v1/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testStore_ShouldReturnBadRequest_WhenNameIsBlank() throws Exception {
        ActorDTORequest request = new ActorDTORequest("", "American", LocalDate.of(1965, 4, 4));

        mockMvc.perform(post("/api/v1/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate_ShouldReturnOk_WhenActorIsUpdated() throws Exception {
        ActorDTORequest dtoRequest = new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        ActorDTOResponse dtoResponse = new ActorDTOResponse(1L, "Robert Downey Jr.", "American",
                LocalDate.of(1965, 4, 4));
        String requestJson = mapper.writeValueAsString(dtoRequest);
        String responseJson = mapper.writeValueAsString(dtoResponse);

        when(editService.updateEntity(1L, dtoRequest)).thenReturn(dtoResponse);

        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/actors/1")
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
        ActorDTORequest dtoRequest = new ActorDTORequest("Robert Downey Jr.", "American", LocalDate.of(1965, 4, 4));
        String requestJson = mapper.writeValueAsString(dtoRequest);

        if (exception != null) {
            when(editService.updateEntity(1L, dtoRequest)).thenThrow(exception);
        } else {
            when(editService.updateEntity(1L, dtoRequest)).thenReturn(null);
        }

        mockMvc.perform(put("/api/v1/actors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is(expectedStatus));
    }

    private static Stream<Arguments> updateErrorScenarios() {
        return Stream.of(
                Arguments.of(new ActorExceptionNotFound("Actor not found. Id 1 does not exist."), 404),
                Arguments.of(null, 409));
    }

    @ParameterizedTest
    @MethodSource("exceptionScenarios")
    void testGetById_ShouldHandleUnexpectedExceptions(Exception exception, int expectedStatus) throws Exception {
        when(service.getById(1L)).thenThrow(exception);

        mockMvc.perform(get("/api/v1/actors/1"))
                .andExpect(status().is(expectedStatus));
    }

    private static Stream<Arguments> exceptionScenarios() {
        return Stream.of(
                Arguments.of(new ActorException("Invalid actor operation"), 400),
                Arguments.of(new RuntimeException("Unexpected error"), 500));
    }
}