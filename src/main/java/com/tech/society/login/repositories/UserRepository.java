package com.tech.society.login.repositories;

import com.tech.society.login.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsernameAndSocietyId(String username, String societyId);
    Optional<User> findByEmailAndSocietyId(String email, String societyId);
}