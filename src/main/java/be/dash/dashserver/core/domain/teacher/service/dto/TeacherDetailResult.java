package be.dash.dashserver.core.domain.teacher.service.dto;

import be.dash.dashserver.core.domain.lesson.Lessons;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;

public record TeacherDetailResult(TeacherLessonGenres teacherLessonGenres, String nickname, Lessons lessons) {
}
