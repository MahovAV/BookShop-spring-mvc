package ru.cource.model.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.cource.model.service.BookShopService;

public class BookValidator implements Validator {

	@Autowired
	private BookShopService service;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//add new type of error in name and add new filed error in author
		Book book=(Book)target;
		if(service.getBookByName(book.getName())!=null){
			//if not empty
			errors.rejectValue("name","","Book is alreary exist");
		}
	    if(book.getAuthorError()!=null) {
	    	errors.rejectValue("authors","",book.getAuthorError());
	    }
	}
		
	

}
