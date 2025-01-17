package be.dash.dashserver.core.fixture;

import java.time.LocalDateTime;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;

public class LessonFixture {
    private LessonFixture() {
    }

    public static Lesson create(long teacherId, long memberId, Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Lesson.builder()
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .imageUrl("image1")
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .location("서울 광진구")
                .streetAddress("서울 광진구 자양동")
                .oldStreetAddress("서울 광진구 구 주소")
                .favoriteCount(100L)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .individualPrice(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson createWithImage(long id, long teacherId, long memberId, Genre genre, Level level, String imageUrl) {
        return Lesson.builder()
                .id(id)
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .imageUrl(imageUrl)
                .startDateTime(LocalDateTime.now().minusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(5))
                .location("서울 광진구")
                .streetAddress("서울 광진구 자양동")
                .oldStreetAddress("서울 광진구 구 주소")
                .favoriteCount(100L)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .individualPrice(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson create(long teacherId, long memberId, Genre genre, Level level) {
        return Lesson.builder()
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .imageUrl("image1")
                .startDateTime(LocalDateTime.now().minusDays(1))
                .endDateTime(LocalDateTime.now().plusDays(5))
                .location("서울 광진구")
                .streetAddress("서울 광진구 자양동")
                .oldStreetAddress("서울 광진구 구 주소")
                .favoriteCount(100L)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .individualPrice(50000)
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
                .imageUrl("image1")
                .startDateTime(startDateTime)
                .endDateTime(LocalDateTime.now().plusDays(20))
                .location("서울 광진구")
                .streetAddress("서울 광진구 자양동")
                .oldStreetAddress("서울 광진구 구 주소")
                .favoriteCount(favoriteCount)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .individualPrice(50000)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Lesson create(long teacherId, long memberId, Genre genre, Level level, LocalDateTime startDateTime, LocalDateTime endDateTime, long favoriteCount) {
        return Lesson.builder()
                .teacher(TeacherFixture.create(teacherId, memberId))
                .name("박재연의 미친 웨이브")
                .genre(genre)
                .level(level)
                .imageUrl("image1")
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .location("서울 광진구")
                .streetAddress("서울 광진구 자양동")
                .oldStreetAddress("서울 광진구 구 주소")
                .favoriteCount(favoriteCount)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .individualPrice(50000)
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
                .imageUrl("image1")
                .startDateTime(startDateTime)
                .endDateTime(LocalDateTime.now().plusDays(20))
                .location("서울 광진구")
                .streetAddress("서울 광진구 자양동")
                .oldStreetAddress("서울 광진구 구 주소")
                .favoriteCount(favoriteCount)
                .reservationCount(50L)
                .maxReservationCount(100L)
                .detail("수업에 대한 상세 설명")
                .recommendation("수강 추천사")
                .individualPrice(50000)
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
