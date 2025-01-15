package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.exception.DashException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshToken {
    private long memberId;
    private String refreshToken;

    void validateOwnerId(long memberId) {
        if (this.memberId != memberId) {
            throw new DashException("잘못된 RefreshToken입니다.");
        }
    }
}
