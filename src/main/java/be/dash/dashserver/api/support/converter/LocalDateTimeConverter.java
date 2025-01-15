package be.dash.dashserver.api.support.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import be.dash.dashserver.api.exception.DashApiException;

@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final String PATTERN = "yyyy-MM-dd['T'HH[:mm[:ss[.SSSSSS]]]]";

    @Override
    public LocalDateTime convert(String source) {
        try {
            if (Objects.isNull(source)) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
            return LocalDateTime.parse(source, formatter);
        } catch (DateTimeParseException e) {
            throw new DashApiException("날짜 형식이 올바르지 않습니다.");
        }
    }
}
