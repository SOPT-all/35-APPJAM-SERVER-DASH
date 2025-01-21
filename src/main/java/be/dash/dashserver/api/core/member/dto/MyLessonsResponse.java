package be.dash.dashserver.api.core.member.dto;

import java.util.List;
import be.dash.dashserver.core.domain.member.service.ReservationResult;

public record MyLessonsResponse(int count,
                                List<MyLessonResponse> lessons) {
    public static MyLessonsResponse from(List<MyLessonResponse> lessons) {
        return new MyLessonsResponse(lessons.size(), lessons);
    }
}
