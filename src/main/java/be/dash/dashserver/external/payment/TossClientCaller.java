package be.dash.dashserver.external.payment;

import java.util.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import be.dash.dashserver.core.domain.payment.PaymentClientApi;
import be.dash.dashserver.core.domain.payment.dto.CancelReason;
import be.dash.dashserver.core.domain.payment.dto.PaymentInformation;
import be.dash.dashserver.core.domain.payment.dto.PaymentResult;
import be.dash.dashserver.external.config.payment.TossProperties;

@Component
public class TossClientCaller implements PaymentClientApi {

    private static final String BASIC = "Basic ";
    private static final String CANCEL_REASON_INTERNAL_ERROR = "내부 오류";

    private final TossProperties tossProperties;
    private final RestClient tossRestClient;
    private final ResponseErrorHandler paymentClientResponseErrorHandler;
    private final String secretKey;

    public TossClientCaller(TossProperties tossProperties, RestClient tossRestClient, ResponseErrorHandler paymentClientResponseErrorHandler) {
        this.tossProperties = tossProperties;
        this.tossRestClient = tossRestClient;
        this.paymentClientResponseErrorHandler = paymentClientResponseErrorHandler;
        this.secretKey = Base64.getEncoder()
                .encodeToString((tossProperties.clientId()).getBytes());
    }

    @Override
    public PaymentResult purchase(PaymentInformation paymentInformation) {
        return tossRestClient.post()
                .uri(tossProperties.paymentUri())
                .header(HttpHeaders.AUTHORIZATION, BASIC + secretKey)
                .body(paymentInformation)
                .retrieve()
                .onStatus(paymentClientResponseErrorHandler)
                .body(PaymentResult.class);
    }

    @Override
    public PaymentResult cancelByInternalError(String paymentKey) {
        return tossRestClient.post()
                .uri(tossProperties.cancelUri() + paymentKey + "/cancel")
                .header(HttpHeaders.AUTHORIZATION, BASIC + secretKey)
                .body(new CancelReason(CANCEL_REASON_INTERNAL_ERROR))
                .retrieve()
                .onStatus(paymentClientResponseErrorHandler)
                .body(PaymentResult.class);
    }
}
