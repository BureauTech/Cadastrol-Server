package br.com.fatec.server.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.services.UserDetailsData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
    @Autowired
    Environment env;

    private static String SECRET_KEY;
    private static final Long EXPIRATION_IN_MS = 10800000L;
    public static final String COOKIE_NAME = "jwtoken";

    @Value("${jwt.secretkey}")
    public void setSecretKey(String key) {
        JWTUtil.SECRET_KEY = key;
    }

    public static String generateToken(UserDetailsData user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        UserEntity userWithoutPassword = new UserEntity();
        userWithoutPassword.setUseEmail(user.getUsername());
        String jsonUser = mapper.writeValueAsString(userWithoutPassword);

        return Jwts.builder().claim("userDetails", jsonUser).setIssuer("br.com.fatec")
            .setSubject(user.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_IN_MS)).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("userDetails", String.class);
        UserEntity user = mapper.readValue(credentialsJson, UserEntity.class);
        return new UsernamePasswordAuthenticationToken(user.getUseEmail(), user.getUsePassword(), new ArrayList<>());
    }

    public static String getTokenInCookies(Cookie[] cookies) {
        if (cookies == null) return null;
        for (Cookie cookie : cookies)
            if (cookie.getName().equals(JWTUtil.COOKIE_NAME))
                return cookie.getValue();
        return null;
    }
}
