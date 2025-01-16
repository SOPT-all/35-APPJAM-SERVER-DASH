package be.dash.dashserver.api.support.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LessonSortOptionConverterTest {

    @DisplayName("정렬 기준이 소문자로 들어와도 정상적으로 매핑된다.")
    @Test
    void convert() {
        SortOptionConverter sortOptionConverter = new SortOptionConverter();
        assertThat(sortOptionConverter.convert("LATESt")).isEqualTo(LessonSortOption.LATEST);
    }

    @DisplayName("올바르지 않은 정렬 기준이 들어오는 경우 예외가 발생한다.")
    @Test
    void failConvert() {
        SortOptionConverter sortOptionConverter = new SortOptionConverter();
        assertThatThrownBy(() -> sortOptionConverter.convert("LATEStt"))
                .isInstanceOf(DashApiException.class)
                .hasMessage("일치하는 정렬 조건이 없습니다.");
    }

    @DisplayName("장르가 들어오지 않은 경우 예외가 발생한다..")
    @Test
    void failConvertIfNull() {
        SortOptionConverter sortOptionConverter = new SortOptionConverter();
        assertThatThrownBy(() -> sortOptionConverter.convert(null))
                .isInstanceOf(DashApiException.class)
                .hasMessage("일치하는 정렬 조건이 없습니다.");
    }
}
