package be.dash.dashserver.core.domain.lesson;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.teacher.Teacher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Lesson {

    private Long id;
    private Teacher teacher;
    private String name;
    private Genre genre;
    private Level level;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String location;
    private String streetAddress;
    private String oldStreetAddress;
    private Long favoriteCount;
    private Long reservationCount;
    private Long maxReservationCount;
    private String detail;
    private String recommendation;
    private Integer individualPrice;
    private LocalDateTime createdAt;

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
