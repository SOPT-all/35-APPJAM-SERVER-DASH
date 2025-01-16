package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.exception.DashException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshToken {
    private long memberId;
    private String refreshToken;
}
