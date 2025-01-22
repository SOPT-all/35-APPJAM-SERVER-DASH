package be.dash.dashserver.database.core.student;

import java.util.List;
import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.member.Student;
import be.dash.dashserver.core.domain.member.service.StudentRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryAdapter implements StudentRepository {

    private final StudentJpaRepository studentJpaRepository;

    @Override
    public List<Student> findAllStudentByIds(List<Long> studentIds) {
        return studentJpaRepository.findAllById(studentIds).stream().map(StudentJpaEntity::toDomain).toList();
    }
}
