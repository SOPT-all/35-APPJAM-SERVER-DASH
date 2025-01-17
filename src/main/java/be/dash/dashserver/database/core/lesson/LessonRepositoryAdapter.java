package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryAdapter implements LessonRepository {

    private final LessonJpaEntityRepository lessonJpaEntityRepository;
    private final TeacherImageJpaRepository teacherImageJpaRepository;
    private final LessonRoundJpaRepository lessonRoundJpaRepository;
    private final LessonImageJpaRepository lessonImageJpaRepository;
    private final LessonVideoJpaRepository lessonVideoJpaRepository;
    @Override
    public List<Lesson> findActiveLessonsByFilters(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime now) {
        List<LessonJpaEntity> activeLessons = lessonJpaEntityRepository.findAll(LessonSpecifications.findActiveLessonsByFilters(genre, level, startDateTime, endDateTime, LocalDateTime.now()));
        return getLessons(activeLessons);
    }

    @Override
    public void save(Lesson lesson) {
        LessonJpaEntity lessonJpaEntity = lessonJpaEntityRepository.save(new LessonJpaEntity(lesson));

        List<LessonImageJpaEntity> lessonImageJpaEntities = lesson.getImages().getImageUrls().stream()
                .map(imageUrl -> new LessonImageJpaEntity(lessonJpaEntity.getId(), imageUrl)).toList();
        lessonImageJpaRepository.saveAll(lessonImageJpaEntities);

        List<LessonVideoJpaEntity> lessonVideoJpaEntities = lesson.getVideos().getVideoUrls().stream()
                .map(videoUrl -> new LessonVideoJpaEntity(lessonJpaEntity.getId(), videoUrl)).toList();
        lessonVideoJpaRepository.saveAll(lessonVideoJpaEntities);

        List<LessonRoundJpaEntity> lessonRoundJpaEntities = lesson.getRounds().getRounds().stream()
                .map(lessonRound -> new LessonRoundJpaEntity(lessonJpaEntity.getId(), lessonRound.getStartTime(), lessonRound.getEndTime()))
                .toList();
        lessonRoundJpaRepository.saveAll(lessonRoundJpaEntities);
    }

    @Override
    public List<Genre> findDistinctGenresByTeacherIdOrderByCountDesc(Long teacherId) {
        return lessonJpaEntityRepository.findDistinctGenresByTeacherIdOrderByCountDesc(teacherId);
    }

    @Override
    public List<Lesson> findActiveLessons(LocalDateTime now) {
        return getLessons(lessonJpaEntityRepository.findByEndDateTimeGreaterThan(now));
    }

    private List<Lesson> getLessons(List<LessonJpaEntity> activeLessons) {
        return activeLessons.stream()
                .map(lessonEntity -> {
                    List<TeacherImageJpaEntity> allByTeacher = teacherImageJpaRepository.findAllByTeacherId(lessonEntity.getTeacher().getId());
                    List<LessonImageJpaEntity> lessonImages = lessonImageJpaRepository.findAllByLesson_Id(lessonEntity.getId());
                    return lessonEntity.toDomainWithImages(allByTeacher, lessonImages);
                })
                .toList();
    }

    @Override
    public List<Lesson> findActiveLessonsByGenreOrLevel(LocalDateTime now, List<Genre> genres, Level level) {
        List<LessonJpaEntity> activeLessons = lessonJpaEntityRepository.findAll(LessonSpecifications.findActiveLessonsByGenreOrLevel(now, genres, List.of(level)));
        return getLessons(activeLessons);
    }
}
