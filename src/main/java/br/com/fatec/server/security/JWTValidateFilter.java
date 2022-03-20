package br.com.fatec.server.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;


public class JWTValidateFilter extends BasicAuthenticationFilter {

    private Set<String> skipUrls = new HashSet<>(Arrays.asList("/user","/login"));
    private AntPathMatcher pathMatcher = new AntPathMatcher();

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

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getMethod().equals("POST")) {
            return skipUrls.stream().anyMatch(path -> pathMatcher.match(path, request.getServletPath()));
        }
        return false;
    }

    
}
