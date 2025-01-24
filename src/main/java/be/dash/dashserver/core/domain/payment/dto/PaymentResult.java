package be.dash.dashserver.core.domain.payment.dto;

import java.math.BigDecimal;

public record PaymentResult(String orderName,
                            String paymentKey,
                            String requestedAt,
                            String approvedAt,
                            BigDecimal totalAmount) {
}
