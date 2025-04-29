package ru.shtamov.eventmanaget.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.shtamov.eventmanaget.model.domain.User;
import ru.shtamov.eventmanaget.service.UserService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeaders = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeaders == null || !authorizationHeaders.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeaders.substring(7);

        String loginFromJwt;
        try {
            loginFromJwt = jwtTokenManager.getLoginFromToken(jwt);
        } catch (Exception e){
            log.info("Ошибка при расшифровке jwt");
            filterChain.doFilter(request, response);
            return;
        }

        User user = userService.findByLogin(loginFromJwt);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user,
                null,
                List.of(new SimpleGrantedAuthority(user.getUserRole().name()))
        );

        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }
}
