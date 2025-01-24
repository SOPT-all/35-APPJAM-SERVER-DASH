package be.dash.dashserver.core.domain.payment;

import java.math.BigDecimal;

public record PaymentInformation(String paymentKey,
                                 String orderId,
                                 BigDecimal amount) {
}
