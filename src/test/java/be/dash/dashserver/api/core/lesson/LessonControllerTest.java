package be.dash.dashserver.api.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.common.SortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.service.LessonService;
import be.dash.dashserver.core.fixture.LessonFixture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @MockitoBean
    private LessonService lessonService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @DisplayName("주어진 필터와 정렬 옵션으로 수업 검색 요청을 처리하고 올바른 응답을 반환한다.")
    @Test
    void search() throws Exception {
        Lessons lessons = new Lessons(List.of(LessonFixture.create(1, 1, 1, Genre.HIPHOP, Level.BEGINNER)));
        when(lessonService.search(
                any(Genre.class),
                any(Level.class),
                any(LocalDateTime.class),
                any(LocalDateTime.class),
                any(SortOption.class)
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
                .andExpect(jsonPath("$.lessons[0].name").value(lessons.lessons().get(0).getName()));
    }
}
