package com.server.talkster.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.talkster.models.FileReference;
import com.server.talkster.models.User;
import com.server.talkster.security.JWTUtil;
import com.server.talkster.services.FileService;
import com.server.talkster.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FileService fileService;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public FileController(FileService fileService, JWTUtil jwtUtil, UserService userService){
        this.fileService = fileService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PutMapping("/set-profile")
    public ResponseEntity<?> uploadProfile(@RequestHeader Map<String, String> headers, @RequestParam("file") MultipartFile file){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        long userID = jwtUtil.getIDFromToken(jwt);
        User user = userService.findUserByID(userID);
        FileReference oldProfilePicture = null;
        FileReference profilePicture = null;
        if (user.getImageID() != null){
            oldProfilePicture = fileService.findFileByID(user.getImageID());
        }
        try {
             profilePicture = fileService.uploadFile(file);
        }
        catch(IllegalStateException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setImageID(profilePicture.getID());
        userService.createUser(user);
        if (oldProfilePicture != null){
            fileService.removeFileByID(oldProfilePicture.getID());
            fileService.deleteFileFromPath(oldProfilePicture.getName());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestHeader Map<String, String> headers, @RequestParam("file") MultipartFile file){
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        long userID = jwtUtil.getIDFromToken(jwt);

        try {
            fileService.uploadFile(file);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IllegalStateException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?>downloadFile(@RequestParam("filename") String filename){
        try {
            byte[] file = fileService.downloadFile(filename);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(file);
        }
        catch(IllegalStateException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-profile/{id}")
    public ResponseEntity<?>downloadProfile(@RequestHeader Map<String, String> headers, @PathVariable("id") long userID) {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        User user = userService.findUserByID(userID);
        if (user.getImageID() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            FileReference profilePicture = fileService.findFileByID(user.getImageID());
            byte[] file = fileService.downloadFile(profilePicture.getName());
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(profilePicture.getType())).body(file);
        } catch(IllegalStateException | IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-profile")
    public ResponseEntity<?> deleteProfile(@RequestHeader Map<String, String> headers) {
        DecodedJWT jwt = jwtUtil.checkJWTFromHeader(headers);
        if(jwt == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        long userID = jwtUtil.getIDFromToken(jwt);
        User user = userService.findUserByID(userID);
        if (user.getImageID() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            long imageID = user.getImageID();
            user.setImageID(null);
            FileReference profilePicture = fileService.findFileByID(imageID);
            fileService.deleteFileFromPath(profilePicture.getName());
            fileService.removeFileByID(imageID);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(IllegalStateException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
