package be.dash.dashserver.core.domain.lesson.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.teacher.Teacher;

public interface LessonRepository {

    List<Lesson> findActiveLessonsByFilters(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, String keyword, LocalDateTime now);

    void save(Lesson lesson);

    List<Genre> findDistinctGenresByTeacherIdOrderByCountDesc(Long teacherId);

    List<Lesson> findActiveLessons(LocalDateTime now);

    List<Lesson> findActiveLessonsByGenreOrLevel(LocalDateTime localDateTime, List<Genre> genres, Level level);

    List<Genre> popularGenres(LocalDateTime localDateTime);

    Lessons findLessonsByTeacher(Teacher teacher);

    Lesson findLessonsById(Long lessonId);

    int getLessonCount(Long teacherId);

    List<Lesson> findAllByIdsOrderByStartDate(Set<Long> lessonIds);

    boolean existsByTeacherIdAndLessonId(long teacherId, long lessonId);

    List<Lesson> findAllByTeacherIdOrderByStartDateTime(long teacherId);

    void increaseReservationCount(long lessonId);

    boolean isFull(long lessonId);
}
