package com.tech.society.login.services;

import com.tech.society.login.dto.AdminRegistrationRequest;
import com.tech.society.login.dto.RequestContext;
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

    public void sendAdminRegistrationEmail(AdminRegistrationRequest admin) {
        if (admin.getEmail().isEmpty()) return;

        StringBuilder content = new StringBuilder("You have been added as a admin for the society. Please log-in to manage your society:\n\n");
        content.append("User Name:").append(admin.getUsername()).append("\n\n");
        content.append("Password:").append(admin.getPassword()).append("\n\n");
        content.append("Best Regards").append("\n").append("SHIVA DHARMALA");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(admin.getEmail());
        message.setSubject("New Admin User Added - "+admin.getSocietyId());
        message.setText(content.toString());

        mailSender.send(message);
    }

    public void sendUserRegistrationEmail(User admin) {
        if (admin.getEmail().isEmpty()) return;

        StringBuilder content = new StringBuilder("You have been added as a user for the society. Please log-in to manage your account and view details:\n\n");
        content.append("User Name:").append(admin.getUsername()).append("\n\n");
        content.append("Password:").append(admin.getPassword()).append("\n\n");
        content.append("Best Regards").append("\n").append("SHIVA DHARMALA");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(admin.getEmail());
        message.setSubject("New User Added - "+admin.getSocietyId());
        message.setText(content.toString());

        mailSender.send(message);
    }


    public void sendLoginSuccessEmail(User user, RequestContext context) {
        System.out.printf("Email to: %s -> Login success from IP: %s at %s%n", user.getEmail(), context.getIpAddress(), context.getRequestTime());
    }

    public void sendResetTokenEmail(User user, String token, RequestContext context) {
        System.out.printf("Email to: %s -> Reset token: %s generated from IP: %s%n", user.getEmail(), token, context.getIpAddress());
    }

    public void sendPasswordChangedEmail(User user, RequestContext context) {
        System.out.printf("Email to: %s -> Password changed from IP: %s%n", user.getEmail(), context.getIpAddress());
    }

    public void sendPasswordResetUsingTokenEmail(User user, RequestContext context) {
        System.out.printf("Email to: %s -> Password reset using token from IP: %s%n", user.getEmail(), context.getIpAddress());
    }

    public void sendUsernameReminderEmail(User user, RequestContext context) {
        System.out.printf("Email to: %s -> Username reminder sent from IP: %s%n", user.getEmail(), context.getIpAddress());
    }
}