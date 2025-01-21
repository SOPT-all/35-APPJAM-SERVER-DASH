package be.dash.dashserver.api.support;

import java.util.Set;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import be.dash.dashserver.core.auth.JwtTokenExtractor;
import be.dash.dashserver.core.auth.TokenParser;
import be.dash.dashserver.core.auth.UnAuthorizedException;
import be.dash.dashserver.core.domain.member.Role;
import be.dash.dashserver.core.exception.ForbiddenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final TokenParser tokenParser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof ResourceHttpRequestHandler)
            return true;
        HandlerMethod method = (HandlerMethod) handler;
        Permission permission = method.getMethodAnnotation(Permission.class);
        if (permission == null)
            return true;

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Role role;
        try {
            role = jwtTokenExtractor.getRole(tokenParser.getToken(token));
        } catch (NullPointerException e) {
            throw UnAuthorizedException.empty();
        } catch (ExpiredJwtException e) {
            throw UnAuthorizedException.expired(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw UnAuthorizedException.wrong(token);
        }

        Set<Role> annotations = Set.of(permission.role());

        if (annotations.contains(Role.MEMBER)) {
            if (role == Role.MEMBER) {
                log.info("Successfully authenticated as Member");
                return true; // Check @Permission
            }
        }

        if (annotations.contains(Role.TEACHER)) {
            if (role == Role.TEACHER) {
                log.info("Successfully authenticated as Teacher");
                return true; // Check @Permission
            }
        }
        throw new ForbiddenException("요청하신 자원에 대해 권한이 없습니다.");
    }
}
