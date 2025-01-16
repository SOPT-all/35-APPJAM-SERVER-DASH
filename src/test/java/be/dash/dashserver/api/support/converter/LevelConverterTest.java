package be.dash.dashserver.api.support.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.common.Level;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LevelConverterTest {

    @DisplayName("레벨이 소문자로 들어와도 정상적으로 매핑된다.")
    @Test
    void convert() {
        LevelConverter levelConverter = new LevelConverter();
        assertThat(levelConverter.convert("BEGINNEr")).isEqualTo(Level.BEGINNER);
    }

    @DisplayName("올바르지 않은 레벨 형식이 들어오는 경우 예외가 발생한다.")
    @Test
    void failConvert() {
        LevelConverter levelConverter = new LevelConverter();
        assertThatThrownBy(() -> levelConverter.convert("BEGINNErr"))
                .isInstanceOf(DashApiException.class)
                .hasMessage("일치하는 난이도가 없습니다.");
    }

    @DisplayName("레벨이 들어오지 않은 경우 NULL을 반환한다.")
    @Test
    void convertNull() {
        LevelConverter levelConverter = new LevelConverter();
        assertThat(levelConverter.convert(null)).isNull();
    }
}
