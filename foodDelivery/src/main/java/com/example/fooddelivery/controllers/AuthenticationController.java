package com.example.fooddelivery.controllers;

import com.example.fooddelivery.auth.AuthenticationRequest;
import com.example.fooddelivery.auth.AuthenticationResponse;
import com.example.fooddelivery.auth.RegisterRequest;
import com.example.fooddelivery.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        String email = request.getEmail();
        String firstname = request.getFirstname();
        String lastname = request.getLastname();

        if(!email.contains("@")){
            System.out.println("Email not correct!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if(!containsOnlyCharacters(firstname) || !containsOnlyCharacters(lastname)){
            System.out.println("Name is not correct!" + firstname + " " + lastname);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    public static boolean containsOnlyCharacters(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
}
