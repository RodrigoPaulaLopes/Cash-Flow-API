package com.rodrigo.cashflowapi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rodrigo.cashflowapi.entities.Usuarios;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${token.secret}")
    private String SECRET;


    public String create(Usuarios usuario){
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            token = JWT.create()
                    .withIssuer("CashFlow")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(expires())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return exception.getMessage();
        }
        return token;
    }

    public Instant expires(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


    public String verify(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(SECRET);
            var tokenSubject = JWT.require(algoritmo)
                    .withIssuer("CashFlow")
                    .build()
                    .verify(token)
                    .getSubject();
            return tokenSubject;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao verificar token " + e);
        }
    }
}
