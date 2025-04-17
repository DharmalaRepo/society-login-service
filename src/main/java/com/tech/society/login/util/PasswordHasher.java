package com.tech.society.login.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Main method for quick testing
    public static void main(String[] args) {
        System.out.println("Hashed admin123: " + hash("admin123"));
        System.out.println("Hashed user1234: " + hash("user1234"));
        System.out.println("Hashed welcome@123: " + hash("welcome@123"));
        System.out.println("Hashed changeme: " + hash("changeme"));
    }
}