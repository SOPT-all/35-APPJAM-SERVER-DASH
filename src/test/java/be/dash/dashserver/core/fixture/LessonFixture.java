package be.dash.dashserver.core.fixture;

import java.time.LocalDateTime;
import java.util.List;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.core.domain.lesson.LessonImages;
import be.dash.dashserver.core.domain.lesson.LessonVideos;
import be.dash.dashserver.core.domain.lesson.Location;
import be.dash.dashserver.core.domain.lesson.Round;
import be.dash.dashserver.core.domain.lesson.Rounds;

public class LessonFixture {
    private LessonFixture() {
    }

    public static Lesson create(long teacherId, long memberId, Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Lesson.builder()
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .images(new LessonImages(List.of("https://image.com/1", "https://image.com/2")))
                .videos(new LessonVideos(List.of("https://video.com/1", "https://video.com/2")))
                .rounds(new Rounds(List.of(new Round(startDateTime, endDateTime))))
                .location(new Location("서울 광진구", "서울 광진구 자양동", "서울 광진구 구 주소", "2층 1호"))
                .favoriteCount(100L)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .price(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson create(long id, long teacherId, long memberId, Genre genre, Level level) {
        return Lesson.builder()
                .id(id)
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .images(new LessonImages(List.of("https://image.com/1", "https://image.com/2")))
                .videos(new LessonVideos(List.of("https://video.com/1", "https://video.com/2")))
                .rounds(new Rounds(List.of(new Round(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(5)))))
                .location(new Location("서울 광진구", "서울 광진구 자양동", "서울 광진구 구 주소", "2층 1호"))
                .favoriteCount(100L)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .price(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson create(long teacherId, long memberId, Genre genre, Level level) {
        return Lesson.builder()
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .images(new LessonImages(List.of("https://image.com/1", "https://image.com/2")))
                .videos(new LessonVideos(List.of("https://video.com/1", "https://video.com/2")))
                .rounds(new Rounds(List.of(new Round(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(5)))))
                .location(new Location("서울 광진구", "서울 광진구 자양동", "서울 광진구 구 주소", "2층 1호"))
                .favoriteCount(100L)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .price(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson create(long id, long teacherId, long memberId, LocalDateTime startDateTime, Long favoriteCount) {
        return Lesson.builder()
                .id(id)
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(Genre.HIPHOP)
                .level(Level.BEGINNER)
                .images(new LessonImages(List.of("https://image.com/1", "https://image.com/2")))
                .videos(new LessonVideos(List.of("https://video.com/1", "https://video.com/2")))
                .rounds(new Rounds(List.of(new Round(startDateTime, LocalDateTime.now().plusDays(5)))))
                .location(new Location("서울 광진구", "서울 광진구 자양동", "서울 광진구 구 주소", "2층 1호"))
                .favoriteCount(favoriteCount)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .price(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson create(long teacherId, long memberId, Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, long favoriteCount) {
        return Lesson.builder()
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .images(new LessonImages(List.of("https://image.com/1", "https://image.com/2")))
                .videos(new LessonVideos(List.of("https://video.com/1", "https://video.com/2")))
                .rounds(new Rounds(List.of(new Round(startDateTime, endDateTime))))
                .location(new Location("서울 광진구", "서울 광진구 자양동", "서울 광진구 구 주소", "2층 1호"))
                .favoriteCount(favoriteCount)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .price(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson create(long id, long teacherId, long memberId, LocalDateTime startDateTime, Long favoriteCount, LocalDateTime createdAt) {
        return Lesson.builder()
                .id(id)
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(Genre.HIPHOP)
                .level(Level.BEGINNER)
                .images(new LessonImages(List.of("https://image.com/1", "https://image.com/2")))
                .videos(new LessonVideos(List.of("https://video.com/1", "https://video.com/2")))
                .rounds(new Rounds(List.of(new Round(startDateTime, LocalDateTime.now().plusDays(20)))))
                .location(new Location("서울 광진구", "서울 광진구 자양동", "서울 광진구 구 주소", "2층 1호"))
                .favoriteCount(favoriteCount)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .price(50000)
                .createdAt(createdAt)
                .build();
    }

    public static Lesson create(long teacherId, long memberId, Genre genre, LocalDateTime startDateTime, LocalDateTime endDateTime, long reservationCount) {
        return Lesson.builder()
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(Level.BEGINNER)
                .imageUrl("image1")
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .location("서울 광진구")
                .streetAddress("서울 광진구 자양동")
                .oldStreetAddress("서울 광진구 구 주소")
                .favoriteCount(100L)
                .reservationCount(reservationCount)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .individualPrice(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
