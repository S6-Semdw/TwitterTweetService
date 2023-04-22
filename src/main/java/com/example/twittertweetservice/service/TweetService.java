package com.example.twittertweetservice.service;

import com.example.twittertweetservice.model.Tweet;
import com.example.twittertweetservice.repo.TwitterTweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TwitterTweetRepository tweetRepository;

    public TweetService(TwitterTweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    //get tweets
    public List<Tweet> getTweets() {
        return tweetRepository.findAll();
    }
}
