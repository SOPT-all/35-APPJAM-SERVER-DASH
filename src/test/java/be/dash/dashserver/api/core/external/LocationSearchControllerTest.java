package be.dash.dashserver.api.core.external;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import be.dash.dashserver.api.config.TestConfig;
import be.dash.dashserver.api.config.WebMvcConfig;
import be.dash.dashserver.api.core.auth.AuthController;
import be.dash.dashserver.api.core.external.dto.LocationsResponse;
import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.domain.lesson.Location;
import be.dash.dashserver.core.domain.lesson.Locations;
import be.dash.dashserver.core.external.LocationSearchService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = LocationSearchController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = WebMvcConfig.class))
@Import(TestConfig.class)
class LocationSearchControllerTest {
    @MockitoBean
    private LocationSearchService locationSearchService;
    @MockitoBean
    private JwtTokenExtractor jwtTokenExtractor;
    @MockitoBean
    private TokenParser tokenParser;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("요청 파라미터가 있다면 오류를 반환한다.")
    void getLocations() throws Exception {
        Locations locations = Locations.of(List.of());
        when(locationSearchService.getLocations(anyString())).thenReturn(locations);

        mockMvc.perform(get("/api/v1//locations")
                        .param("query", "test"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("요청 파라미터가 없다면 오류를 반환한다.")
    void failGetLocations() throws Exception {
        Locations locations = Locations.of(List.of());
        when(locationSearchService.getLocations(anyString())).thenReturn(locations);

        mockMvc.perform(get("/api/v1//locations"))
                .andExpect(status().isBadRequest());
    }
}
