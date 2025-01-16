package be.dash.dashserver.api.support.converter;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import be.dash.dashserver.api.exception.DashApiException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalDateTimeConverterTest {

    @DisplayName("날짜 형식이 올바르게 들어온 경우 정상적으로 매핑된다.")
    @Test
    void convert() {
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        assertThat(localDateTimeConverter.convert("2025-01-13T18:26:27")).isEqualTo(LocalDateTime.of(2025, 1, 13, 18, 26, 27));
    }

    @DisplayName("올바르지 않은 날짜 형식이 들어오는 경우 예외가 발생한다.")
    @Test
    void failConvert() {
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        assertThatThrownBy(() -> localDateTimeConverter.convert("2025-01-33T18:26:27"))
                .isInstanceOf(DashApiException.class)
                .hasMessage("날짜 형식이 올바르지 않습니다.");
    }

    @DisplayName("날짜 형식이 들어오지 않는 경우 NULL을 반환한다.")
    @Test
    void convertNull() {
        LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
        assertThat(localDateTimeConverter.convert(null)).isNull();
    }
}
