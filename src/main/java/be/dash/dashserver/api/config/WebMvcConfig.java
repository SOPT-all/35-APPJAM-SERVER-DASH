package be.dash.dashserver.api.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import be.dash.dashserver.api.support.MemberIdArgumentResolver;
import be.dash.dashserver.api.support.PermissionInterceptor;
import be.dash.dashserver.api.support.converter.GenreConverter;
import be.dash.dashserver.api.support.converter.LevelConverter;
import be.dash.dashserver.api.support.converter.LocalDateTimeConverter;
import be.dash.dashserver.api.support.converter.SocialProviderConverter;
import be.dash.dashserver.api.support.converter.SortOptionConverter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final MemberIdArgumentResolver MemberIdArgumentResolver;
    private final PermissionInterceptor permissionInterceptor;
    private final GenreConverter genreConverter;
    private final LevelConverter levelConverter;
    private final LocalDateTimeConverter localDateTimeConverter;
    private final SortOptionConverter sortOptionConverter;
    private final SocialProviderConverter socialProviderConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(socialProviderConverter);
        registry.addConverter(genreConverter);
        registry.addConverter(levelConverter);
        registry.addConverter(localDateTimeConverter);
        registry.addConverter(sortOptionConverter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(MemberIdArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionInterceptor);
    }
}
