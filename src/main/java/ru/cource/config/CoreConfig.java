package ru.cource.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configure all configs and components for work of application.
 * 
 * @author AlexanderM-O
 *
 */
@Configuration
@ComponentScan(basePackages = { "ru.cource.config", "ru.cource.aspects", "ru.cource.model.validation",
		"ru.cource.model.service" })
public class CoreConfig {

}
