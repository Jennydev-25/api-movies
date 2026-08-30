package dev.jenny.apimovies.releaseyear;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import dev.jenny.apimovies.releaseyear.dtos.ReleaseYearDTOResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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
}
