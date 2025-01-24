package be.dash.dashserver.api.core.lesson.dto;

import java.math.BigDecimal;
import be.dash.dashserver.core.domain.reservation.command.CreateReservationCommand;

public record PaymentRequest(String paymentKey,
                             String orderId,
                             BigDecimal amount) {
    public CreateReservationCommand toCommand(long memberId, long lessonId) {
        return new CreateReservationCommand(paymentKey, orderId, amount, memberId, lessonId);
    }
}
