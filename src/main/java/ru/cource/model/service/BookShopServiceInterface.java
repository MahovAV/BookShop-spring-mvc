package ru.cource.model.service;

import java.util.List;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;

/**
 * Used to store {@link ru.cource.model.domain.Book Book} and
 * {@link ru.cource.model.domain.Author Author}
 * 
 * @author AlexanderM-O
 *
 */
public interface BookShopServiceInterface {
	public void createBook(Book book);

	public Book findBookById(int id);

	public Book findBookByName(String Name);

	public Author findAuthorByName(String Name);

	public List<Book> GetAllBook();

	public List<Author> GetAllAuthors();

	public void updateBook(Book book);

	public void deleteBookById(int id);
}
