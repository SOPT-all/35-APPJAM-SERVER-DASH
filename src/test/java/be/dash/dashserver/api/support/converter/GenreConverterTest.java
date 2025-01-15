package be.dash.dashserver.api.support.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.common.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenreConverterTest {

    @DisplayName("장르가 소문자로 들어와도 정상적으로 매핑된다.")
    @Test
    void convert() {
        GenreConverter genreConverter = new GenreConverter();
        assertThat(genreConverter.convert("hiPhop")).isEqualTo(Genre.HIPHOP);
    }

    @DisplayName("올바르지 않은 장르가 들어오는 경우 예외가 발생한다.")
    @Test
    void failConvert() {
        GenreConverter genreConverter = new GenreConverter();
        assertThatThrownBy(() -> genreConverter.convert("hiPhopp"))
                .isInstanceOf(DashApiException.class)
                .hasMessage("일치하는 장르가 없습니다.");
    }

    @DisplayName("장르가 들어오지 않은 경우 NULL을 반환한다.")
    @Test
    void convertNull() {
        GenreConverter genreConverter = new GenreConverter();
        assertThat(genreConverter.convert(null)).isNull();
    }
}
