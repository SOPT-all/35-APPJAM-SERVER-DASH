package be.dash.dashserver.core.domain.payment;

import be.dash.dashserver.api.core.lesson.dto.PaymentRequest;
import be.dash.dashserver.core.domain.payment.dto.CancelReason;
import be.dash.dashserver.core.domain.payment.dto.PaymentResult;

public interface PaymentClientApi {
    PaymentResult purchase(PaymentInformation paymentInformation);
    PaymentResult cancelByInternalError(String paymentKey);
}
