package be.dash.dashserver.core.exception;

public class DashException extends RuntimeException{
    public DashException() {
        super();
    }

    public DashException(String message) {
        super(message);
    }
}
