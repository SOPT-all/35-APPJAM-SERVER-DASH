package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.exception.DashException;

public class UnAuthorizedException extends DashException {

    public UnAuthorizedException(String message) {
        super(message);
    }

    static UnAuthorizedException wrong(String token) {
        return new UnAuthorizedException(String.format("잘못된 토큰 (%s) 입니다", token));
    }

    static UnAuthorizedException expired(String token) {
        return new UnAuthorizedException(String.format("만료된 토큰 (%s) 입니다", token));
    }
}
