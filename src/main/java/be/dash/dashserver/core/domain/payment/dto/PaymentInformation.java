package be.dash.dashserver.core.domain.payment.dto;

import java.math.BigDecimal;

public record PaymentInformation(String paymentKey,
                                 String orderId,
                                 BigDecimal amount) {
}
