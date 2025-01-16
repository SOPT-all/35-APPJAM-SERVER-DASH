package be.dash.dashserver.api.core.teacher.dto;

import java.util.List;
import be.dash.dashserver.core.domain.teacher.TeacherLessonGenres;

public record TeacherResponses(List<TeacherResponse> teachers) {

    public static TeacherResponses from(List<TeacherLessonGenres> teacherLessonGenres) {
        List<TeacherResponse> responses = teacherLessonGenres.stream().map(TeacherResponse::new).toList();
        return new TeacherResponses(responses);
    }
}
