package com.tech.society.login.services;

import com.tech.society.login.dto.AdminRegistrationRequest;
import com.tech.society.login.dto.LoginRequestContext;
import com.tech.society.login.dto.UserLoginRequest;
import com.tech.society.login.models.User;
import com.tech.society.login.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    public Object login(UserLoginRequest request, LoginRequestContext context) {
        Optional<User> userOpt = userRepository.findByUsernameAndSocietyId(request.getUsername(), request.getSocietyId());
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        mailService.sendLoginSuccessEmail(userOpt.get(), context);


        return "Login successful!";
    }

    public Object registerAdmin(AdminRegistrationRequest request) {
        // Check if admin already exists for the given society
        Optional<User> existing = userRepository.findByUsernameAndSocietyId(request.getUsername(), request.getSocietyId());
        if (existing.isPresent()) {
            throw new RuntimeException("Admin or user already exists with this username and society.");
        }

        User admin = new User();
        admin.setUsername(request.getUsername());
        admin.setPassword(request.getPassword());  // Ideally encrypt the password
        admin.setEmail(request.getEmail());
        admin.setSocietyId(request.getSocietyId());
        admin.setFirstLogin(true);
        admin.setCreatedBy("SYSTEM");
        admin.setCreatedDate(LocalDateTime.now());
        admin.setMobileNumber(request.getAdminMobileNumber());
        admin.setTokenExpiry(LocalDateTime.now().plusDays(2));
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        admin.setRoles(roles);

        userRepository.save(admin);

        mailService.sendAdminRegistrationEmail(request);
        return "Admin user created successfully.";
    }

    public Object forgotPassword(String username, String societyId, LoginRequestContext context) {
        User user = userRepository.findByUsernameAndSocietyId(username, societyId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);

        mailService.sendResetTokenEmail(user, token, context);
        return "Reset token sent to your registered email.";
    }

    public Object resetPasswordWithOld(String username, String oldPassword, String newPassword, String societyId, LoginRequestContext context) {
        User user = userRepository.findByUsernameAndSocietyId(username, societyId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Old password does not match.");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        mailService.sendPasswordChangedEmail(user, context);
        return "Password changed successfully.";
    }

    public Object resetPasswordWithToken(String username, String token, String newPassword, String societyId, LoginRequestContext context) {
        User user = userRepository.findByUsernameAndSocietyId(username, societyId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!token.equals(user.getResetToken())) {
            throw new RuntimeException("Invalid reset token.");
        }

        user.setPassword(newPassword);
        user.setResetToken(null); // Clear token after use
        userRepository.save(user);
        mailService.sendPasswordResetUsingTokenEmail(user, context);
        return "Password reset successfully.";
    }

    public Object forgotUsername(String email, String societyId, LoginRequestContext context) {
        User user = userRepository.findByEmailAndSocietyId(email, societyId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        mailService.sendUsernameReminderEmail(user, context);
        return "Username sent to your registered email.";
    }
}