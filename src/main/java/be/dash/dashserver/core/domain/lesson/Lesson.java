package be.dash.dashserver.core.domain.lesson;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.teacher.Teacher;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Lesson {

    private final long id;
    private final Teacher teacher;
    private final Rounds rounds;
    private final Location location;
    private final Genre genre;
    private final Level level;
    private final Images images;
    private final Videos videos;
    private final String name;
    private final Long favoriteCount;
    private final Long reservationCount;
    private final Long maxReservationCount;
    private final String detail;
    private final String recommendation;
    private final int price;
    private final LocalDateTime createdAt;


    @Builder
    public Lesson(long id, Teacher teacher, String name, Genre genre, Level level, Location location, Long favoriteCount, Long reservationCount, Long maxReservationCount, String detail, String recommendation, int price, LocalDateTime createdAt, Images images, Videos videos, Rounds rounds) {
        this.id = id;
        this.teacher = teacher;
        this.name = name;
        this.genre = genre;
        this.level = level;
        this.location = location;
        this.favoriteCount = favoriteCount;
        this.reservationCount = reservationCount;
        this.maxReservationCount = maxReservationCount;
        this.detail = detail;
        this.recommendation = recommendation;
        this.price = price;
        this.createdAt = createdAt;
        this.images = images;
        this.videos = videos;
        this.rounds = rounds;
    }

    public String getRepresentativeImageUrl() {
        return images.getFirstImage();
    }

    public LocalDateTime getStartTime() {
        return rounds.getStartTime();
    }

    public LocalDateTime getEndTime() {
        return rounds.getEndTime();
    }
}
