package ru.cource.model.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.cource.model.domain.User;
import ru.cource.model.service.UserServiceHibernateImpl;

/**
 * A Validator for {@link User}. A new {@link User} is valid if he has unique
 * name and unique email and each of fields are valid
 * 
 * @author AlexanderM-O
 *
 */
@Component
public class UserValidator implements Validator {
	private User user;

	@Autowired
	UserServiceHibernateImpl service;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		user = (User) target;
		if (!user.getConfPassword().equals(user.getPassword())) {
			errors.rejectValue("confPassword", "", "passwords are different");
		}
		if (service.findUserByName(user.getName()) != null) {
			errors.rejectValue("name", "", "user with the same name already exists");
		}
		if (service.findUserByEmail(user.getName()) != null) {
			errors.rejectValue("name", "", "user with the same email already exists");
		}
	}

}
