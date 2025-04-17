package com.tech.society.login.services;

import com.tech.society.login.models.User;
import com.tech.society.login.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register new user
    public User registerUser(User user) {
        user.setCustomId(generateCustomId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setCreatedDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        user.setModifiedDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        return userRepository.save(user);
    }

    // Login logic
    public Optional<User> login(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    // Forgot password request (generates a token)
    public Optional<String> initiatePasswordReset(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        userOpt.ifPresent(user -> {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setModifiedDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
            userRepository.save(user);
        });
        return userOpt.map(User::getResetToken);
    }

    // Reset password via token
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setModifiedDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Forgot username
    public Optional<String> getUsernameByEmail(String email) {
        return userRepository.findByEmail(email).map(User::getUsername);
    }

    // Change password
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && passwordEncoder.matches(oldPassword, userOpt.get().getPassword())) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setModifiedDate(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Utility to generate custom numeric ID
    private int generateCustomId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}