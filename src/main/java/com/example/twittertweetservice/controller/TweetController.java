package com.example.twittertweetservice.controller;

import com.example.twittertweetservice.model.Tweet;
import com.example.twittertweetservice.repo.TwitterTweetRepository;
import com.example.twittertweetservice.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class TweetController {

    @Autowired
    private TweetService service;

    //Get tweets
    @GetMapping(value = "/tweets")
    public List<Tweet> findAllTweets() {
        return service.getTweets();
    }

    @GetMapping("/tweets/{name}")
    public List<Tweet> getTweetsByEmail(@PathVariable String name) throws Exception {
        try {
            return service.getTweetsByName(name);
        } catch (Exception e) {
            throw new Exception("Failed to get tweets by email: " + e.getMessage(), e);
        }
    }
}
