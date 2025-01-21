package be.dash.dashserver.api.core.member;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.dash.dashserver.api.config.TestConfig;
import be.dash.dashserver.api.config.WebMvcConfig;
import be.dash.dashserver.api.core.member.dto.OnBoardRequest;
import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.member.command.OnboardCommand;
import be.dash.dashserver.core.domain.member.service.MemberService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
@Import(TestConfig.class)
class MemberControllerTest {

    @MockitoBean
    private MemberService memberService;
    @MockitoBean
    private MemberFacade memberFacade;
    @MockitoBean
    private JwtTokenExtractor jwtTokenExtractor;
    @MockitoBean
    private TokenParser tokenParser;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("적절한 요청에 대해 응답을 반환한다.")
    void onboard() throws Exception {
        // given
        OnBoardRequest onBoardRequest = new OnBoardRequest("name", "01011111111", "nick", Level.ADVANCED, List.of(Genre.BRAKING, Genre.HIPHOP), "www.");
        // when
        doNothing().when(memberService).onboard(any(OnboardCommand.class));
        // then
        mockMvc.perform(post("/api/v1/members/onboard")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(onBoardRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유효하지 않은 요청에 대해 400 에러를 반환한다.")
    void failOnboard() throws Exception {
        // given
        OnBoardRequest onBoardRequest = new OnBoardRequest("name", "01011111111", "nick", Level.ADVANCED, List.of(Genre.BRAKING, Genre.HIPHOP, Genre.KPOP, Genre.FEMALE_HIPHOP), "www.");
        // when
        doNothing().when(memberService).onboard(any(OnboardCommand.class));
        // then
        mockMvc.perform(post("/api/v1/members/onboard")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(onBoardRequest)))
                .andExpect(status().isBadRequest());
    }
}
