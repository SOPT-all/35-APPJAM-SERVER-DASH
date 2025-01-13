package be.dash.dashserver.database.core.lesson;

import be.dash.dashserver.core.domain.common.Genre;
import be.dash.dashserver.core.domain.common.Level;
import be.dash.dashserver.database.core.common.BaseTimeEntity;
import be.dash.dashserver.database.core.teacher.TeacherJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

}
