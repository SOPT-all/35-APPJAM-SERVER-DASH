package be.dash.dashserver.external.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import be.dash.dashserver.core.auth.OauthClientApi;
import be.dash.dashserver.core.auth.dto.OauthTokenResult;
import be.dash.dashserver.core.auth.dto.SocialInfoResult;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KaKaoClientCaller implements OauthClientApi {

    @Value("${kakao.client-id}")
    private String clientId;
    private final RestClient restClient;

    @Override
    public OauthTokenResult getAccessToken(String redirectUrl, String code) {
        return restClient
                .method(HttpMethod.POST)
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(createHttpBody(redirectUrl, code))
                .retrieve()
                .toEntity(OauthTokenResult.class)
                .getBody();

    }

    @Override
    public SocialInfoResult getSocialUserInfo(String accessToken) {
        return restClient
                .method(HttpMethod.GET)
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", createAuthorizationHeader(accessToken))
                .retrieve()
                .toEntity(SocialInfoResult.class)
                .getBody();
    }

    private String createHttpBody(String redirectUrl, String code) {
        return new StringBuilder("grant_type=authorization_code")
                .append("&client_id=").append(clientId)
                .append("&redirect_uri=").append(redirectUrl)
                .append("&code=").append(code)
                .toString();
    }

    private String createAuthorizationHeader(String accessToken) {
        return "Bearer " + accessToken;
    }
}
