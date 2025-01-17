package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.core.domain.lesson.Lesson;
import be.dash.dashserver.database.core.common.BaseTimeEntity;
import be.dash.dashserver.database.core.teacher.TeacherImageJpaEntity;
import be.dash.dashserver.database.core.teacher.TeacherJpaEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lesson")
public class LessonJpaEntity extends BaseTimeEntity {

    @Id
    @Column(name = "lesson_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private TeacherJpaEntity teacher;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String oldStreetAddress;

    @Column(nullable = false)
    private Long favoriteCount;

    @Column(nullable = false)
    private Long reservationCount;

    @Column(nullable = false)
    private Long maxReservationCount;

    @Column(nullable = false)
    private String detail;

    @Column(nullable = false)
    private String recommendation;

    @Column(nullable = false)
    private Integer individualPrice;

    public LessonJpaEntity(Lesson lesson) {
        this.id = lesson.getId();
        this.teacher = new TeacherJpaEntity(lesson.getTeacher());
        this.name = lesson.getName();
        this.genre = lesson.getGenre();
        this.level = lesson.getLevel();
        this.startDateTime = lesson.getStartDateTime();
        this.endDateTime = lesson.getEndDateTime();
        this.location = lesson.getLocation().getTitle();
        this.streetAddress = lesson.getLocation().getRoadAddress();
        this.oldStreetAddress = lesson.getLocation().getAddress();
        this.favoriteCount = lesson.getFavoriteCount();
        this.reservationCount = lesson.getReservationCount();
        this.maxReservationCount = lesson.getMaxReservationCount();
        this.detail = lesson.getDetail();
        this.recommendation = lesson.getRecommendation();
        this.individualPrice = lesson.getIndividualPrice();
    }

    public Lesson toDomain() {
        return Lesson.builder()
                .id(id)
                .teacher(teacher.toDomain())
                .name(name)
                .genre(genre)
                .level(level)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .location(location)
                .streetAddress(streetAddress)
                .oldStreetAddress(oldStreetAddress)
                .favoriteCount(favoriteCount)
                .reservationCount(reservationCount)
                .maxReservationCount(maxReservationCount)
                .detail(detail)
                .recommendation(recommendation)
                .individualPrice(individualPrice)
                .createdAt(getCreatedAt())
                .build();
    }

    public Lesson toDomainWithTeacherImage(List<TeacherImageJpaEntity> teacherImageJpaEntitys) {
        return Lesson.builder()
                .id(id)
                .teacher(teacher.toDomainWithTeacherImage(teacherImageJpaEntitys))
                .name(name)
                .genre(genre)
                .level(level)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .location(location)
                .streetAddress(streetAddress)
                .oldStreetAddress(oldStreetAddress)
                .favoriteCount(favoriteCount)
                .reservationCount(reservationCount)
                .maxReservationCount(maxReservationCount)
                .detail(detail)
                .recommendation(recommendation)
                .individualPrice(individualPrice)
                .createdAt(getCreatedAt())
                .build();
    }
}
