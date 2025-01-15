package be.dash.dashserver.api.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
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
                        .param("genre", "HIPHOP")
                        .param("level", "BEGINNER")
                        .param("startDate", LocalDateTime.now().toString())
                        .param("endDate", LocalDateTime.now().plusDays(2).toString())
                        .param("sortOption", "LATEST"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lessons[0].id").value(lessons.lessons().get(0).getId()))
                .andExpect(jsonPath("$.lessons[0].genre").value(lessons.lessons().get(0).getGenre().name()))
                .andExpect(jsonPath("$.lessons[0].level").value(lessons.lessons().get(0).getLevel().name()))
                .andExpect(jsonPath("$.lessons[0].name").value(lessons.lessons().get(0).getName()));
    }
}
