package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.exception.DashException;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryAdapter implements LessonRepository {

    private final LessonJpaEntityRepository lessonJpaEntityRepository;
    private final TeacherImageJpaRepository teacherImageJpaRepository;

    @Override
    public List<Lesson> findActiveLessonsByFilters(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime now) {
        List<LessonJpaEntity> activeLessons = lessonJpaEntityRepository.findAll(LessonSpecifications.findActiveLessonsByFilters(genre, level, startDateTime, endDateTime, LocalDateTime.now()));
        return activeLessons.stream()
                .map(lessonEntity -> {
                    TeacherImageJpaEntity teacherImageJpaEntity = teacherImageJpaRepository.findFirstByTeacherId(lessonEntity.getTeacher()
                            .getId()).orElseThrow(() -> new DashException("해당 수업에 일치하는 선생님이 없습니다."));
                    return lessonEntity.toDomainWithTeacherImage(teacherImageJpaEntity);
                })
                .toList();
    }

    @Override
    public void save(Lesson lesson) {
        lessonJpaEntityRepository.save(new LessonJpaEntity(lesson));
    }

    @Override
    public List<Genre> findDistinctGenresByTeacherIdOrderByCountDesc(Long teacherId) {
        return lessonJpaEntityRepository.findDistinctGenresByTeacherIdOrderByCountDesc(teacherId);
    }
}
