package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.exception.DashException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtTokenValidator jwtTokenValidator;
    private final JwtTokenExtractor jwtTokenExtractor;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenParser tokenParser;

    public Token reissue(String refreshToken) {

        RefreshToken token = getValidRefreshToken(refreshToken);

        String memberId = jwtTokenExtractor.getSubject(token.getRefreshToken());
        Role role = jwtTokenExtractor.getRole(token.getRefreshToken());
        String newAccessToken = jwtTokenGenerator.createAccessToken(memberId, role);
        String newRefreshToken = jwtTokenGenerator.createRefreshToken(memberId, role);

        refreshTokenRepository.save(newRefreshToken, Long.parseLong(memberId));

        return new Token(newAccessToken, newRefreshToken);
    }

    private RefreshToken getValidRefreshToken(String refreshToken) {
        String parsedToken = tokenParser.getToken(refreshToken);
        jwtTokenValidator.validate(parsedToken);
        RefreshToken token = refreshTokenRepository.findByRefreshToken(parsedToken)
                .orElseThrow(() -> UnAuthorizedException.wrong(refreshToken));
        return token;
    }
}
