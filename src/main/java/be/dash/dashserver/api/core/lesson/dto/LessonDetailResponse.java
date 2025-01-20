package be.dash.dashserver.api.core.lesson.dto;

import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;

import static be.dash.dashserver.api.core.lesson.dto.LessonRoundResponse.calculateRemainingDays;

public record LessonDetailResponse(
        String imageUrl,
        Genre genre,
        String name,
        Long teacherId,
        String teacherNickname,
        String teacherImageUrl,
        long reservationCount,
        long maxReservationCount,
        long price,
        String detail,
        String recommendation,
        Level level,
        long remainingDays,
        LessonRoundResponses lessonRound,
        String location,
        String streetAddress,
        String streetDetailAddress,
        String oldStreetAddress,
        long favoriteCount,
        boolean bookStatus,
        LessonStatus status
) {

    public LessonDetailResponse(Lesson lesson, boolean booked) {
        this(lesson.getRepresentativeImageUrl(),
                lesson.getGenre(),
                lesson.getName(),
                lesson.getTeacher().getId(),
                lesson.getTeacher().getNickName(),
                lesson.getTeacher().getRepresentativeImageUrl(),
                lesson.getReservationCount(),
                lesson.getMaxReservationCount(),
                lesson.getPrice(),
                lesson.getDetail(),
                lesson.getRecommendation(),
                lesson.getLevel(),
                calculateRemainingDays(lesson.getStartTime()),
                LessonRoundResponses.from(lesson.getRounds()),
                lesson.getLocation().getTitle(),
                lesson.getLocation().getRoadAddress(),
                lesson.getLocation().getDetailedAddress(),
                lesson.getLocation().getAddress(),
                lesson.getFavoriteCount(),
                booked,
                LessonStatus.from(lesson));
    }
}
