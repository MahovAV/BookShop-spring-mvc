package ru.cource.model.service;

import java.util.List;

import ru.cource.model.domain.Author;
import ru.cource.model.domain.Book;

public interface BookShopServiceInterface {
	public void createBook(Book book);

	public Book getBookById(int id);

	public Book getBookByName(String Name);

	public List<Book> GetAllBook();

	public List<Author> GetAllAuthors();

	public void updateBook(Book book);

	public void deleteBookById(int id);
}
