package be.dash.dashserver.api.support.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.member.SocialProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class SocialProviderConverterTest {

    @DisplayName("레벨이 소문자로 들어와도 정상적으로 매핑된다.")
    @Test
    void convert() {
        SocialProviderConverter socialProviderConverter = new SocialProviderConverter();
        assertThat(socialProviderConverter.convert("kakao")).isEqualTo(SocialProvider.KAKAO);
    }

    @DisplayName("올바르지 않은 소셜로그인 제공자 형식이 들어오는 경우 예외가 발생한다.")
    @Test
    void failConvert() {
        SocialProviderConverter socialProviderConverter = new SocialProviderConverter();
        assertThatThrownBy(() -> socialProviderConverter.convert("kakaoo"))
                .isInstanceOf(DashApiException.class)
                .hasMessage("소셜로그인 제공자를 잘못 입력하셨습니다.");
    }
}
