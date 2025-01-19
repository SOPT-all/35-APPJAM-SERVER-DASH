package be.dash.dashserver.database.core.teacher;

import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.service.TeacherImageRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TeacherImageRepositoryAdapter implements TeacherImageRepository {
    private final TeacherImageJpaRepository teacherImageJpaRepository;

    @Override
    public void saveAll(Teacher teacher) {
        List<TeacherImageJpaEntity> teacherImageJpaEntities = teacher.getImages().getImageUrls().stream()
                .map(url -> new TeacherImageJpaEntity(new TeacherJpaEntity(teacher), url)).toList();
        teacherImageJpaRepository.saveAll(teacherImageJpaEntities);
    }
}
