package dev.jenny.apimovies.releaseyear;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Stream;

import dev.jenny.apimovies.implementations.InterfaceGenericEditService;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTORequest;
import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;
import dev.jenny.apimovies.releaseyear.exceptions.ReleaseYearExceptionNotFound;
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

@WebMvcTest(controllers = ReleaseYearController.class)
class ReleaseYearControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InterfaceReleaseYearService service;

    @MockitoBean
    private InterfaceGenericEditService<ReleaseYearDTORequest, ReleaseYearDTOResponse> editService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testIndex_ShouldReturnReleaseYears() throws Exception {
        ReleaseYearDTOResponse dto = new ReleaseYearDTOResponse(1L, 1994);
        List<ReleaseYearDTOResponse> releaseYears = List.of(dto);
        String json = mapper.writeValueAsString(releaseYears);

        when(service.getEntities()).thenReturn(releaseYears);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/release-years"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
    }

    @Test
    void testGetById_ShouldReturnReleaseYear() throws Exception {
        ReleaseYearDTOResponse dto = new ReleaseYearDTOResponse(1L, 1994);
        String json = mapper.writeValueAsString(dto);

        when(service.getById(1L)).thenReturn(dto);
        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/release-years/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(200)));
        assertThat(response.getContentAsString(), is(equalTo(json)));
    }

    @Test
    void testGetById_ShouldReturnNotFound_WhenReleaseYearDoesNotExist() throws Exception {
        String errorMessage = "Release year not found. Id 99 does not exist.";

        when(service.getById(99L)).thenThrow(new ReleaseYearExceptionNotFound(errorMessage));

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/release-years/99"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(404)));
        assertThat(response.getContentAsString(), is(equalTo(errorMessage)));
    }

    @Test
    void testStore_ShouldReturnCreated() throws Exception {
        ReleaseYearDTORequest dtoRequest = new ReleaseYearDTORequest(1994);
        ReleaseYearDTOResponse dtoResponse = new ReleaseYearDTOResponse(1L, 1994);
        String requestJson = mapper.writeValueAsString(dtoRequest);
        String responseJson = mapper.writeValueAsString(dtoResponse);

        when(editService.storeEntity(dtoRequest)).thenReturn(dtoResponse);

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/release-years")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus(), is(equalTo(201)));
        assertThat(response.getContentAsString(), is(equalTo(responseJson)));
    }

    @Test
    void testStore_ShouldReturnConflict_WhenReleaseYearAlreadyExists() throws Exception {
        ReleaseYearDTORequest dtoRequest = new ReleaseYearDTORequest(1994);
        String requestJson = mapper.writeValueAsString(dtoRequest);

        when(editService.storeEntity(dtoRequest)).thenReturn(null);

        mockMvc.perform(post("/api/v1/release-years")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isConflict());
    }

    @Test
    void testUpdate_ShouldReturnOk_WhenReleaseYearIsUpdated() throws Exception {
        ReleaseYearDTORequest dtoRequest = new ReleaseYearDTORequest(2001);
        ReleaseYearDTOResponse dtoResponse = new ReleaseYearDTOResponse(1L, 2001);
        String requestJson = mapper.writeValueAsString(dtoRequest);
        String responseJson = mapper.writeValueAsString(dtoResponse);

        when(editService.updateEntity(1L, dtoRequest)).thenReturn(dtoResponse);

        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/release-years/1")
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
        ReleaseYearDTORequest dtoRequest = new ReleaseYearDTORequest(2001);
        String requestJson = mapper.writeValueAsString(dtoRequest);

        if (exception != null) {
            when(editService.updateEntity(1L, dtoRequest)).thenThrow(exception);
        } else {
            when(editService.updateEntity(1L, dtoRequest)).thenReturn(null);
        }

        mockMvc.perform(put("/api/v1/release-years/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is(expectedStatus));
    }

    private static Stream<Arguments> updateErrorScenarios() {
        return Stream.of(
                Arguments.of(new ReleaseYearExceptionNotFound("Release year not found. Id 1 does not exist."), 404),
                Arguments.of(null, 409));
    }
}