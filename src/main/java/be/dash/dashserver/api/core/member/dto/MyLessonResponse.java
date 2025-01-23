package be.dash.dashserver.api.core.member.dto;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;

public record MyLessonResponse(long id,
                               String name,
                               String imageUrl,
                               Genre genre,
                               Level level,
                               String location,
                               String detailedAddress,
                               LocalDateTime startDateTime,
                               LocalDateTime endDateTime,
                               ApplyStatus applyStatus) {
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
                ApplyStatus.calculate(lesson.getStartTime(), lesson.getReservationCount(), lesson.getMaxReservationCount()
                ));
    }
}
