package be.dash.dashserver.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import be.dash.dashserver.api.support.converter.GenreConverter;
import be.dash.dashserver.api.support.converter.LevelConverter;
import be.dash.dashserver.api.support.converter.LocalDateTimeConverter;
import be.dash.dashserver.api.support.converter.SortOptionConverter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final GenreConverter genreConverter;
    private final LevelConverter levelConverter;
    private final LocalDateTimeConverter localDateTimeConverter;
    private final SortOptionConverter sortOptionConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(genreConverter);
        registry.addConverter(levelConverter);
        registry.addConverter(localDateTimeConverter);
        registry.addConverter(sortOptionConverter);
    }
}
