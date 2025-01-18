package be.dash.dashserver.core.domain.lesson.command;

import java.time.LocalDateTime;
import java.util.List;
import be.dash.dashserver.api.core.lesson.dto.RoundRequest;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.LessonImages;
import be.dash.dashserver.core.domain.lesson.LessonVideos;
import be.dash.dashserver.core.domain.lesson.Location;
import be.dash.dashserver.core.domain.lesson.Round;
import be.dash.dashserver.core.domain.lesson.Rounds;
import be.dash.dashserver.core.domain.teacher.Teacher;

public record CreateLessonCommand(List<String> imageUrls,
                                  String name,
                                  String detail,
                                  List<String> videoUrls,
                                  long maxReservationCount,
                                  Genre genre,
                                  Level level,
                                  String recommendation,
                                  int price,
                                  String location,
                                  String streetAddress,
                                  String oldStreetAddress,
                                  String detailedAddress,
                                  List<RoundRequest> times,
                                  Long memberId) {
    public LocalDateTime getStartTime() {
        return times.get(0).startTime();
    }
    public LocalDateTime getEndTime() {
        return times.get(times.size() - 1).endTime();
    }

    public Lesson toDomainWith(Teacher teacher) {
        return Lesson.builder()
                .teacher(teacher)
                .name(name)
                .genre(genre)
                .level(level)
                .location(new Location(location, streetAddress, oldStreetAddress, detailedAddress))
                .images(new LessonImages(imageUrls))
                .videos(new LessonVideos(videoUrls))
                .favoriteCount(0L)
                .reservationCount(0L)
                .maxReservationCount(maxReservationCount)
                .detail(detail)
                .recommendation(recommendation)
                .price(price)
                .rounds(new Rounds(times.stream().map(time -> new Round(time.startTime(), time.endTime())).toList()))
                .build();
    }
}
