package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.common.SortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;

    public Lessons search(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, SortOption sortOption) {
        Lessons lessons = new Lessons(
                lessonRepository.findActiveLessonsByFilters(genre, level, startDateTime, endDateTime, LocalDateTime.now(ZoneId.of("Asia/Seoul")))
        );
        return lessons.sort(sortOption);
    }
}
