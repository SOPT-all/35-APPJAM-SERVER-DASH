package be.dash.dashserver.api.config;

import be.dash.dashserver.api.support.MemberIdArgumentResolver;
import be.dash.dashserver.api.support.PermissionInterceptor;
import be.dash.dashserver.api.support.SocialProviderConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final MemberIdArgumentResolver MemberIdArgumentResolver;
    private final PermissionInterceptor permissionInterceptor;
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SocialProviderConverter());
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
