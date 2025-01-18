package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.command.CreateLessonCommand;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;

    public Lessons search(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, LessonSortOption sortOption) {
        Lessons lessons = new Lessons(
                lessonRepository.findActiveLessonsByFilters(genre, level, startDateTime, endDateTime, LocalDateTime.now())
        );
        return lessons.sort(sortOption);
    }

    @Transactional
    public void createLesson(CreateLessonCommand command) {
        Teacher teacher = teacherRepository.findByMemberId(command.memberId());
        Lesson lesson = command.toDomainWith(teacher);
        lessonRepository.save(lesson);
    }
}
