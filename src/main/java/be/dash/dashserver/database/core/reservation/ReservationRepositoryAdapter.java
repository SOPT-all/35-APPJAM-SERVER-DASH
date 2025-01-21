package be.dash.dashserver.database.core.reservation;

import org.springframework.stereotype.Repository;
import be.dash.dashserver.core.domain.reservation.service.ReservationRepository;
import be.dash.dashserver.core.exception.DashException;
import be.dash.dashserver.database.core.lesson.LessonJpaEntity;
import be.dash.dashserver.database.core.lesson.LessonJpaEntityRepository;
import be.dash.dashserver.database.core.student.StudentJpaEntity;
import be.dash.dashserver.database.core.student.StudentJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;
    private final LessonJpaEntityRepository lessonJpaEntityRepository;
    private final StudentJpaRepository studentJpaRepository;

    @Override
    public boolean existsByMemberIdAndLessonId(long memberId, long lessonId) {
        return reservationJpaRepository.existsByMemberIdAndLessonId(memberId, lessonId);
    }

    @Override
    public long save(long memberId, long lessonId) {
        LessonJpaEntity lessonJpaEntity = lessonJpaEntityRepository.findById(lessonId)
                .orElseThrow(() -> new DashException("일치하는 수업이 존재하지 않습니다."));
        StudentJpaEntity studentJpaEntity = studentJpaRepository.findById(memberId)
                .orElseThrow(() -> new DashException("일치하는 학생이 존재하지 않습니다."));
        ReservationJpaEntity reservationJpaEntity = new ReservationJpaEntity(lessonJpaEntity, studentJpaEntity);
        reservationJpaRepository.save(reservationJpaEntity);
        return reservationJpaEntity.getId();
    }
}
