package ru.cource.model.validation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;
import ru.cource.model.service.BookShopServiceInterface;
/**
 * A Validator for {@link Author}.A {@link Author} is valid if its has unique name
 * and has valid book string i.e. each book is persisted in database
 * 
 * 
 * @author AlexanderM-O
 *
 */
@Component
public class AuthorValidator implements Validator{

	@Autowired
	private BookShopServiceInterface service;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Author.class.equals(clazz);
	}
	

	@Override
	public void validate(Object target, Errors errors) {
		Author author = (Author) target;
		Author oldAuthor=service.findAuthorById(author.getId());
		if (!author.getName().equals(oldAuthor.getName())&&service.findAuthorByName(author.getName()) != null) {
			errors.rejectValue("name", "", "Athor with the same name is alreary exist");
		}
		if (author.getBookError() != null) {
			errors.rejectValue("books", "", author.getBookError());
			return;
		}
		Set<Book> books=author.getBooks();
		for(Book b:books) {
			if(service.findBookByName(b.getName())==null) {
				errors.rejectValue("books", "", "You have written unexisted book,you have to create it first!");
				return;
			}
		}
	}

}
