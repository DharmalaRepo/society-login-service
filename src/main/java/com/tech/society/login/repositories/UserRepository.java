package com.tech.society.login.repositories;

import com.tech.society.login.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Get by customId
    Optional<User> findByCustomId(Integer customId);

    // Get by username
    Optional<User> findByUsername(String username);

    // Get by email
    Optional<User> findByEmail(String email);

    // Get by mobile number
    Optional<User> findByMobileNumber(String mobileNumber);

    // Get by societyId
    List<User> findBySocietyId(String societyId);

    // Get by reset token
    Optional<User> findByResetToken(String resetToken);

    // Delete by username
    void deleteByUsername(String username);

    // Get all users with specific roles (e.g., ADMIN, RESIDENT)
    List<User> findByRolesContaining(String role);

    // Get active users
    List<User> findByIsActive(boolean isActive);

    List<User> findByLastLoginDateBeforeAndIsActive(Date date, int isActive);
}