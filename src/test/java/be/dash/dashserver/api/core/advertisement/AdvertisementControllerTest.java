package be.dash.dashserver.api.core.advertisement;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.domain.advertisement.Advertisement;
import be.dash.dashserver.core.domain.advertisement.service.AdvertisementService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AdvertisementController.class)
class AdvertisementControllerTest {

    @MockitoBean
    private AdvertisementService advertisementService;
    @MockitoBean
    private JwtTokenExtractor jwtTokenExtractor;
    @MockitoBean
    private TokenParser tokenParser;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("광고 이미지들을 반환한다.")
    @Test
    void advertisement() throws Exception {
        Advertisement advertisement = new Advertisement("advertisement", "설명 1");
        Advertisement advertisement1 = new Advertisement("advertisement2", "설명 2");
        when(advertisementService.getAdvertisement()).thenReturn(List.of(advertisement, advertisement1));

        mockMvc.perform(get("/api/v1/advertisements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.advertisements[0].imageUrl").value(advertisement.imageUrl()))
                .andExpect(jsonPath("$.advertisements[0].description").value(advertisement.description()))
                .andExpect(jsonPath("$.advertisements[1].imageUrl").value(advertisement1.imageUrl()))
                .andExpect(jsonPath("$.advertisements[1].description").value(advertisement1.description()));
    }
}
