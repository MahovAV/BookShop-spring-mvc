package ru.cource.model.validation;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.cource.model.domain.Book;
import ru.cource.model.domain.User;
import ru.cource.model.service.UserServiceImpl;
/**
 * A Validator for registration
 * 
 * A new user is valid if:
 * 1.Has unique name (get all from database and check)
 * 2.Has unique email
 * IMPLEMENT!!!			
 */
@Component
public class UserValidator implements Validator {
	private User user;
	
	@Autowired
	UserServiceImpl service;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		user = (User) target;
		if(!user.getConfPassword().equals(user.getPassword())) {
			errors.rejectValue("confPassword","","passwords are different");
		}
		if(service.findUserByName(user.getName())!=null) {
			errors.rejectValue("name","","user with the same name already exists");
		}
		if(service.findUserByEmail(user.getName())!=null) {
			errors.rejectValue("name","","user with the same name already exists");
		}
	}

}
