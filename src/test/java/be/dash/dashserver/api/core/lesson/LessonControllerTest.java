package be.dash.dashserver.api.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.service.LessonService;
import be.dash.dashserver.core.fixture.LessonFixture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static be.dash.dashserver.core.domain.common.Genre.CHOREOGRAPHY;
import static be.dash.dashserver.core.domain.common.Genre.HIPHOP;
import static be.dash.dashserver.core.domain.common.Genre.KPOP;


@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @MockitoBean
    private LessonService lessonService;
    @MockitoBean
    private JwtTokenExtractor jwtTokenExtractor;
    @MockitoBean
    private TokenParser tokenParser;
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("주어진 필터와 정렬 옵션으로 수업 검색 요청을 처리하고 올바른 응답을 반환한다.")
    @Test
    void search() throws Exception {
        Lessons lessons = new Lessons(List.of(LessonFixture.createWithImage(1, 1, 1, HIPHOP, Level.BEGINNER, "image")));
        when(lessonService.search(
                any(Genre.class),
                any(Level.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(LessonSortOption.class)
        )).thenReturn(lessons);

        mockMvc.perform(get("/api/v1/lessons")
                        .param("genre", "HIPHoP")
                        .param("level", "BEGINNER")
                        .param("startDate", "2025-01-13T18:26:27")
                        .param("endDate", "2025-01-15T18:26:27")
                        .param("sortOption", "LATEST"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lessons[0].id").value(lessons.lessons().get(0).getId()))
                .andExpect(jsonPath("$.lessons[0].genre").value(lessons.lessons().get(0).getGenre().name()))
                .andExpect(jsonPath("$.lessons[0].level").value(lessons.lessons().get(0).getLevel().name()))
                .andExpect(jsonPath("$.lessons[0].name").value(lessons.lessons().get(0).getName()))
                .andExpect(jsonPath("$.lessons[0].imageUrl").value(lessons.lessons().get(0).getImageUrl()));
    }

    @DisplayName("수업을 추천한다.")
    @Test
    void recommendation() throws Exception {
        Long memberId = 1L;
        Lessons lessons = new Lessons(List.of(LessonFixture.createWithImage(memberId, 1, 1, HIPHOP, Level.BEGINNER, "image")));
        when(tokenParser.getToken(anyString())).thenReturn("subject");
        when(jwtTokenExtractor.getSubject(anyString())).thenReturn(String.valueOf(memberId));
        when(lessonService.getRecommendationLessons(any(Long.class))).thenReturn(lessons);

        mockMvc.perform(get("/api/v1/lessons/recommendations")
                        .header(HttpHeaders.AUTHORIZATION, "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lessons[0].id").value(lessons.lessons().get(0).getId()))
                .andExpect(jsonPath("$.lessons[0].genre").value(lessons.lessons().get(0).getGenre().name()))
                .andExpect(jsonPath("$.lessons[0].level").value(lessons.lessons().get(0).getLevel().name()))
                .andExpect(jsonPath("$.lessons[0].name").value(lessons.lessons().get(0).getName()))
                .andExpect(jsonPath("$.lessons[0].imageUrl").value(lessons.lessons().get(0).getImageUrl()));
    }

    @DisplayName("장르를 추천한다.")
    @Test
    void popularGenres() throws Exception {
        when(lessonService.getPopularGenres()).thenReturn(List.of(HIPHOP, Genre.CHOREOGRAPHY, Genre.KPOP, Genre.BRAKING));

        mockMvc.perform(get("/api/v1/lessons/popular-genres")
                        .header(HttpHeaders.AUTHORIZATION, "token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres[0]").value(HIPHOP.name()))
                .andExpect(jsonPath("$.genres[1]").value(CHOREOGRAPHY.name()))
                .andExpect(jsonPath("$.genres[2]").value(KPOP.name()));
    }
}
