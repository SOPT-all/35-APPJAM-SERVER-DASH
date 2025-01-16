package be.dash.dashserver.database.core.teacher;

import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.service.TeacherRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeacherRepositoryAdapter implements TeacherRepository {
    private final TeacherJpaRepository teacherJpaRepository;

    @Override
    public void save(Teacher teacher) {
        teacherJpaRepository.save(new TeacherJpaEntity(teacher));
    }
}
