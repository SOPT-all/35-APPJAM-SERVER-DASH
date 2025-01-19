package be.dash.dashserver.database.core.teacher;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.Teachers;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import be.dash.dashserver.core.exception.NotFoundException;
import be.dash.dashserver.database.core.lesson.LessonJpaEntityRepository;
import be.dash.dashserver.database.core.teacher.projection.TeacherLessonCount;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeacherRepositoryAdapter implements TeacherRepository {
    private final TeacherJpaRepository teacherJpaRepository;
    private final LessonJpaEntityRepository lessonJpaEntityRepository;
    private final TeacherImageJpaRepository teacherImageJpaRepository;
    private final TeacherVideoJpaRepository teacherVideoJpaRepository;

    @Override
    public void save(Teacher teacher) {
        TeacherJpaEntity teacherJpaEntity = TeacherJpaEntity.fromDomain(teacher);
        teacherJpaRepository.save(teacherJpaEntity);
    }

    @Override
    public Teachers findTeachersSortByLessonCountsDesc() {
        List<Teacher> teachers = new ArrayList<>();
        List<TeacherLessonCount> teacherLessonCounts = lessonJpaEntityRepository.findTeacherLessonCountsDesc();
        teacherLessonCounts.forEach(teacherLessonCount -> {
            List<TeacherImageJpaEntity> teacherImageJpaEntities = teacherImageJpaRepository
                    .findAllByTeacherId(teacherLessonCount.teacherId());
            Teacher teacher = Teacher.builder()
                    .id(teacherLessonCount.teacherId())
                    .imageUrls(teacherImageJpaEntities.stream().map(TeacherImageJpaEntity::getImageUrl).toList())
                    .lessonCount(teacherLessonCount.lessonCount()).build();
            teachers.add(teacher);
        });
        return new Teachers(teachers);
    }

    @Override
    public void register(Teacher teacher) {
        TeacherJpaEntity teacherJpaEntity = TeacherJpaEntity.fromDomain(teacher);
        teacherJpaRepository.save(teacherJpaEntity);
        List<TeacherImageJpaEntity> teacherImageJpaEntities = teacher.getImageUrls().stream()
                .map(imageUrl -> TeacherImageJpaEntity.builder()
                        .teacher(teacherJpaEntity)
                        .imageUrl(imageUrl)
                        .build()).toList();
        teacherImageJpaRepository.saveAll(teacherImageJpaEntities);

        List<TeacherVideoJpaEntity> teacherVideoJpaEntities = teacher.getVideoUrls().stream()
                .map(videoUrl -> TeacherVideoJpaEntity.builder()
                        .teacher(teacherJpaEntity)
                        .videoUrl(videoUrl)
                        .build()).toList();
        teacherVideoJpaRepository.saveAll(teacherVideoJpaEntities);
    }

    @Override
    public Teacher findByMemberId(Long memberId) {
        return teacherJpaRepository.findByMemberId(memberId)
                .map(TeacherJpaEntity::toDomain)
                .orElseThrow(() -> new NotFoundException("해당하는 선생님을 찾을 수 없습니다."));
    }

    @Override
    public Teacher findByTeacherId(Long teacherId) {
        TeacherJpaEntity teacherJpaEntity = teacherJpaRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("해당하는 선생님을 찾을 수 없습니다."));
        List<TeacherVideoJpaEntity> videos = teacherVideoJpaRepository.findAllByTeacherId(teacherId);
        List<TeacherImageJpaEntity> images = teacherImageJpaRepository.findAllByTeacherId(teacherId);
        return teacherJpaEntity.toDomainWithImageAndVideo(images, videos);
    }
}
