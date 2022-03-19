package br.com.fatec.server.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class JWTValidateFilter extends BasicAuthenticationFilter {

    public JWTValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwtoken = JWTUtil.getTokenInCookies(request.getCookies());
        SecurityContextHolder.getContext().setAuthentication(jwtoken != null ? JWTUtil.parseToken(jwtoken) : null);
        chain.doFilter(request, response);
    }    
}
