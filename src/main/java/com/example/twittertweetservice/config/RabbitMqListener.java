package com.example.twittertweetservice.config;

import com.example.twittertweetservice.model.Tweet;
import com.example.twittertweetservice.model.User;
import com.example.twittertweetservice.repo.TwitterTweetRepository;
import com.example.twittertweetservice.repo.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class RabbitMqListener {

    @Autowired
    private RabbitTemplate template;

    private final ObjectMapper objectMapper;

    public RabbitMqListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Autowired
    private TwitterTweetRepository repository;

    @Autowired
    private UserRepository userRepository;

    @RabbitListener(queues = "token")
    public void receiveMessage(String jwtToken) {
        System.out.println("Received JWT token: " + jwtToken);

        try {
            String[] tokenParts = jwtToken.split("\\.");
            String payloadBase64 = tokenParts[1]; // Get the payload part of the JWT token
            String payloadJson = new String(Base64.decodeBase64(payloadBase64));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(payloadJson);

            if (jsonNode.has("sub")) {
                String sub = jsonNode.get("sub").asText();
                System.out.println("EMAIL: " + sub);

                // Create a new tweet with the extracted email and additional text
                Tweet tweet = new Tweet();
                tweet.setName(sub); // Use the extracted email value
                tweet.setText("This is an example tweet.");

                // Save the tweet using your tweet service
                repository.save(tweet);
            } else {
                System.out.println("Email not found in the JWT token payload.");
            }
        } catch (JsonParseException | JsonMappingException e) {
            // Handle JSON parsing errors
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "deleteUser")
    public void receiveDelete(String json) {
        try {
            // Extract the email from the JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            String email = jsonNode.get("email").asText();

            // Find the user by email
            Optional<User> user = userRepository.findByEmail(email);

            if (user.isPresent()) {
                userRepository.delete(user.get());
                System.out.println("User deleted: " + email);
            } else {
                System.out.println("User not found with email: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @RabbitListener(queues = "getTweetUser")
//    public void getTweet(String email) throws Exception {
//        try {
//            System.out.println(email);
//
//            List<Tweet> tweets = repository.findByEmail(email);
//
//            // Create a message containing the email and tweets
//            Message<List<Tweet>> message = MessageBuilder.withPayload(tweets)
//                    .setHeader("email", email)
//                    .build();
//            System.out.println(message);
//
//            // Send the message to the desired destination exchange and routing key
//            template.convertAndSend("x.send-tweetUser", "sendTweetUser", message);
//
//        } catch (Exception e) {
//            throw new Exception("Failed to get tweets by email: " + e.getMessage(), e);
//        }
//    }

    @RabbitListener(queues = "getTweetUser")
    public void getTweet(String email) throws Exception {
        try {
            System.out.println(email);

            List<Tweet> tweets = repository.findByEmail(email);

            // Serialize the payload to JSON
            String payloadJson = objectMapper.writeValueAsString(tweets);

            // Create headers and set the email
            MessageHeaders headers = new MessageHeaders(Map.of("email", email));

            // Create a message containing the email and tweets with JSON payload
            Message<String> message = MessageBuilder.createMessage(payloadJson, headers);

            System.out.println(message);

            // Send the message to the desired destination exchange and routing key
            template.convertAndSend("x.send-tweetUser", "sendTweetUser", message);
        } catch (Exception e) {
            throw new Exception("Failed to get tweets by email: " + e.getMessage(), e);
        }
    }




}
