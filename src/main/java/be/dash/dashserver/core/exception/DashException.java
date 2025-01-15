package be.dash.dashserver.core.exception;

public class DashException extends RuntimeException{
    public DashException() {
    }

    public DashException(String message) {
        super(message);
    }
}
