package com.example.twittertweetservice.service;

import com.example.twittertweetservice.model.Tweet;
import com.example.twittertweetservice.repo.TwitterTweetRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TwitterTweetRepository tweetRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public TweetService(TwitterTweetRepository tweetRepository, RabbitTemplate rabbitTemplate) {
        this.tweetRepository = tweetRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Tweet> getTweets() {
        return tweetRepository.findAll();
    }

    public List<Tweet> getTweetsByName(String email) throws Exception {
        try {
            return tweetRepository.findByEmail(email);
        } catch (Exception e) {
            throw new Exception("Failed to get tweets by email: " + e.getMessage(), e);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}
