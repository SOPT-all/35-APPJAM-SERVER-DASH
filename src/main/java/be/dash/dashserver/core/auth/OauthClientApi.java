package be.dash.dashserver.core.auth;

import be.dash.dashserver.core.auth.dto.OauthTokenResult;
import be.dash.dashserver.core.auth.dto.SocialInfoResult;

public interface OauthClientApi {
    OauthTokenResult getAccessToken(String redirctUrl, String code);

    SocialInfoResult getSocialUserInfo(String accessToken);

}
