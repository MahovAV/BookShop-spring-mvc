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

	public Book getBookById(int id);

	public Book getBookByName(String Name);

	public Author getAuthorByName(String Name);

	public List<Book> GetAllBook();

	public List<Author> GetAllAuthors();

	public void updateBook(Book book);

	public void deleteBookById(int id);
}
