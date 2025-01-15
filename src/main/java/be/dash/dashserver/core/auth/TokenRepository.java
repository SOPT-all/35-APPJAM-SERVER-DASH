package be.dash.dashserver.core.auth;

public interface TokenRepository {
    void save(String refreshToken, long memberId);
}
