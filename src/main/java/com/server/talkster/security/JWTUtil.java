package com.server.talkster.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil
{
    private final int JWT_TOKEN_LIFE_SPAN = 60;

    @Value("${JWT_SECRET}")
    private String JWT_SECRET;

    private final JWTVerifier jwtVerifier = JWT
            .require(Algorithm.HMAC256("27128275542f8328906162d214f217bf7aa9f2698adcda6820b46907e88485e4"))
            .withSubject("Talkster user data")
            .withIssuer("talkster-security").build();

    public String generateJWTToken(long ID, boolean isActive)
    {
        Date expirationDate = Date.from(ZonedDateTime.now().plusYears(JWT_TOKEN_LIFE_SPAN).toInstant());

        return JWT.create()
                .withSubject("Talkster user data")
                .withClaim("ID", ID)
                .withClaim("isActive", isActive)
                .withIssuedAt(new Date())
                .withIssuer("talkster-security")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256("27128275542f8328906162d214f217bf7aa9f2698adcda6820b46907e88485e4"));
    }

    public DecodedJWT checkJWTFromHeader(Map<String, String> headers)
    {
        if(!headers.containsKey("authorization"))
            return null;

        try { return validateToken(headers.get("authorization")); }
        catch (Exception e) { return null; }
    }

    public DecodedJWT validateToken(String JWT_TOKEN) throws JWTVerificationException { return jwtVerifier.verify(JWT_TOKEN); }
    public Long getIDFromToken(DecodedJWT jwtToken) { return jwtToken.getClaim("ID").asLong(); }
}
