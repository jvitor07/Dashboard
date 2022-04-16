package api.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class JWTValidationFilter extends BasicAuthenticationFilter {
    public static final String HEADER_TOKEN_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token_attr = request.getHeader(HEADER_TOKEN_KEY);
        if(Boolean.TRUE.equals(Objects.isNull(token_attr)) || Boolean.FALSE.equals(token_attr.startsWith(TOKEN_PREFIX))) {
            chain.doFilter(request, response);
            return;
        }

        String token = token_attr.replace(TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authToken = this.getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String email = JWT.require(Algorithm.HMAC512(JWTAuthFilter.JWT_SECRET))
                .build()
                .verify(token)
                .getSubject();
        if(Boolean.TRUE.equals(Objects.isNull(email))) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
    }
}
