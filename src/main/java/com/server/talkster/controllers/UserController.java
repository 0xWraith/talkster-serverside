package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.dto.NameDTO;

import com.server.talkster.dto.UserChangeLoginDTO;
import com.server.talkster.dto.UserDTO;
import com.server.talkster.models.Contact;
import com.server.talkster.models.User;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.ContactService;
import com.server.talkster.services.UserService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final ContactService contactService;

    @Autowired
    public UserController(JWTUtil jwtUtil, UserService userService, ContactService contactService)
    {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.contactService = contactService;
    }

    @PutMapping("/set-name")
    public ResponseEntity<?> uploadProfile(@RequestHeader Map<String, String> headers, @RequestBody NameDTO nameDTO){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        long userID = jwtUtil.getIDFromToken(jwt);
        User user = userService.findUserByID(userID);

        user.setFirstname(nameDTO.getFirstName());
        user.setLastname(nameDTO.getLastName());
        user = userService.createUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-name/{id}")
    public ResponseEntity<NameDTO> uploadProfile(@RequestHeader Map<String, String> headers, @PathVariable("id") long userID){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userService.findUserByID(userID);
        NameDTO nameDTO = new NameDTO();
        nameDTO.setFirstName(user.getFirstname());
        nameDTO.setLastName(user.getLastname());

        return ResponseEntity.ok(nameDTO);
    }

    @PostMapping("/check-username")
    public ResponseEntity<UserChangeLoginDTO> checkUsername(@RequestHeader Map<String, String> headers, @RequestBody UserChangeLoginDTO userChangeLoginDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Optional<Long> id = userService.findIdByUsername(userChangeLoginDTO.getLogin());

        userChangeLoginDTO.setResult(id.isEmpty());

        System.out.println(userChangeLoginDTO.getResult());

        return ResponseEntity.ok(userChangeLoginDTO);
    }
    @PostMapping("/update/username")
    public ResponseEntity<UserChangeLoginDTO> updateUsername(@RequestHeader Map<String, String> headers, @RequestBody UserChangeLoginDTO userChangeLoginDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Optional<Long> id = userService.findIdByUsername(userChangeLoginDTO.getLogin());

        if(id.isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        long userID = jwtUtil.getIDFromToken(jwt);
        User user = userService.findUserByID(userID);

        user.setUsername(userChangeLoginDTO.getLogin());
        userService.createUser(user);

        return ResponseEntity.ok(userChangeLoginDTO);
    }

    @PostMapping("/update/biography")
    public ResponseEntity<UserChangeLoginDTO> updateBiography(@RequestHeader Map<String, String> headers, @RequestBody UserChangeLoginDTO userChangeLoginDTO)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        long userID = jwtUtil.getIDFromToken(jwt);
        User user = userService.findUserByID(userID);

        user.setBiography(userChangeLoginDTO.getLogin());
        userService.createUser(user);

        return ResponseEntity.ok(userChangeLoginDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUser(@RequestHeader Map<String, String> headers, @PathVariable("id") long userID)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userService.findUserByID(userID);

        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        contactService.createContact(new Contact(jwtUtil.getIDFromToken(jwt), user.getId()));

        return ResponseEntity.ok(userService.convertToUserDTO(user));
    }

    @PutMapping("/update/map-tracker/{status}")
    public ResponseEntity<?> updateMapTracker(@RequestHeader Map<String, String> headers, @PathVariable("status") boolean status)
    {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);

        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        long userID = jwtUtil.getIDFromToken(jwt);
        User user = userService.findUserByID(userID);

        user.setMapTracker(status);
        userService.createUser(user);

        return ResponseEntity.ok().build();
    }
}
