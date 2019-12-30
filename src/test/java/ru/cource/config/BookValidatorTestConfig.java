package ru.cource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.cource.model.service.BookShopServiceImpl;
import ru.cource.model.service.BookShopServiceInterface;
import ru.cource.model.validation.BookValidator;

import static org.mockito.Mockito.*;

@Configuration
public class BookValidatorTestConfig {

	@Bean
	public BookShopServiceInterface getMockBookShopService() {
		return mock(BookShopServiceInterface.class);
	}

	@Bean
	public BookValidator getBookValidator() {
		return new BookValidator();
	}

}
