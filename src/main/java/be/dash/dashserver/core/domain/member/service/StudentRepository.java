package be.dash.dashserver.core.domain.member.service;

import java.util.List;
import be.dash.dashserver.core.domain.member.Student;

public interface StudentRepository {

    List<Student> findAllStudentByIds(List<Long> studentIds);
}
