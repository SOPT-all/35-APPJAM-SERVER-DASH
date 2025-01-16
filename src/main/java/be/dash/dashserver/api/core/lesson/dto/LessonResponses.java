package be.dash.dashserver.api.core.lesson.dto;

import java.util.List;
import be.dash.dashserver.core.domain.lesson.Lessons;

public record LessonResponses(List<LessonResponse> lessons) {
    public LessonResponses(Lessons lessons) {
        this(lessons.lessons().stream()
                .map(LessonResponse::new)
                .toList());
    }
}
