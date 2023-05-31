FROM openjdk:19-jdk-alpine
MAINTAINER SemdeWilde
COPY target/TwitterTweetService-0.0.1-SNAPSHOT.jar TwitterTweetService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/TwitterTweetService-0.0.1-SNAPSHOT.jar"]