package com.easy.throttling.api;


import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application config
 */
@Log4j
@Configuration
@ComponentScan({
    "com.easy.throttling.services"})
public class ApiConfig {

}
