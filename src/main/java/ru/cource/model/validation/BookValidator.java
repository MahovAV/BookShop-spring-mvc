package ru.cource.model.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.cource.model.domain.Book;
import ru.cource.model.service.BookShopServiceInterface;

/**
 * A Validator for {@link Book}.A {@link Book} is valid if its has unique name
 * and has valid author string i.e. there is no duplicates and we could parse it
 * 
 * 
 * @author AlexanderM-O
 *
 */
@Component
public class BookValidator implements Validator {
	private Book book;

	@Autowired
	private BookShopServiceInterface service;

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}

	private void baseValidate(Object target, Errors errors) {
		book = (Book) target;
		if (book.getAuthorError() != null) {
			errors.rejectValue("authors", "", book.getAuthorError());
		}
	}

	/**
	 * valid {@link Book} in create methods
	 * 
	 * @param target new book
	 * @param errors
	 *
	 */
	@Override
	public void validate(Object target, Errors errors) {
		baseValidate(target, errors);
		if (service.findBookByName(book.getName()) != null) {
			errors.rejectValue("name", "", "Book with the same name is alreary exist");
		}
	}

	/**
	 * valid {@link Book} in update methods:should allow new book has the same name
	 * as old book due to we replace it
	 * 
	 * @param target  new book
	 * @param errors
	 * @param oldBook book which we are replacing
	 */
	public void validate(Object target, Errors errors, Book oldBook) {
		baseValidate(target, errors);
		if (book.getName().equals(oldBook.getName()))
			return;
		// book have different names should check is it try to replace another book
		if (service.findBookByName(book.getName()) != null) {
			errors.rejectValue("name", "", "Cannot replace book");
		}
	}

}
