package com.server.talkster.configs;

import com.server.talkster.security.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter
{
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) { this.jwtUtil = jwtUtil; }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException
    {
        String accessToken =  httpServletRequest.getHeader("Authorization");

        if(accessToken != null && !accessToken.isBlank() && accessToken.startsWith("Bearer "))
        {
            String JWTToken = accessToken.substring(7);

            if(JWTToken.isBlank())
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            else
            {
//                String mail = jwtUtil.validateToken(accessToken);

            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
