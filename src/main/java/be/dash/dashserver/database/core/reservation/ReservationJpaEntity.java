package be.dash.dashserver.database.core.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import be.dash.dashserver.core.domain.reservation.Reservation;
import be.dash.dashserver.database.core.common.BaseCreatedAtEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reservation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationJpaEntity extends BaseCreatedAtEntity {

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "student_id")
    private Long studentId;

    public Reservation toDomain() {
        return new Reservation(id, lessonId, studentId, getCreatedAt());
    }
}
