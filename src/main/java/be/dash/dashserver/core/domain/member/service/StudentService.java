package be.dash.dashserver.core.domain.member.service;

import java.util.List;
import org.springframework.stereotype.Service;
import be.dash.dashserver.core.domain.member.Student;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> findAllByIds(List<Long> studentIds) {
        return studentRepository.findAllStudentByIds(studentIds);
    }
}
