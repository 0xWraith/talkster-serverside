package com.server.talkster.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.utility.Enums.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil
{
    private final int JWT_TOKEN_LIFE_SPAN = 60;

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    public String generateJWTToken(long ID, boolean isActive)
    {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(JWT_TOKEN_LIFE_SPAN).toInstant());

        return JWT.create()
                .withSubject("Talkster user data")
                .withClaim("ID", ID)
                .withClaim("isActive", isActive)
                .withIssuedAt(new Date())
                .withIssuer("talkster-security")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256("27128275542f8328906162d214f217bf7aa9f2698adcda6820b46907e88485e4"));
    }

    public String validateToken(String JWT_TOKEN) throws JWTVerificationException
    {
        JWTVerifier jwtVerifier = JWT
                .require(Algorithm.HMAC256(JWT_SECRET))
                .withSubject("Talkster user data")
                .withIssuer("talkster-security").build();

        DecodedJWT decodedJWT = jwtVerifier.verify("27128275542f8328906162d214f217bf7aa9f2698adcda6820b46907e88485e4");
        return decodedJWT.getClaim("usermail").asString();
    }
}
