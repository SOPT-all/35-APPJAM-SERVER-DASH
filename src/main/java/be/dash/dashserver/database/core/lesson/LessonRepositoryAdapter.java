package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Images;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.lesson.Round;
import be.dash.dashserver.core.domain.lesson.Rounds;
import be.dash.dashserver.core.domain.lesson.Videos;
import be.dash.dashserver.core.domain.lesson.service.LessonRepository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.exception.DashException;
import be.dash.dashserver.database.core.member.MemberJpaEntity;
import be.dash.dashserver.database.core.member.MemberJpaRepository;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaRepository;
import be.dash.dashserver.database.core.teacher.TeacherJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherJpaRepository;
import be.dash.dashserver.database.core.teacher.TeacherVideoJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherVideoJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LessonRepositoryAdapter implements LessonRepository {

    private final LessonJpaEntityRepository lessonJpaEntityRepository;
    private final TeacherImageJpaRepository teacherImageJpaRepository;
    private final LessonRoundJpaRepository lessonRoundJpaRepository;
    private final LessonImageJpaRepository lessonImageJpaRepository;
    private final LessonVideoJpaRepository lessonVideoJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final TeacherJpaRepository teacherJpaRepository;
    private final TeacherVideoJpaRepository teacherVideoJpaRepository;

    @Override
    public List<Lesson> findActiveLessonsByFilters(Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, String keyword, LocalDateTime now) {
        List<LessonJpaEntity> activeLessons = lessonJpaEntityRepository.findAll(LessonSpecifications.findActiveLessonsByFilters(genre, level, startDateTime, endDateTime, keyword, now));
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
        return getLessons(lessonJpaEntityRepository.findByStartDateTimeGreaterThan(now));
    }

    private List<Lesson> getLessons(List<LessonJpaEntity> activeLessons) {
        return activeLessons.stream()
                .map(lessonEntity -> {
                    List<TeacherImageJpaEntity> teacherImages = teacherImageJpaRepository.findAllByTeacherId(lessonEntity.getTeacher()
                            .getId());
                    List<LessonImageJpaEntity> lessonImages = lessonImageJpaRepository.findAllByLessonId(lessonEntity.getId());
                    return lessonEntity.toDomainWithImages(teacherImages, lessonImages);
                })
                .toList();
    }

    @Override
    public List<Lesson> findActiveLessonsByGenreOrLevel(LocalDateTime now, List<Genre> genres, Level level) {
        List<LessonJpaEntity> activeLessons = lessonJpaEntityRepository.findAll(LessonSpecifications.findActiveLessonsByGenreOrLevel(now, genres, List.of(level)));
        return getLessons(activeLessons);
    }

    @Override
    public List<Genre> popularGenres(LocalDateTime localDateTime) {
        return lessonJpaEntityRepository.findPopularGenresByActiveLessons(localDateTime);
    }

    @Override
    public Lessons findLessonsByTeacher(Teacher teacher, LocalDateTime localDateTime) {
        return new Lessons(lessonJpaEntityRepository.findByTeacherIdOrderByCreatedAtDesc(teacher.getId())
                .stream().map(lessonJpaEntity -> {
                    List<LessonImageJpaEntity> images = lessonImageJpaRepository.findAllByLessonId(lessonJpaEntity.getId());
                    return lessonJpaEntity.toDomain(teacher, images);
                }).toList());
    }

    @Override
    public Lesson findLessonsById(Long lessonId) {
        LessonJpaEntity lessonJpaEntity = lessonJpaEntityRepository.findById(lessonId)
                .orElseThrow(() -> new DashException("해당하는 수업을 찾을 수 없습니다."));
        Images lessonImages = new Images(lessonImageJpaRepository.findAllByLessonId(lessonId).stream()
                .map(LessonImageJpaEntity::getImageUrl).toList());
        Videos lessonVideos = new Videos(lessonVideoJpaRepository.findAllByLessonId(lessonId).stream()
                .map(LessonVideoJpaEntity::getVideoUrl).toList());
        Rounds lessonsRounds = new Rounds(lessonRoundJpaRepository.findAllByLessonId(lessonId).stream()
                .map(lessonRoundJpaEntity -> new Round(lessonRoundJpaEntity.getStartTime(), lessonRoundJpaEntity.getEndTime()))
                .toList());

        TeacherJpaEntity teacherJpaEntity = teacherJpaRepository.findById(lessonJpaEntity.getTeacher().getId())
                .orElseThrow(() -> new DashException("해당하는 댄서를 찾을 수 없습니다."));
        List<TeacherImageJpaEntity> teacherImages = teacherImageJpaRepository.findAllByTeacherId(teacherJpaEntity.getId());
        List<TeacherVideoJpaEntity> teacherVideos = teacherVideoJpaRepository.findAllByTeacherId(teacherJpaEntity.getId());
        MemberJpaEntity memberJpaEntity = memberJpaRepository.findById(teacherJpaEntity.getMember().getId())
                .orElseThrow(() -> new DashException("해당하는 멤버를 찾을 수 없습니다."));
        Teacher teacher = teacherJpaEntity.toDomainWithImageAndVideo(teacherImages, teacherVideos, memberJpaEntity);

        return lessonJpaEntity.toDomain(teacher, lessonImages, lessonVideos, lessonsRounds);
    }
}
