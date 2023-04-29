package com.example.twittertweetservice.repo;

import com.example.twittertweetservice.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterTweetRepository extends JpaRepository<Tweet, Integer> {
}
