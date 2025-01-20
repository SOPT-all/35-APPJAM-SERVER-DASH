package be.dash.dashserver.api.core.lesson.dto;

import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;

import static be.dash.dashserver.api.core.lesson.dto.LessonRoundResponse.calculateRemainingDays;

public record LessonResponse(
        long id,
        Genre genre,
        Level level,
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
                lesson.getGenre(),
                lesson.getLevel(),
                lesson.getName(),
                lesson.getRepresentativeImageUrl(),
                lesson.getTeacher().getImages().getFirstImage(),
                lesson.getTeacher().getMember().getName(),
                lesson.getStartTime().toString(),
                lesson.getEndTime().toString(),
                lesson.getLocation().getTitle(),
                calculateRemainingDays(lesson.getStartTime())
        );
    }
}
