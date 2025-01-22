package be.dash.dashserver.core.exception;

public class NotFoundException extends DashException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
