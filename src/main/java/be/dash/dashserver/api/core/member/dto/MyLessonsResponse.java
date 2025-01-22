package be.dash.dashserver.api.core.member.dto;

import java.util.List;

public record MyLessonsResponse(int count,
                                List<MyLessonResponse> lessons) {
    public static MyLessonsResponse from(List<MyLessonResponse> lessons) {
        return new MyLessonsResponse(lessons.size(), lessons);
    }
}
