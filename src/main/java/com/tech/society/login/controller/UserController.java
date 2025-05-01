package com.tech.society.login.controller;

import com.tech.society.login.dto.AdminRegistrationRequest;
import com.tech.society.login.dto.RequestContext;
import com.tech.society.login.dto.UserLoginRequest;
import com.tech.society.login.models.User;
import com.tech.society.login.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Hello, welcome to Society Login Service..!!");
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegistrationRequest request) {
        return ResponseEntity.ok(userService.registerAdmin(request));
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request,
                                   HttpServletRequest httpRequest) {
        RequestContext context = extractContext(httpRequest);
        return ResponseEntity.ok(userService.login(request, context));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String username,
                                            @RequestParam String societyId,
                                            HttpServletRequest httpRequest) {
        RequestContext context = extractContext(httpRequest);
        return ResponseEntity.ok(userService.forgotPassword(username, societyId, context));
    }

    @PostMapping("/reset-password-old")
    public ResponseEntity<?> resetPasswordWithOld(@RequestParam String username,
                                                  @RequestParam String oldPassword,
                                                  @RequestParam String newPassword,
                                                  @RequestParam String societyId,
                                                  HttpServletRequest httpRequest) {
        RequestContext context = extractContext(httpRequest);
        return ResponseEntity.ok(userService.resetPasswordWithOld(username, oldPassword, newPassword, societyId, context));
    }

    @PostMapping("/reset-password-token")
    public ResponseEntity<?> resetPasswordWithToken(@RequestParam String username,
                                                    @RequestParam String token,
                                                    @RequestParam String newPassword,
                                                    @RequestParam String societyId,
                                                    HttpServletRequest httpRequest) {
        RequestContext context = extractContext(httpRequest);
        return ResponseEntity.ok(userService.resetPasswordWithToken(username, token, newPassword, societyId, context));
    }

    @PostMapping("/forgot-username")
    public ResponseEntity<?> forgotUsername(@RequestParam String email,
                                            @RequestParam String societyId,
                                            HttpServletRequest httpRequest) {
        RequestContext context = extractContext(httpRequest);
        return ResponseEntity.ok(userService.forgotUsername(email, societyId, context));
    }

    private RequestContext extractContext(HttpServletRequest request) {
        String societyId = request.getHeader("Society-Id");
        String ipAddress = request.getRemoteAddr();
        String requestTime = request.getHeader("Request-Time");
        String userName = request.getHeader("userName");
        String userId = request.getHeader("userId");
        String userType = request.getHeader("userType");
        return new RequestContext(societyId, ipAddress, requestTime, userName, userId, userType);
    }


}