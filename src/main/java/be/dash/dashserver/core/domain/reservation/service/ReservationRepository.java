package be.dash.dashserver.core.domain.reservation.service;

public interface ReservationRepository {

    boolean existsByMemberIdAndLessonId(long memberId, long lessonId);

    long save(long memberId, long lessonId);
}
