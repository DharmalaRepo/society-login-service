package com.tech.society.login.controller;

import com.tech.society.login.models.User;
import com.tech.society.login.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok("Hello, welcome to Society Login Service..!!");
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    // User login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        return ResponseEntity.ok(userService.login(username, password));
    }

    // Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String username,
                                           @RequestParam String oldPassword,
                                           @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.changePassword(username, oldPassword, newPassword));
    }

    // Forgot password
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String token,
                                            @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.resetPassword(token, newPassword));
    }

    // Forgot username
//    @PostMapping("/forgot-username")
//    public ResponseEntity<?> forgotUsername(@RequestParam String email) {
//        //return ResponseEntity.ok(userService.forgotUsername(email));
//    }
}