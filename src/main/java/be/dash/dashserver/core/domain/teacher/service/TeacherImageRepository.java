package be.dash.dashserver.core.domain.teacher.service;

import be.dash.dashserver.core.domain.teacher.Teacher;

public interface TeacherImageRepository {
    void saveAll(Teacher teacher);
}
