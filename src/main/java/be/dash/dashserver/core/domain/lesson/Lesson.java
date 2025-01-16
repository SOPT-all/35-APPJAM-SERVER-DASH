package be.dash.dashserver.core.domain.lesson;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.teacher.Teacher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Lesson {

    private final Long id;
    private final Teacher teacher;
    private final String name;
    private final Genre genre;
    private final Level level;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String location;
    private final String streetAddress;
    private final String oldStreetAddress;
    private final Long favoriteCount;
    private final Long reservationCount;
    private final Long maxReservationCount;
    private final String detail;
    private final String recommendation;
    private final Integer individualPrice;
    private final LocalDateTime createdAt;

    @Builder
    public Lesson(Long id, Teacher teacher, String name, Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, String location, String streetAddress, String oldStreetAddress, Long favoriteCount, Long reservationCount, Long maxReservationCount, String detail, String recommendation, Integer individualPrice, LocalDateTime createdAt) {
        this.id = id;
        this.teacher = teacher;
        this.name = name;
        this.genre = genre;
        this.level = level;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.streetAddress = streetAddress;
        this.oldStreetAddress = oldStreetAddress;
        this.favoriteCount = favoriteCount;
        this.reservationCount = reservationCount;
        this.maxReservationCount = maxReservationCount;
        this.detail = detail;
        this.recommendation = recommendation;
        this.individualPrice = individualPrice;
        this.createdAt = createdAt;
    }
}
