package be.dash.dashserver.core.domain.teacher.service;

import be.dash.dashserver.core.domain.teacher.Teacher;
import be.dash.dashserver.core.domain.teacher.Teachers;

public interface TeacherRepository {
    void save(Teacher teacher);

    Teachers findTeachersSortByLessonCountsDesc(String keyword);

    void register(Teacher teacher);

    Teacher findByMemberId(Long aLong);

    Teacher findByTeacherId(Long teacherId);
}
