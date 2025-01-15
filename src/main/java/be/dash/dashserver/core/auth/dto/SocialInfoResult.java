package be.dash.dashserver.core.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SocialInfoResult(
        String id,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
}
