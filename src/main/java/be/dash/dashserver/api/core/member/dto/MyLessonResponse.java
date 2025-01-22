package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;

public record MyLessonResponse(long lessonId,
                               String lessonName,
                               String lessonImageUrl,
                               Genre lessonGenre,
                               Level lessonLevel,
                               String lessonLocation,
                               String detailedAddress,
                               LocalDateTime lessonStartDateTime,
                               LocalDateTime lessonEndDateTime,
                               APPLYSTATUS applyStatus) {
    public static MyLessonResponse from(Lesson lesson) {
        return new MyLessonResponse(
                lesson.getId(),
                lesson.getName(),
                lesson.getRepresentativeImageUrl(),
                lesson.getGenre(),
                lesson.getLevel(),
                lesson.getLocationName(),
                lesson.getDetailedAddress(),
                lesson.getRounds().getStartTime(),
                lesson.getRounds().getEndTime(),
                APPLYSTATUS.calculate(lesson.getStartTime(), lesson.getReservationCount(), lesson.getMaxReservationCount()
                ));
    }
}
