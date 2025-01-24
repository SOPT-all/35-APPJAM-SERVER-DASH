package be.dash.dashserver.external.payment;

import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.dash.dashserver.core.exception.PaymentClientException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentClientResponseErrorHandler implements ResponseErrorHandler {
    private static final String ERROR_MESSAGE = "message";
    private static final String ERROR_CODE = "code";

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        String errorMessage = objectMapper.readTree(httpResponse.getBody()).get(ERROR_MESSAGE).asText();
        String errorCode = objectMapper.readTree(httpResponse.getBody()).get(ERROR_CODE).asText();
        throw new PaymentClientException(errorCode + errorMessage);

    }
}
