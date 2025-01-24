package be.dash.dashserver.core.domain.payment;

import be.dash.dashserver.core.domain.payment.dto.PaymentInformation;
import be.dash.dashserver.core.domain.payment.dto.PaymentResult;

public interface PaymentClientApi {
    PaymentResult purchase(PaymentInformation paymentInformation);
    PaymentResult cancelByInternalError(String paymentKey);
}
