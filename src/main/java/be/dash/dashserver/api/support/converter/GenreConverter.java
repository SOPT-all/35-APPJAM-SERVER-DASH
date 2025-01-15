package be.dash.dashserver.api.support.converter;

import java.util.Objects;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import be.dash.dashserver.api.exception.DashApiException;
import be.dash.dashserver.core.domain.common.Genre;

@Component
public class GenreConverter implements Converter<String, Genre> {
    @Override
    public Genre convert(String source) {
        try {
            if (Objects.isNull(source)) {
                return null;
            }
            return Genre.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new DashApiException("일치하는 장르가 없습니다.");
        }
    }
}
