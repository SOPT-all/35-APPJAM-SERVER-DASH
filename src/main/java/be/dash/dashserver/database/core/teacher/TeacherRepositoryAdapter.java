package be.dash.dashserver.database.core.teacher;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.Teachers;
import be.dash.dashserver.core.domain.teacher.projection.TeacherLessonCount;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import be.dash.dashserver.core.exception.DashException;
import be.dash.dashserver.database.core.lesson.LessonJpaEntityRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeacherRepositoryAdapter implements TeacherRepository {
    private final TeacherJpaRepository teacherJpaRepository;
    private final LessonJpaEntityRepository lessonJpaEntityRepository;
    private final TeacherImageJpaRepository teacherImageJpaRepository;

    @Override
    public void save(Teacher teacher) {
        teacherJpaRepository.save(new TeacherJpaEntity(teacher));
    }

    @Override
    public Teachers findTeachersSortByLessonCountsDesc() {
        List<Teacher> teachers = new ArrayList<>();
        List<TeacherLessonCount> teacherLessonCounts = lessonJpaEntityRepository.findTeacherLessonCountsDesc();
        teacherLessonCounts.forEach(teacherLessonCount -> {
            TeacherImageJpaEntity teacherImageJpaEntity = teacherImageJpaRepository.findFirstByTeacherId(teacherLessonCount.teacherId())
                    .orElseThrow(() -> new DashException("등록된 선생님의 사진이 없습니다."));
            Teacher teacher = Teacher.builder()
                    .id(teacherLessonCount.teacherId())
                    .imageUrl(teacherImageJpaEntity.getImageUrl())
                    .lessonCount(teacherLessonCount.lessonCount()).build();
            teachers.add(teacher);
        });
        return new Teachers(teachers);
    }
}
