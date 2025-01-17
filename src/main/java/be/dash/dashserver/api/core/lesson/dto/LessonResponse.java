package be.dash.dashserver.api.core.lesson.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.lesson.Lesson;

public record LessonResponse(
        long id,
        String genre,
        String level,
        String name,
        String imageUrl,
        String teacherProfileImage,
        String teacherName,
        String startDate,
        String endDate,
        String location,
        long remainingDays) {

    public LessonResponse(Lesson lesson) {
        this(lesson.getId(),
                lesson.getGenre().name(),
                lesson.getLevel().name(),
                lesson.getName(),
                lesson.getImageUrl(),
                lesson.getTeacher().getImageUrls().get(0),
                lesson.getTeacher().getMember().getName(),
                lesson.getStartDateTime().toString(),
                lesson.getEndDateTime().toString(),
                lesson.getLocation().getTitle(),
                calculateRemainingDays(lesson.getStartDateTime())
        );
    }

    private static long calculateRemainingDays(LocalDateTime startDateTime) {
        Duration duration = Duration.between(LocalDateTime.now(), startDateTime);
        return duration.toDays();
    }
}
