package api.config.security.filter;

import api.dtos.LoginDTO;
import api.dtos.ResponseDTO;
import api.dtos.AuthResponseDTO;
import api.exceptions.BadRequest;
import api.models.UserDetailsImpl;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public static final String JWT_SECRET = "eb470a18-db68-4381-abc6-7c0e510e11c1";
    public static final Integer JWT_EXPIRES_AT = 3_600_000;

    public JWTAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDTO user = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

            return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException ioException) {
            throw new BadRequest("Falha ao autenticar o usuário");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRES_AT))
                .sign(algorithm);
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ResponseDTO<>(null, new AuthResponseDTO(token, userDetails))));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(new ResponseDTO<AuthResponseDTO>(List.of("Email ou senha incorretos"), null)));
        response.getWriter().flush();
    }
}
