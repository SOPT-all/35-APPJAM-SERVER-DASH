package be.dash.dashserver.api.support;

import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.auth.UnAuthorizedException;
import be.dash.dashserver.core.exception.DashException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenExtractor jwtTokenExtractor;
    private final TokenParser tokenParser;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(MemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String memberId;
        try {
            memberId = jwtTokenExtractor.getSubject(token);
        } catch (ExpiredJwtException e) {
            throw UnAuthorizedException.expired(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw UnAuthorizedException.wrong(token);
        }
        return Long.valueOf(memberId);
    }
}
