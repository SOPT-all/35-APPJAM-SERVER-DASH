package be.dash.dashserver.api.support.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.lesson.LessonSortOption;

@Component
public class SortOptionConverter implements Converter<String, LessonSortOption> {

    @Override
    public LessonSortOption convert(String source) {
        try {
            return LessonSortOption.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DashApiException("일치하는 정렬 조건이 없습니다.");
        }
    }
}
