package be.dash.dashserver.core.domain.reservation.command;

import java.math.BigDecimal;
import be.dash.dashserver.core.domain.payment.PaymentInformation;

public record CreateReservationCommand(String paymentKey,
                                       String orderId,
                                       BigDecimal amount,
                                       long memberId,
                                       long lessonId) {
    public PaymentInformation toPaymentInformation() {
        return new PaymentInformation(paymentKey, orderId, amount);
    }
}
