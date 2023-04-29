package com.example.twittertweetservice.controller;

import com.example.twittertweetservice.model.Tweet;
import com.example.twittertweetservice.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
