package dev.jenny.apimovies.actor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import dev.jenny.apimovies.actor.dtos.ActorDTORequest;
import dev.jenny.apimovies.actor.dtos.ActorDTOResponse;
import dev.jenny.apimovies.implementations.InterfaceGenericEditService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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
}