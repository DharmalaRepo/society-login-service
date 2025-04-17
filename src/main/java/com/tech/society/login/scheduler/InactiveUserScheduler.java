package com.tech.society.login.scheduler;

import com.tech.society.login.models.User;
import com.tech.society.login.repositories.UserRepository;
import com.tech.society.login.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class InactiveUserScheduler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 1 * * ?") // Every day at 1 AM
    public void deactivateInactiveUsers() {
        LocalDateTime ninetyDaysAgo = LocalDateTime.now().minusDays(90);
        Date thresholdDate = Date.from(ninetyDaysAgo.atZone(ZoneId.systemDefault()).toInstant());

        List<User> inactiveUsers = userRepository.findByLastLoginDateBeforeAndIsActive(thresholdDate, 1);

        for (User user : inactiveUsers) {
            user.setActive(false);
        }
        if (!inactiveUsers.isEmpty()) {
            userRepository.saveAll(inactiveUsers);
            mailService.sendDeactivationReport(inactiveUsers);
        }

        userRepository.saveAll(inactiveUsers);
        System.out.println("Deactivated users: " + inactiveUsers.size());
    }
}