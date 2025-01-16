package be.dash.dashserver.core.exception;

public class BadRequestException extends DashException {
    public BadRequestException() {
        super();
    }
    public BadRequestException(String message) {
        super(message);
    }
}
