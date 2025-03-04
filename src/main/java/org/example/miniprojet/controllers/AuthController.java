package org.example.miniprojet.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.miniprojet.DTO.AuthDTO;
import org.example.miniprojet.DTO.NewPasswordDTO;
import org.example.miniprojet.entities.User;
import org.example.miniprojet.repositories.UserRepository;
import org.example.miniprojet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> authenticate(@RequestBody AuthDTO auth){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(auth.username(), auth.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.findUserByUsername(auth.username()).get());
        }
        catch (AuthenticationException exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/createCode")
    public ResponseEntity<User> createCode(@RequestParam String email){
        User updatedUser = userService.createCode(email);
        if(updatedUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @PutMapping("/newPassword")
    public ResponseEntity<User> newPassword(@RequestBody NewPasswordDTO newPasswordDTO){
        User updatedUser = userService.newPassword(newPasswordDTO);
        if(updatedUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
