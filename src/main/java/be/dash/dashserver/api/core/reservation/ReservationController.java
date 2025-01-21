package be.dash.dashserver.api.core.reservation;

import java.net.URI;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import be.dash.dashserver.api.support.MemberId;
import be.dash.dashserver.core.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{lessonId}")
    public ResponseEntity<Void> reservation(
            @MemberId Long memberId,
            @PathVariable @Min(value = 1L, message = "수업의 식별자는 양수로 이루어져야 합니다.") long lessonId) {
        long reservationId = reservationService.reserve(memberId, lessonId);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }
}
