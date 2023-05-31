package com.example.twittertweetservice.repo;

import com.example.twittertweetservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
