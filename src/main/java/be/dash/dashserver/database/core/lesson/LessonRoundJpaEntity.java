package be.dash.dashserver.database.core.lesson;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "lesson_round")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonRoundJpaEntity {

    @Id
    @Column(name = "lesson_round_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long lessonId;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    public LessonRoundJpaEntity(Long lessonId, LocalDateTime startTime, LocalDateTime endTime) {
        this.lessonId = lessonId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
