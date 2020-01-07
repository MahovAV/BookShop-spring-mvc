package ru.cource.model.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.Errors;

import ru.cource.config.BookValidatorTestConfig;
import ru.cource.model.domain.Book;
import ru.cource.model.service.BookShopServiceInterface;

import static org.mockito.Mockito.*;

/**
 * Test for {@link BookValidator}
 * 
 * @author AlexanderM-O
 *
 */
@ExtendWith(SpringExtension.class) // add spring support
@ContextConfiguration(classes = BookValidatorTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class BookValidatorTest {
	@Autowired
	BookValidator validator;

	@Autowired
	BookShopServiceInterface mockService;

	private static final Book book = mock(Book.class);

	private static final String nameOfBook = "1984";

	@BeforeAll
	public static void setup() {
		when(book.getName()).thenReturn(nameOfBook);
	}

	@Test
	public void validateShouldAcceptWithCorrectAuthorStringAndName() {
		// there is no such book in data base
		when(mockService.findBookByName(nameOfBook)).thenReturn(null);
		// perform action
		Errors errors = mock(Errors.class);
		validator.validate(book, errors);
		verify(errors, never()).rejectValue(eq("name"), any(), any());
	}

	@Test
	public void validateShouldRejectWithTakenName() {
		// have book
		when(mockService.findBookByName(nameOfBook)).thenReturn(new Book());
		// perform action
		Errors errors = mock(Errors.class);
		validator.validate(book, errors);
		verify(errors, times(1)).rejectValue(eq("name"), any(), any());
	}

	@Test
	public void validateShouldRejectWithWrongAuthor() {
		// have book
		when(mockService.findBookByName(nameOfBook)).thenReturn(new Book());
		when(book.getAuthorError()).thenReturn("SomeString");
		// perform action
		Errors errors = mock(Errors.class);
		validator.validate(book, errors);
		verify(errors, times(1)).rejectValue(eq("authors"), any(), any());
	}
}
