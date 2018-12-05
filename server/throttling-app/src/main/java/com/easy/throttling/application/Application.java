package com.easy.throttling.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class for start and configure Spring Application
 */
@SpringBootApplication
@EnableAutoConfiguration(
    exclude = {MongoAutoConfiguration.class, BatchAutoConfiguration.class
        , RabbitAutoConfiguration.class, SecurityFilterAutoConfiguration.class, CodecsAutoConfiguration.class}
)
@ComponentScan(basePackages = "com.easy.throttling.application")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
