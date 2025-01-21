package be.dash.dashserver.core.domain.teacher.service;

import java.util.Optional;
import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.Teachers;

public interface TeacherRepository {
    void save(Teacher teacher);

    Teachers findTeachersSortByLessonCountsDesc(String keyword);

    void register(Teacher teacher);

    Optional<Teacher> findByMemberId(Long aLong);

    Teacher findByTeacherId(Long teacherId);
}
