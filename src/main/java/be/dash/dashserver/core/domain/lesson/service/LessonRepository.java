package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;

public interface LessonRepository {

    List<Lesson> findActiveLessonsByFilters(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime now);

    void save(Lesson lesson);

    List<Genre> findDistinctGenresByTeacherIdOrderByCountDesc(Long teacherId);
}
