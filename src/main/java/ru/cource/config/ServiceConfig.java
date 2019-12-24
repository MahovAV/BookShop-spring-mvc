package ru.cource.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configure Business Logic Layer
 */
@Configuration
@ComponentScan(basePackages= {"ru.cource.model.service"})
public class ServiceConfig {

}
