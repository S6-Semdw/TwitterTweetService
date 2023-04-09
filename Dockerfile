FROM openjdk:19
MAINTAINER SemdeWilde
COPY target/TwitterTweetService-0.0.1-SNAPSHOT.jar TwitterTweetService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/TwitterTweetService-0.0.1-SNAPSHOT.jar"]
FROM openjdk:19
