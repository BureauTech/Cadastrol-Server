package br.com.fatec.server.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.fatec.server.entities.UserEntity;
import br.com.fatec.server.services.UserDetailsData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
    
    private static final String KEY = "spring.jwt.sec";
    private static final Long EXPIRATION = 1000L * 60L * 60L; // A hour
    //private static final Long EXPIRATION = 1000L * 100L; // Ten seconds

    public static String generateToken(UserDetailsData user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        UserEntity userWithoutPassword = new UserEntity();
        userWithoutPassword.setUseEmail(user.getUsername());
        String jsonUser = mapper.writeValueAsString(userWithoutPassword);
       // Date now = new Date();
        return Jwts.builder().claim("userDetails", jsonUser).setIssuer("br.gov.sp.fatec")
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(SignatureAlgorithm.HS512, KEY).compact();
    }

    public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody().get("userDetails",
                String.class);
        UserEntity user = mapper.readValue(credentialsJson, UserEntity.class);
        //UserDetails userDetails = User.builder().username(user.getUseEmail()).password("secret").build();
        return new UsernamePasswordAuthenticationToken(user.getUseEmail(), user.getUsePassword(), new ArrayList<>());
    }
}