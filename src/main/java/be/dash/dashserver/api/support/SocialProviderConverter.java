package be.dash.dashserver.api.support;

import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.member.SocialProvider;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SocialProviderConverter implements Converter<String, SocialProvider> {

    @Override
    public SocialProvider convert(String source) {
        try {
            return SocialProvider.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DashApiException("소셜로그인 제공자를 잘못 입력하셨습니다.");
        }
    }
}

