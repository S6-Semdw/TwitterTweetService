package com.example.twittertweetservice.repo;

import com.example.twittertweetservice.model.Tweet;
import com.example.twittertweetservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TwitterTweetRepository extends MongoRepository<Tweet, Integer> {

    List<Tweet> findByName(String e);

    List<Tweet> findByEmail(String email);
}
