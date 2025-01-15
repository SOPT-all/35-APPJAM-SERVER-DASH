package be.dash.dashserver.api.exception;

public class DashApiException extends RuntimeException {
    public DashApiException() {
        super();
    }

    public DashApiException(String message) {
        super(message);
    }
}
