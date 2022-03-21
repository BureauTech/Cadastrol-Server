package br.com.fatec.server.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import br.com.fatec.server.responses.ErrorResponse;


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
        try {
            SecurityContextHolder.getContext().setAuthentication(JWTUtil.parseToken(jwtoken));
            chain.doFilter(request, response);
        } catch (Exception e) {
            String json = new ObjectMapper().writeValueAsString(new ErrorResponse("Your token is not valid. You need to log in.", HttpStatus.UNAUTHORIZED));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter writter = response.getWriter();
            writter.write(json);
            writter.flush();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getMethod().equals("POST")) {
            return skipUrls.stream().anyMatch(path -> pathMatcher.match(path, request.getServletPath()));
        }
        return false;
    }

    
}
