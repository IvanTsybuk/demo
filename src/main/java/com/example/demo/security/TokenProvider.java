package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenProvider {

    private final ObjectMapper objectMapper;

    public TokenProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Token getTokenForUser(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC512("secret");
            String token = JWT.create()
                .withIssuer("may-application")
                .withClaim("user", objectMapper.writeValueAsString(user))
                .sign(algorithm);
            return new Token(token);
        } catch (JWTCreationException | JsonProcessingException e){
            log.error(e.getMessage(), e);
            throw new JwtGenerationException(e.getMessage(), e);
        }
    }

    public User getUserFromToken(Token token) {
        User user;
        try {
            DecodedJWT jwt = JWT.decode(token.getToken());
            user = objectMapper.readValue(jwt.getClaim("user").asString(), User.class);
        } catch (JWTDecodeException | JsonProcessingException e){
            log.error(e.getMessage(), e);
            throw new JwtGenerationException(e.getMessage(), e);
        }
        return user;
    }
}
