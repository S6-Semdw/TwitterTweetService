package com.example.twittertweetservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Tweet {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String message;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
