package be.dash.dashserver.api.core.lesson.dto;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;

public record LessonFilterRequest(
        Genre genre, Level level, LocalDateTime startDate, LocalDateTime endDate) {
}
