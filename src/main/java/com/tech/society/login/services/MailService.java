package com.tech.society.login.services;

import com.tech.society.login.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.admin.email}")
    private String adminEmail;

    public void sendDeactivationReport(List<User> deactivatedUsers) {
        if (deactivatedUsers.isEmpty()) return;

        StringBuilder content = new StringBuilder("The following users were deactivated due to inactivity:\n\n");
        for (User user : deactivatedUsers) {
            content.append("- ").append(user.getUsername()).append(" (Last Login: ").append(user.getLastLoginDate()).append(")\n");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adminEmail);
        message.setSubject("Deactivation Report - Inactive Users");
        message.setText(content.toString());

        mailSender.send(message);
    }
}