package ru.cource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.cource.model.service.BookShopServiceImpl;
import ru.cource.model.validation.BookValidator;


/**
 * Contains all config.
 */
@Configuration
@ComponentScan(basePackages = {"ru.cource.config","ru.cource.aspects","ru.cource.model.validation"})
public class CoreConfig {
	
}
