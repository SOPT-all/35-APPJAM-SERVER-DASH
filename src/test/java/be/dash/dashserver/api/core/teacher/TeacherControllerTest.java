package be.dash.dashserver.api.core.teacher;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.dash.dashserver.api.config.TestConfig;
import be.dash.dashserver.api.config.WebMvcConfig;
import be.dash.dashserver.api.core.teacher.dto.CreateTeacherRequest;
import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.Token;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;
import be.dash.dashserver.core.domain.teacher.command.CreateTeacherCommand;
import be.dash.dashserver.core.domain.teacher.service.TeacherService;
import be.dash.dashserver.core.fixture.TeacherFixture;

import static org.hamcrest.Matchers.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TeacherController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class))
@Import(TestConfig.class)
class TeacherControllerTest {

    @MockitoBean
    private TeacherService teacherService;
    @MockitoBean
    private JwtTokenExtractor jwtTokenExtractor;
    @MockitoBean
    private TokenParser tokenParser;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("주어진 기본 정렬 옵션으로 댄서 검색 요청을 처리하고 올바른 응답을 반환한다.")
    @Test
    void search() throws Exception {
        TeacherLessonGenres teacherLessonGenres1 = new TeacherLessonGenres(TeacherFixture.create(1, 1), List.of(Genre.HIPHOP, Genre.FEMALE_HIPHOP));
        TeacherLessonGenres teacherLessonGenres2 = new TeacherLessonGenres(TeacherFixture.create(3, 3), List.of());
        List<TeacherLessonGenres> teacherLessonGenresList = List.of(teacherLessonGenres1, teacherLessonGenres2);
        when(teacherService.search()).thenReturn(teacherLessonGenresList);

        mockMvc.perform(get("/api/v1/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teachers[0].id").value(teacherLessonGenres1.teacher().getId()))
                .andExpect(jsonPath("$.teachers[0].nickname").value(teacherLessonGenres1.teacher().getMember().getNickname()))
                .andExpect(jsonPath("$.teachers[0].profileImage").value(teacherLessonGenres1.teacher().getImageUrls().get(0)))
                .andExpect(jsonPath("$.teachers[0].genres[0]").value(teacherLessonGenres1.genres().get(0).name()))
                .andExpect(jsonPath("$.teachers[0].genres[1]").value(teacherLessonGenres1.genres().get(1).name()))
                .andExpect(jsonPath("$.teachers[1].id").value(teacherLessonGenres2.teacher().getId()))
                .andExpect(jsonPath("$.teachers[1].nickname").value(teacherLessonGenres2.teacher().getMember().getNickname()))
                .andExpect(jsonPath("$.teachers[1].profileImage").value(teacherLessonGenres2.teacher().getImageUrls().get(0)))
                .andExpect(jsonPath("$.teachers[1].genres").value(empty()));
    }

    @DisplayName("주어진 요청으로 댄서 생성 요청을 처리하고 올바른 응답을 반환한다.")
    @Test
    void create() throws Exception {
        CreateTeacherRequest createTeacherRequest = new CreateTeacherRequest("detail",
                "youtube",
                List.of("education1", "education2"),
                List.of("experience1", "experience2"),
                "instagram",
                List.of("youtube", "youtube"),
                List.of("imageUrl1", "imageUrl2"));
        // when
        when(teacherService.create(any(CreateTeacherCommand.class))).thenReturn(new Token("accessToken", "refreshToken"));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTeacherRequest)))
                .andExpect(status().isOk());


    }

    @DisplayName("요청 검증을 통과하지 못하면 에러를 반환한다.")
    @Test
    void failCreate() throws Exception {
        CreateTeacherRequest createTeacherRequest = new CreateTeacherRequest("detail",
                "youtube",
                List.of("education1education1education1education1", "education2"),
                List.of("experience1", "experience2"),
                "instagram",
                List.of("youtube", "youtube"),
                List.of("imageUrl1", "imageUrl2"));
        // when
        when(teacherService.create(any(CreateTeacherCommand.class))).thenReturn(new Token("accessToken", "refreshToken"));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTeacherRequest)))
                .andExpect(status().isBadRequest());


    }

}
