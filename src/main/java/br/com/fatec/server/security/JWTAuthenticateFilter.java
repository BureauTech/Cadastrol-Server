package br.com.fatec.server.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.services.UserDetailsData;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUseEmail(), user.getUsePassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Error to authenticate the user", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        UserDetailsData userData = (UserDetailsData) authResult.getPrincipal();
        String token = JWTUtil.generateToken(userData);
        response.setHeader("Set-Cookie", String.format("%s=%s; SameSite=None; Secure", JWTUtil.COOKIE_NAME, token));
        System.out.println(String.format("key=%s; SameSite=None; Secure", token));
    }

}
