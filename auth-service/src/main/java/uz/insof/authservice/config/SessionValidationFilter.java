package uz.insof.authservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.insof.authservice.entity.UserEntity;
import uz.insof.authservice.service.AuthService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SessionValidationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String sessionToken = extractSessionToken(request);

        if (sessionToken != null) {
            UserEntity user = authService.validateSession(sessionToken);
            if (user != null) {
                request.setAttribute("user", user);
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String extractSessionToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
