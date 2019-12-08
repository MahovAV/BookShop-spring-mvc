package ru.cource.model.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.cource.model.service.BookShopService;

public class BookValidator implements Validator {
	private Book book;

	@Autowired
	private BookShopService service;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}
	private void baseValidate(Object target, Errors errors) {
		book=(Book)target;
	    if(book.getAuthorError()!=null) {
	    	errors.rejectValue("authors","",book.getAuthorError());
	    }
	}

	@Override
	public void validate(Object target, Errors errors) {
		baseValidate(target,errors);
		if(service.getBookByName(book.getName())!=null){
			//if book exist in data base hence book has name and we wont have 2 errors at
			//the same time
			errors.rejectValue("name","","Book is alreary exist");
		}
	}
	
	public void validate(Object target, Errors errors,Book oldBook) {
		baseValidate(target,errors);
		if(book.getName().equals(oldBook.getName()))return;
		//book have different names should check
		if(service.getBookByName(book.getName())!=null){
			errors.rejectValue("name","","Cannot replace book");
		}
	}
	
}
