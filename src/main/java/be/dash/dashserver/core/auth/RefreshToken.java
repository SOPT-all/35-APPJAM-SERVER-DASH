package be.dash.dashserver.core.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshToken {
    private long memberId;
    private String refreshToken;
}
