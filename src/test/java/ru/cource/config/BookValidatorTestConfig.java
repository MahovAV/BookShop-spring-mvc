package ru.cource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.cource.model.service.BookShopServiceHibernateImpl;
import ru.cource.model.service.BookShopServiceInterface;
import ru.cource.model.validation.BookValidator;

import static org.mockito.Mockito.*;

/**
 * Setup fixture for {@link ru.cource.model.validation.BookValidatorTest
 * BookValidatorTest }
 * 
 * @author AlexanderM-O
 *
 */
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
