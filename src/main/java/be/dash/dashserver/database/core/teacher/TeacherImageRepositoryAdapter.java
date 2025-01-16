package be.dash.dashserver.database.core.teacher;

import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.service.TeacherImageRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeacherImageRepositoryAdapter implements TeacherImageRepository {
    private final TeacherImageJpaRepository teacherImageJpaRepository;

    @Override
    public void save(Teacher teacher) {
        teacherImageJpaRepository.save(new TeacherImageJpaEntity(new TeacherJpaEntity(teacher), teacher.getImageUrl()));
    }
}
