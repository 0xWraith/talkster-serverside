package com.server.talkster.controllers;

import com.server.talkster.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(JWTUtil jwtUtil)
    {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/find-user")
    public ResponseEntity<String> findUserByMail()
    {
        return ResponseEntity.ok("Hello, World!");
    }
}
