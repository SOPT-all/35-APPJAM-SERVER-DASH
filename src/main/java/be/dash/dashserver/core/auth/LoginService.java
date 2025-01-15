package be.dash.dashserver.core.auth;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.dash.dashserver.core.auth.command.LoginCommand;
import be.dash.dashserver.core.auth.dto.OauthTokenResult;
import be.dash.dashserver.core.auth.dto.SocialInfoResult;
import be.dash.dashserver.core.domain.member.AuthMember;
import be.dash.dashserver.core.domain.member.service.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final OauthClientApi oauthClientApi;
    private final MemberRepository memberRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Token login(LoginCommand command) {

        SocialInfoResult socialUserInfo = getSocialInfo(command);
        AuthMember authMember = loadOrCreateMember(command, socialUserInfo);
        Token token = createToken(authMember);
        updateRefreshToken(token.refreshToken(), authMember.getId());

        return token;
    }

    private void updateRefreshToken(String refreshToken, long id) {
        refreshTokenRepository.save(refreshToken, id);
    }

    private Token createToken(AuthMember authMember) {
        return new Token(
               jwtTokenGenerator.createAccessToken(String.valueOf(authMember.getId()), authMember.getRole()),
               jwtTokenGenerator.createRefreshToken(String.valueOf(authMember.getId()), authMember.getRole())
       );
    }

    private AuthMember loadOrCreateMember(LoginCommand command, SocialInfoResult socialUserInfo) {
        AuthMember retrievedAuthMember = memberRepository.findBySocialIdAndProviderOrNull(
                socialUserInfo.id(),
                command.provider()
        );
        if (retrievedAuthMember != null) {
            return retrievedAuthMember;
        }
        return memberRepository.save(
                AuthMember.create(command.provider(),
                        socialUserInfo.id(),
                        socialUserInfo.kakaoAccount().email(),
                        socialUserInfo.kakaoAccount().profile().nickname()
                )
        );
    }

    private SocialInfoResult getSocialInfo(LoginCommand command) {
        OauthTokenResult tokenResult = oauthClientApi.getAccessToken(command.redirectUrl(), command.code());
        SocialInfoResult socialUserInfo = oauthClientApi.getSocialUserInfo(tokenResult.accessToken());
        return socialUserInfo;
    }
}
